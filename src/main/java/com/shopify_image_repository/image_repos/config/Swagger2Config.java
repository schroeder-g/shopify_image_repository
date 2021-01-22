package com.shopify_image_repository.image_repos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configures the default Swagger Documentation
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config
{
    /**
     * Configures what to document using Swagger
     *
     * @return A Docket which is the primary interface for Swagger configuration
     */
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.shopify_image_repository.image_repos"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo());
    }

    /**
     * Configures some information related to the Application for Swagger
     *
     * @return ApiInfo a Swagger object containing identification information for this application
     */
    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("The Repository of Authenticated Vector Images for Shopify AKA R.A.V.I.Sh.")
                .description("Demonstrating facility with RESTful APIs, OOP / ORM, and Authentication / Authorization  best practices with OAuth.")
                .contact(new Contact("Alexander Goncalves",
                        "http://schroeder-g.me",
                        "schroedergoncalves@gmail.com"))
                .license("")
                .licenseUrl("http://unlicense.org")
                .version("1.0.0")
                .build();
    }
}