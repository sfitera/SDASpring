package org.dreamteam.sda.confiuguration;

import org.dreamteam.sda.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initUsers(UserService userService) {
        return args -> {
            try {
                userService.loadUserByUsername("admin");
            } catch (UsernameNotFoundException e){
                userService.createUser("admin", "admin123", "ADMIN");
            }
        };
    }
}
