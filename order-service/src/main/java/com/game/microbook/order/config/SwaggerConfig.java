package com.game.microbook.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {

    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service APIs")
                        .description("BookStore Order Service APIs")
                        .version("v1.0.0"))
                .servers(List.of(new Server().url("http://localhost:8082")));
    }
}
