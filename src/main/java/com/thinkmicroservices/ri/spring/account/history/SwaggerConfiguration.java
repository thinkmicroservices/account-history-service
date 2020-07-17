/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author cwoodward
 */
@Profile(value = {"swagger"})
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfiguration {
 
    @Bean
    public Docket productApi() {

        log.info("create swagger2 docket");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.thinkmicroservices.ri.spring.account.history.controller"))
                .build().apiInfo(apiEndpointInfo());

    }

    private ApiInfo apiEndpointInfo() {
        log.info("create swagger2 api endpoint info");
        return new ApiInfoBuilder().title("Account History REST API")
                .description("provides access to account-history facilities")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}
