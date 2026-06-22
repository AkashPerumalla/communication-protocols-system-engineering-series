package com.sky2dev.day21.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/health", "/h2-console/**", "/api/platform").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/audit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("VIEWER", "OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            PasswordEncoder passwordEncoder,
            @Value("${app.security.viewer-username}") String viewerUsername,
            @Value("${app.security.viewer-password}") String viewerPassword,
            @Value("${app.security.operator-username}") String operatorUsername,
            @Value("${app.security.operator-password}") String operatorPassword,
            @Value("${app.security.admin-username}") String adminUsername,
            @Value("${app.security.admin-password}") String adminPassword) {
        UserDetails viewer = User.withUsername(viewerUsername)
                .password(passwordEncoder.encode(viewerPassword))
                .roles("VIEWER")
                .build();
        UserDetails operator = User.withUsername(operatorUsername)
                .password(passwordEncoder.encode(operatorPassword))
                .roles("VIEWER", "OPERATOR")
                .build();
        UserDetails admin = User.withUsername(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles("VIEWER", "OPERATOR", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(viewer, operator, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
