package com.bruno.literAlura.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bruno.literAlura.dtos.ClientResponseDto;
import com.bruno.literAlura.infra.config.FeignConfig;

@FeignClient(name = "gutendex-client", url = "https://gutendex.com", configuration = FeignConfig.class)
public interface GutendexClient {

    @GetMapping("/books")
    public ClientResponseDto findBookByTitle(@RequestParam("search") String title);
}
