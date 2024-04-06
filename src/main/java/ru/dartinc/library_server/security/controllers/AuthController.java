package ru.dartinc.library_server.security.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dartinc.library_server.security.dto.JwtAuthenticationResponse;
import ru.dartinc.library_server.security.dto.SignInRequest;
import ru.dartinc.library_server.security.dto.SignUpRequest;
import ru.dartinc.library_server.security.services.AuthenticationService;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        LinkedMultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        try{
            return ResponseEntity.ok(authenticationService.signUp(request));
        } catch( RuntimeException e){
            if(e.getMessage().equals("Пользователь с таким именем уже существует")){
                headers.put("error", List.of("Пользователь с таким именем уже существует"));
            } else if (e.getMessage().equals("Пользователь с таким email уже существует")){
                headers.put("error",List.of("Пользователь с таким email уже существует"));
            }
            return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
