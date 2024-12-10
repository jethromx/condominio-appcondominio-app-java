package com.core.coffee.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.core.coffee.entity.Token;
import com.core.coffee.entity.User;
import com.core.coffee.repository.TokenRepository;
import com.core.coffee.repository.UserRepository;
import com.core.coffee.util.Constants;
import com.core.coffee.util.JwtUtil;
import com.mongodb.lang.NonNull;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);
    private static final String LOGLINE = JwtAuthFilter.class.getName() +" - {} - {}"; 
    
    private static final String DO_FILTER_INTERNAL = "doFilterInternal";
    private static final String BEARER = "Bearer ";

   // @Value("${api.enpoint.auth}")
    private String API_ENDPOINT="/api/v0/auth/";
    

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
        throws ServletException, IOException, java.io.IOException {
            LOGGER.debug(LOGLINE, DO_FILTER_INTERNAL, Constants.IN);


            if(request.getRequestURI().contains(API_ENDPOINT)){
                LOGGER.debug(LOGLINE, "API not required auth", Constants.IN);

                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(authHeader == null || !authHeader.startsWith(BEARER)){
                LOGGER.debug(LOGLINE, "Not autentication header", Constants.IN);
                filterChain.doFilter(request, response);
                
                return;
            }

            final String jwtToken = authHeader.substring(7);
            if(jwtToken == null ){
                LOGGER.debug(LOGLINE, "Token null", Constants.IN);                
                filterChain.doFilter(request, response);
                return;
            }else if (jwtToken.isEmpty() || jwtToken.isBlank() || jwtToken.length() <20){
                LOGGER.debug(LOGLINE, "Token empty", Constants.IN);                
                filterChain.doFilter(request, response);
                return;
                
            }

            final String userEmail = jwtUtil.extractEmailUser(jwtToken);
            
            if(userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null){                
                LOGGER.debug(LOGLINE,"userEmail  null or autentication not null", Constants.IN);
                return;
            }

            LOGGER.debug(LOGLINE, "tokenRepository.findByToken ", Constants.IN);
            final Token token = tokenRepository.findByToken(jwtToken).orElse(null);
            LOGGER.debug(LOGLINE, "tokenRepository.findByToken ", Constants.OUT);

            if(token == null || token.isRevoked() || token.isExpirated()){
                LOGGER.debug(LOGLINE, "Token null o expirated", Constants.IN);
                filterChain.doFilter(request, response);
                return;
            }

            LOGGER.debug(LOGLINE, "userDetailsService.loadUserByUsername ", Constants.IN);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            final Optional<User>  user = userRepository.findByEmail(userEmail);

            if(user.isEmpty()){
                LOGGER.debug(LOGLINE, "User empty", Constants.IN);
                filterChain.doFilter(request, response);
                return;
            }

            
            final Boolean isTokenValid = jwtUtil.isTokenValid(jwtToken, user.get());

            
            if(!isTokenValid){
                LOGGER.debug(LOGLINE, "Token invalid", Constants.IN);
                filterChain.doFilter(request, response);
                return;
            }

            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, 
                null,
                userDetails.getAuthorities()
            );

            LOGGER.debug(LOGLINE, "UsernamePasswordAuthenticationToken - setDetails", Constants.IN);
            authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));

            LOGGER.debug(LOGLINE, "SecurityContextHolder -setAuthentication", Constants.IN);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            LOGGER.debug(LOGLINE, DO_FILTER_INTERNAL, Constants.OUT);
            filterChain.doFilter(request, response);

    }
    
}
