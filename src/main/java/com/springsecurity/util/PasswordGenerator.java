package com.springsecurity.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static String encryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
