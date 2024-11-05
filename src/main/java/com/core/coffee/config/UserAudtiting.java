package com.core.coffee.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserAudtiting implements AuditorAware<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAudtiting.class);
    private static final String LOGLINE = UserAudtiting.class.getName() +" - {} - {}";  

    @Override
    public Optional<String> getCurrentAuditor() {
      
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || !authentication.isAuthenticated()) {
        LOGGER.debug(LOGLINE, "getCurrentAuditor", "System");
          return Optional.of("System");
      }     

      String uname = SecurityContextHolder.getContext().getAuthentication().getName();
      if(uname == null || uname.isEmpty() || uname.equals("anonymousUser")) {
        LOGGER.debug(LOGLINE, "getCurrentAuditor", "System");
        return Optional.of("System");
      }
      LOGGER.debug(LOGLINE, "getCurrentAuditor", authentication.getName());
      return Optional.of(uname);
     
    }

    
}
