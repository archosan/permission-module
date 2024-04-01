
package com.cihanozmen.permissionmodule.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Title")
                        .version("v1")
                        .description("API Description"));
    }

    @Bean
    public OpenApiCustomiser globalHeaderCustomiser() {
        return openApi -> openApi.getPaths().values()
                .forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                    operation.addParametersItem(new Parameter().name("Accept-Language")
                            .description("Dil tercihi")
                            .required(false)
                            .in("header")
                            .example("en-US"));
                }));
    };
}
