package io.github.tuanpq.lms.authorizationserver.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class LMSAuthorizationServerSecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LMSAuthorizationServerSecurityConfiguration.class);

    @Bean
    @Order(1)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        logger.trace("=====LMS===== authorizationServerSecurityFilterChain: start");
        
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
		http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
			.with(authorizationServerConfigurer, (authorizationServer) -> authorizationServer.oidc(Customizer.withDefaults()))	// Enable OpenID Connect 1.0
			.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
        SecurityFilterChain sfc = http.formLogin(Customizer.withDefaults()).build();
        
        logger.trace("=====LMS===== authorizationServerSecurityFilterChain: end");

		return sfc;
    }
    
    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        logger.trace("=====LMS===== defaultSecurityFilterChain: start");

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
            .formLogin(Customizer.withDefaults());
        SecurityFilterChain sfc = http.build();

        logger.trace("=====LMS===== defaultSecurityFilterChain: end");

        return sfc;
    }

    @Bean
    UserDetailsService users() {
        logger.trace("=====LMS===== users: start");

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails admin = User.builder()
            .username("admin")
            .password("password")
            .passwordEncoder(encoder::encode)
            .roles("ADMIN")
            .build();
        UserDetails user = User.builder()
            .username("user")
            .password("password")
            .passwordEncoder(encoder::encode)
            .roles("USER")
            .build();
            
        logger.trace("=====LMS===== users: end");

        return new InMemoryUserDetailsManager(admin, user);
    }
    
}
