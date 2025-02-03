package com.learn.jwt.security.securityConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
