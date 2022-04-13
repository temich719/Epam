package com.epam.esm.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class WebLayerConfig implements WebMvcConfigurer {

    @Bean
    public Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

}
