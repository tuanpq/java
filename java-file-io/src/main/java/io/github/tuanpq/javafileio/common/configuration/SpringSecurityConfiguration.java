package io.github.tuanpq.javafileio.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SpringSecurityConfiguration.class);

    @Bean
    PasswordEncoder passwordEncoder() {
        logger.debug("passwordEncoder: start");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        logger.debug("passwordEncoder: end");
        return passwordEncoder;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        logger.debug("filterChain: start");
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/register/**", "/files", "/files/view/**", "/xml/search/**").permitAll()
                                .requestMatchers("/webjars/**").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .permitAll())
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/logout")
                                .logoutSuccessUrl("/"));

        SecurityFilterChain securityFilterChain = httpSecurity.build();
        logger.debug("filterChain: end");
        return securityFilterChain;
    }

}
