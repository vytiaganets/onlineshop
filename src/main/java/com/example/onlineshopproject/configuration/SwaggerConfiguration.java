package com.example.onlineshopproject.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
public class SwaggerConfiguration {
    @Bean
    @Primary
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi
                .builder()
                .group("public")
                .packagesToScan("com.example.onlineshopproject.controller")
                .build();
    }

    private GroupedOpenApi groupedOpenApi(String groupName, String paths) {
        return GroupedOpenApi
                .builder()
                .group(groupName)
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi cartApi() {
        return groupedOpenApi("carts", "/v1/carts/**");
    }

    @Bean
    public GroupedOpenApi categoryApi() {
        return groupedOpenApi("categories", "/v1/categories/**");
    }

    @Bean
    public GroupedOpenApi favoritiesApi() {
        return groupedOpenApi("favorities", "v1/favorities/**");
    }

    @Bean
    public GroupedOpenApi ordersApi() {
        return groupedOpenApi("orders", "/v1/orders/**");
    }

    @Bean
    public GroupedOpenApi producctsApi() {
        return groupedOpenApi("products", "/v1/products/**");
    }

    @Bean
    public GroupedOpenApi usersApi() {
        return groupedOpenApi("users", "/v1/users/**");
    }
}
