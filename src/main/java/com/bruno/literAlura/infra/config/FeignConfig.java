package com.bruno.literAlura.infra.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

@Configuration
public class FeignConfig {

    @Bean
    Request.Options options() {
        return new Request.Options(5000L, TimeUnit.SECONDS, 1000L, TimeUnit.SECONDS, true);
    }

}
