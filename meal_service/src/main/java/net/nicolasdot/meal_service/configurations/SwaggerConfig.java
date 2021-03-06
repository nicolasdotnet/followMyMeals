package net.nicolasdot.meal_service.configurations;

import java.util.Arrays;
import java.util.HashSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author nicolasdotnet
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(SwaggerConfigProperties swaggerConfigProperties) {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(new HashSet<>(
                        Arrays.asList(swaggerConfigProperties.getProducesAndconsumes())))
                .
                consumes(new HashSet<>(
                        Arrays.asList(swaggerConfigProperties.getProducesAndconsumes())))
                .apiInfo(apiInfo(swaggerConfigProperties))
                .enable(Boolean.valueOf(swaggerConfigProperties.getEnabled()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.nicolasdot.meal_service.controllers"))
                .paths(PathSelectors.regex("/api.*"))
                .build();
    }

    private ApiInfo apiInfo(SwaggerConfigProperties swaggerConfigProperties) {

        return new ApiInfoBuilder().title(swaggerConfigProperties.getTitle()).description(swaggerConfigProperties.getDescription())
                .version(swaggerConfigProperties.getApiVersion()).build();

    }

}
