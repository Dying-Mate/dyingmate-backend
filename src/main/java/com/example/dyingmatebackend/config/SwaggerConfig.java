package com.example.dyingmatebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket DyingMateApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Dying Mate API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.dyingmatebackend"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.DyingMateApiInfo());
    }

    private ApiInfo DyingMateApiInfo() {
        return new ApiInfoBuilder()
                .title("Dying Mate API")
                .description("Dying Mate 프로젝트입니다.")
                .build();
    }
}
