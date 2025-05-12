package com.example.cms_mzt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档配置
 */
@Configuration
public class SwaggerConfig {

    /**
     * API文档信息配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("美职通CMS API文档")
                        .description("美职通CMS后台管理系统接口文档")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("miro")
                                .email("394464214@qq.com")
                                .url("https://www.example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
} 