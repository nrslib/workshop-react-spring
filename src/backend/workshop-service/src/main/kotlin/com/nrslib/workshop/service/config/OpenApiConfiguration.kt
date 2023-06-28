package com.nrslib.workshop.service.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes("sigv4", sigv4SecurityScheme())
            )
            .info(
                Info().title("Workshop API")
                    .version("0.0.1")
            )
    }

    private fun sigv4SecurityScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .name("Authorization")
            .`in`(SecurityScheme.In.HEADER)
            .extensions(mapOf("x-amazon-apigateway-authtype" to "awsSigv4"))
    }
}