package org.oka.springsecurity.demoapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration. Defines the secured and non-secured endpoints. It also defines the required authorities for
 * each endpoint.
 */
@Configuration
public class SecurityFilterChainConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/info").hasAnyAuthority("VIEW_INFO")
                .antMatchers("/admin").hasAnyAuthority("VIEW_ADMIN")
                .and()
                .formLogin()
                .loginProcessingUrl("/perform_login")
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }
}