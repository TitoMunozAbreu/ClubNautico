package com.app.clubnautico;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@SpringBootApplication
public class ClubNauticoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubNauticoApplication.class, args);
    }

    @Bean
    Faker faker(){
        return new Faker(new Locale("es","ES"));
    }
}
