package com.wdt.keyvaluestore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/*
 * Swagger API documentation configuration
 * Swagger UI URL : http://localhost:8080/swagger-ui.html
 * Json : http://localhost:8080/v2/api-docs
 */

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wdt.keyvaluestore"))
                .paths(regex("/api.*"))
                .build();
    }
}

