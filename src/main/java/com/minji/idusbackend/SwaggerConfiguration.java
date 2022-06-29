package com.minji.idusbackend;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfiguration {
    private String version;
    private String title;

    @Bean
    public Docket api() {
        version = "v1";
        title = "aLittleVanilla API " + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.minji.idusbackend"))
                .paths(PathSelectors.any())
                .build()
                .groupName(version)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo(title, version))
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "A Little Vanilla API Docs",
                version,
                "http://www.alittlevanilla.kro.kr/",
                new Contact("Contact Me", "https://blog.naver.com/ghdalswl77", "ghdalswl77@naver.com"),
                "Licenses",

                "http://www.alittlevanilla.kro.kr/",

                new ArrayList<>());
    }
}