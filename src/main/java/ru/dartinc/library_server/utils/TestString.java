package ru.dartinc.library_server.utils;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestString {
    public static void main(String[] args) {
        System.out.println(capitalizeString("салтыков-щедрин"));
        System.out.println(capitalizeString("САЛТЫКОВ-ЩЕДРИН"));
        System.out.println(capitalizeString("сАлТыКоВ-щЕдРиН"));
        System.out.println(capitalizeString("салтыков"));
    }

    public static String capitalizeString(String str){
        str=str.toLowerCase();
        if(str.contains("-")){
            return Arrays.stream(str.split("-")).map(StringUtils::capitalize).collect(Collectors.joining("-"));
        } else {
            return StringUtils.capitalize(str);
        }
    }
}
