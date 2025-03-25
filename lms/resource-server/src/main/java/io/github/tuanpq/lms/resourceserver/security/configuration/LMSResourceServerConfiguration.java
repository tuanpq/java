package io.github.tuanpq.lms.resourceserver.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class LMSResourceServerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LMSResourceServerConfiguration.class);

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.trace("=====LMS===== securityFilterChain: start");

        http.securityMatcher("/courses/**")
            .authorizeHttpRequests(authorize -> authorize.anyRequest().hasAuthority("SCOPE_lms.read"))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        logger.trace("=====LMS===== securityFilterChain: end");

        return http.build();
    }

}
