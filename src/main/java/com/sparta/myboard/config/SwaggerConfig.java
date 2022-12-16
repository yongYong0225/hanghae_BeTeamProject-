package com.sparta.myboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
    public class SwaggerConfig {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.OAS_30)
                    .useDefaultResponseMessages(true) // Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404) 등의 노출 여부
                    .apiInfo(apiInfo()) // Swagger UI 로 노출할 정보
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.sparta.myboard.controller")) // api 스펙이 작성되어 있는 패키지 (controller)
                    .paths(PathSelectors.any()) // apis 에 위치하는 API 중 특정 path 를 선택
                    .build();
        }

        public ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("MY BOARD")
                    .description("게시판")
                    .version("1.0")
                    .build();
        }
//        private Set<String> getConsumeContentTypes() {
//            Set<String> consumes = new HashSet<>();
//            consumes.add("application/json;charset=UTF-8");
//            consumes.add("application/x-www-form-urlencoded");
//            return consumes;
//        }
//
//        private Set<String> getProduceContentTypes() {
//            Set<String> produces = new HashSet<>();
//            produces.add("application/json;charset=UTF-8");
//            return produces;
//        }
    }
