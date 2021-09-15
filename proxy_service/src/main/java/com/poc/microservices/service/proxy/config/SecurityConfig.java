package com.poc.microservices.service.proxy.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 *
 * @author nicolasdotnet
 */
@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // l'authentification est basé sur keyclock ou userDetail 
        auth.authenticationProvider(keycloakAuthenticationProvider());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        super.configure(http);

        http.csrf().disable();
        //http.authorizeRequests().antMatchers("/api/user/**").hasAuthority("user");
        
        http.authorizeRequests().antMatchers("**/api/user/**").hasAuthority("user");

        //http.authorizeRequests().anyRequest().authenticated();
    }

}
