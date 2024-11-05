package com.core.coffee.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.core.coffee.entity.Token;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.TokenRepository;
import com.core.coffee.util.Constants;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig  {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository; 

    

    //@Value("${api.endpoint.auth.match}")
    private final String API_ENDPOINT_MATCH  = "/api/v0/auth/**"; //TODO get from properties

    //@Value("${api.enpoint.logout}")
    private final String API_LOGOUT= "/api/v0/auth/logout"; //TODO get from properties


    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String LOGLINE = SecurityConfig.class.getName() +" - {} - {}"; 
    private static final String SECURITY_FILTER_CHAIN = "securityFilterChain";
    private static final String LOGOUT = "logout";

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {     
        LOGGER.debug(LOGLINE, SECURITY_FILTER_CHAIN, Constants.IN);
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                req
                    .requestMatchers(API_ENDPOINT_MATCH)
                    .permitAll()
                    .requestMatchers("/swagger-ui/**","/v3/api-docs/**","swagger-ui.html")
                    .permitAll()
                    .requestMatchers("/test/**","/api/v0/rabbitmq/**")
                    .permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")  // Solo accesible por usuarios con rol ADMIN
                    .anyRequest().authenticated()                    
            )
            .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout
                .logoutUrl(API_LOGOUT)
                    .addLogoutHandler((request, response, authentication)->{
                        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                        logout(authHeader);
                    })                
                .logoutSuccessHandler((request, response, authentication) ->
                    SecurityContextHolder.clearContext()
                )
            );
        LOGGER.debug(LOGLINE, SECURITY_FILTER_CHAIN, Constants.OUT);
        return http.build();
    }

    private void logout(String token) {

        LOGGER.debug(LOGLINE, LOGOUT, Constants.IN);
        if(token == null || !token.startsWith("Bearer ")){
            throw new CustomException(Constants.API_TOKEN_INVALID, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());           
        }

        final String jwtToken = token.substring(7);

        final Token foundToken = tokenRepository.findByToken(jwtToken)
            .orElseThrow( ()-> new CustomException(Constants.API_TOKEN_INVALID, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue()) );           

        foundToken.setRevoked(true);
        foundToken.setExpirated(true);
        tokenRepository.save(foundToken);
        LOGGER.debug(LOGLINE, LOGOUT, Constants.OUT);

    }
    
}
