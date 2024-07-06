package com.example.onlineshopproject.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Online Shop",
                version = "1.0",
                description = "API for Online Shop",
                contact = @Contact(
                        name = "Andrii",
                        email = "andrii@ukr.net",
                        url = "http://onlineshop.com"
                )
        )
)
public class SwaggerConfiguration {}