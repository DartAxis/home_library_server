package ru.dartinc.library_server.utils;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil {
    public static String capitalizeString(String str){
        str=str.toLowerCase();
        if(str.contains("-")){
            return Arrays.stream(str.split("-")).map(StringUtils::capitalize).collect(Collectors.joining("-"));
        } else {
            return StringUtils.capitalize(str);
        }
    }
}
