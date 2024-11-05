package com.core.coffee.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.core.coffee.entity.User;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.UserRepository;
import com.core.coffee.util.Constants;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import lombok.RequiredArgsConstructor;


import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class BackendCoreConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendCoreConfig.class);
    private static final String LOGLINE = BackendCoreConfig.class.getName() +" - {} - {}"; 
    
    private static final String USER_DETAIL_SERVICE = "userDetailsService";
    private static final String AUTH_PROVIDER = "authenticationProvider";

    private final UserRepository userRepository;


    /*
     * User Details Service
     */
    @Bean
    UserDetailsService userDetailsService() {
        LOGGER.debug(LOGLINE, USER_DETAIL_SERVICE, Constants.IN);
        
        return username -> {
            final User user = userRepository.findByEmail(username).orElseThrow( () -> new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue()));

            List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
            
            LOGGER.debug(LOGLINE, USER_DETAIL_SERVICE, Constants.OUT);
            return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .authorities(authorities)            
            .password(user.getPassword())
            .build();
        };
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    UserAudtiting userAudtiting() {
        return new UserAudtiting();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    AuthenticationManager authenticatedAuthorizationManager( AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    /*     
     * Authentication Provider 
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        LOGGER.debug(LOGLINE, AUTH_PROVIDER, Constants.IN);

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        LOGGER.debug(LOGLINE, AUTH_PROVIDER, Constants.OUT);
        return provider;
     
    }

    /*
     * Swagger Configuration
     */
   @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
            .servers(
                List.of(new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8080").description("Local Server"))
            )            
            .addSecurityItem(new SecurityRequirement().
                addList(Constants.API_INFO_SWAGGER))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title(Constants.API_INFO_SWAGGER)
                .description(Constants.API_DESCRIPTION_SWAGGER)
                .version(Constants.API_VERSION_SWAGGER)   
                
                );
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }

    
}
