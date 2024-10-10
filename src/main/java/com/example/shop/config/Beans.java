package com.example.shop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining Spring beans.
 */
@Configuration
public class Beans {

    /**
     * Provides a ModelMapper bean.
     * ModelMapper is an intelligent object mapping library that automatically maps objects to each other.
     * It is used to map DTOs to entities and vice versa.
     *
     * @return a new instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
