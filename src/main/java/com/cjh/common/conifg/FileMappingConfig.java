package com.cjh.common.conifg;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileMappingConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // download mp3
        registry.addResourceHandler("/file/mp3/**")
            .addResourceLocations("file:/home/book/mp3/");
        // download excel
        registry.addResourceHandler("/home/excel/out/**")
            .addResourceLocations("file:/home/excel/out/");
    }
}
