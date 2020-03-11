package com.sda.microblogging.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String SWAGGER_SECURITY_SCHEMA_OAUTH2 = "swagger_oauth2";

    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.version}")
    private String version;
    @Value("${swagger.path}")
    private String path;
    @Value("${swagger.ui.oauth2.clientId}")
    private String swaggerAppClientId;
    @Value("${swagger.ui.oauth2.clientSecret}")
    private String swaggerClientSecret;
    @Value("${swagger.ui.oauth2.token.url}")
    private String swaggerTokenURL;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(regex(path))
                .build()
                .apiInfo(apiDetails())
                .useDefaultResponseMessages(false)
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo apiDetails() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(swaggerAppClientId)
                .clientSecret(swaggerClientSecret)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    @Bean
    public SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name(SWAGGER_SECURITY_SCHEMA_OAUTH2)
                .grantTypes(grantTypes())
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference(SWAGGER_SECURITY_SCHEMA_OAUTH2, scopes())))
                .forPaths(PathSelectors.regex("^/(v2|logout)/((?!(sign-up|password-reset-link|password-reset)).)*$"))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[0];
    }

    private List<GrantType> grantTypes() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(swaggerTokenURL);
        return Arrays.asList(grantType);
    }
}
