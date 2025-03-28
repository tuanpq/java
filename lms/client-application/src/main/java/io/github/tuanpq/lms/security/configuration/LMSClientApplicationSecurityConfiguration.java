package io.github.tuanpq.lms.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class LMSClientApplicationSecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LMSClientApplicationSecurityConfiguration.class);

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.trace("=====LMS===== securityFilterChain: start");
        
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
            .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/lms-client-oidc"))
            .oauth2Client(Customizer.withDefaults());
        
        logger.trace("=====LMS===== securityFilterChain: end");

        return http.build();
    }

}
