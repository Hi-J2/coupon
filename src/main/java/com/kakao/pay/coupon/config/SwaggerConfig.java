package com.kakao.pay.coupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
//        .globalOperationParameters(
//                        newArrayList(new ParameterBuilder()
//                                        .name("storeId")
//                                        .description("Description of storeId")
//                                        .modelRef(new ModelRef("string"))
//                                        .parameterType("header")
//                                        .defaultValue("memebox")
//                                        .required(true)
//                                        .build()
//                                , new ParameterBuilder()
//                                        .name("serviceId")
//                                        .description("Description of serviceId")
//                                        .modelRef(new ModelRef("string"))
//                                        .parameterType("header")
//                                        .defaultValue("memebox")
//                                        .required(true)
//                                        .build()
//                                , new ParameterBuilder()
//                                        .name("terminalId")
//                                        .description("Description of terminalId")
//                                        .modelRef(new ModelRef("string"))
//                                        .parameterType("header")
//                                        .required(true)
//                                        .defaultValue("10005001")
//                                        .build()
//                                , new ParameterBuilder()
//                                        .name("accountId")
//                                        .description("Description of accountId")
//                                        .modelRef(new ModelRef("integer"))
//                                        .parameterType("header")
//                                        .required(true)
//                                        .defaultValue("0")
//                                        .build()
//                                , new ParameterBuilder()
//                                        .name("accountName")
//                                        .description("Description of accountName")
//                                        .modelRef(new ModelRef("string"))
//                                        .parameterType("header")
//                                        .required(true)
//                                        .defaultValue("unknown")
//                                        .build()
//                        ));
    }
}
