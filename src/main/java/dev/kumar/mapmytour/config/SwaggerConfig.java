package dev.kumar.mapmytour.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "MapMyTour API",
        version = "1.0.0",
        description = "REST API for managing tour packages with image upload functionality",
        contact = @Contact(
            name = "Kumar Sambhav",
            email = "sambhav26k@gmail.com"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local Development Server"),
        @Server(url = "https:", description = "Production Server")
    }
)
@SecurityScheme(
    name = "X-API-Key",
    type = SecuritySchemeType.APIKEY,
    paramName = "X-API-Key",
    in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
    description = "API Key for authentication"
)
public class SwaggerConfig {
}
