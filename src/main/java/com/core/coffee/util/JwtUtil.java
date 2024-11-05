package com.core.coffee.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.core.coffee.entity.User;

@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private static final String LOGLINE = JwtUtil.class.getName() + " - {} - {}";

    
    private final String GENERATING_TOKEN = "Generation Token";
    private final String GENERATING_REFRESH_TOKEN = "Generation Refresh Token";
    private final String EXTRACT_USERNAME = "Extract Username"; 
    private final String BUILD_TOKEN = "buildToken";
    private final String EXTRACT_EXPIRATION = "extractExpiration";


    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-token.expiration}")
    private long jwtRefreshExpiration;

    

    public String extractEmailUser(final String token) {

        LOGGER.debug(LOGLINE, EXTRACT_USERNAME, Constants.IN);
        final Claims jwtToken = Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

        LOGGER.debug(LOGLINE, EXTRACT_USERNAME, Constants.OUT);
        return jwtToken.getSubject();
        
    } 


    public String generateToken(User user) {
        LOGGER.debug(LOGLINE, GENERATING_TOKEN, Constants.IN);

        String token = buildToken(user, jwtExpiration);
        LOGGER.debug(LOGLINE, GENERATING_TOKEN, Constants.OUT);
        return token;
        
    }

    public String generateRefreshToken(User user) {
        LOGGER.debug(LOGLINE, GENERATING_REFRESH_TOKEN, Constants.IN);
        String token = buildToken(user, jwtRefreshExpiration);
        LOGGER.debug(LOGLINE, GENERATING_REFRESH_TOKEN, Constants.IN);
        return token;
    }

    private String buildToken(final User user, final long expiration){
        LOGGER.debug(LOGLINE, BUILD_TOKEN, Constants.IN);
        String token = Jwts
                .builder()     
                .id(UUID.randomUUID().toString()) //a random UUID      
                .claims(Map.of("name",user.getName()))
                .subject(user.getEmail())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .compact();
        
        LOGGER.debug(LOGLINE, BUILD_TOKEN, Constants.OUT);
        return token;
    }

    public boolean isTokenValid(final String token, final User user) {
        final String email = extractEmailUser(token);
      // LOGGER.debug(LOGLINE, "XXXXXXXXXX", Constants.OUT);
      //  LOGGER.debug(LOGLINE, "XXXXXXXXXX+email "+email, Constants.OUT);
      //  LOGGER.debug(LOGLINE, "XXXXXXXXXX+user.getEmail() "+user.getEmail(), Constants.OUT);
        return (email.equals(user.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        LOGGER.debug(LOGLINE, "isTokenExpired " + new Date() , Constants.OUT);
        LOGGER.debug(LOGLINE, "isTokenExpired ---°" + extractExpiration(token).before(new Date()), Constants.OUT);

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        LOGGER.debug(LOGLINE, EXTRACT_EXPIRATION, Constants.IN);
        
        final Claims jwtToken = Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
        LOGGER.debug(LOGLINE, EXTRACT_EXPIRATION, Constants.OUT);
        LOGGER.debug(LOGLINE, "getExpiration "+jwtToken.getExpiration(), Constants.OUT);
        return jwtToken.getExpiration();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
