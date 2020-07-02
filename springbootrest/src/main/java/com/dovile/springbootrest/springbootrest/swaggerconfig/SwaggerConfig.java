package com.dovile.springbootrest.springbootrest.swaggerconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.dovile.springbootrest"))
                    .paths(regex("/api.*"))
                    .build()
                    .apiInfo(apiInfo());
        }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "REST API for a real estate registry for buildings", //title
                "Calculation of the total yearly real estate tax for all properties owned by a particular owner.", //description
                "Version 1.0", //version
                "Terms of service", //terms of service URL
                new Contact("Dovile Barkauskaite", "https://github.com/Doirena/JavaHomeTask", "barkauskaite.dovile@gmail.com"),
                "License of API", "API license URL", Collections.emptyList()); // contact info


    }
}
