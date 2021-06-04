package com.jevsoftwares.apirestencurtadorurl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
     public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jevsoftwares.apirestencurtadorurl.controller"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Encurtamento de URL")
                .description("Redirecionamento de link com diminuição de caracteres.")
                .version("1.0")
                .contact(contact())
                .build();

    }

        private Contact contact(){
    return new Contact(
            "Jayson Mattoso Mareco",
            "www.jevsoftwares.com.br",
            "jevsoftwares.com.br"
            );
    }
}
