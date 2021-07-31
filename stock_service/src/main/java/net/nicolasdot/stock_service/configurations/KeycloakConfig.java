package net.nicolasdot.stock_service.configurations;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author nicolasdotnet
 */
@Configuration
public class KeycloakConfig {
    
    @Bean
    KeycloakSpringBootConfigResolver configResolver(){
        
        return new KeycloakSpringBootConfigResolver();
        
    }

    
}
