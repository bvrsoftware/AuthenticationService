package com.bvrsoftware.config;

import java.util.Collections;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	//http://localhost:9090/swagger-ui/index.html
    @Bean
    Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }
	
	public ApiInfo getInfo() {
		return new ApiInfo("ONLINE-EXAMINATION-PORTAL-SYSTEM APPLICATION API", "this project Api Create by Sanjeev kumar", "1.0", "Term and Service", new Contact("SANJEEV KUMAR", "http://bvrsoftware.com/", "ssp9448@gmail.com"), "license of Api", "Api license URL", Collections.emptyList());
	}
}
