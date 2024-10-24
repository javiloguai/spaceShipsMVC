package com.w2m.spaceShips.infrastructure.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * @author javiloguai
 */
@OpenAPIDefinition(info = @Info(title = "SpaceShips SpringBoot 3 API", version = "1.0", description = "This is an amazing API to define and classify famous spaceShips from movies and Sci-Fi ", termsOfService = "http://swagger.io/terms/", license = @License(name = "Apache 2.0 Licence for space ships", url = "http://springdoc.org/")), security = {@SecurityRequirement(name = "bearerAuth")})
@SecurityScheme(name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfiguration {
}
