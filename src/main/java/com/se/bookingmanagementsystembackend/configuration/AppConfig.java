package com.se.bookingmanagementsystembackend.configuration;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoding passwordEncoder() {
        return new PasswordEncoding() {
            @Override
            public String encode(String password) {
                return BCrypt.hashpw(password, BCrypt.gensalt());
            }

            @Override
            public boolean matches(String password, String hashedPassword) {
                return BCrypt.checkpw(password, hashedPassword);
            }
        };
    }

}

