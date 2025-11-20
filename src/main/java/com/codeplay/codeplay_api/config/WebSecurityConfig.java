package com.codeplay.codeplay_api.config;

import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http
            // 1. NONAKTIFKAN CSRF (Penting untuk REST API)
            .csrf(csrf -> csrf.disable())
            // 2. Tentukan aturan otorisasi untuk semua request
            .authorizeHttpRequests(auth ->
                auth
                    // IZINKAN akses ke semua endpoint di bawah /api/auth/ tanpa otentikasi
                    .requestMatchers("/api/auth/**")
                    .permitAll()
                    .requestMatchers("/api/courses")
                    .permitAll()
                    .requestMatchers("/api/courses/{idCourse}/lessons")
                    .permitAll()
                    .requestMatchers("/api/lessons/{idLesson}/stages")
                    .permitAll()
                    .requestMatchers("/api/lessons/*/stages/*/questions/*/**")
                    .permitAll()
                    .requestMatchers("/api/lessons/*/stages/*/**")
                    .permitAll()
                    .requestMatchers("/api/energy/**")
                    .permitAll()
                    .requestMatchers("/api/check-time")
                    .permitAll()
                    // Semua request lainnya HARUS diotentikasi
                    .anyRequest()
                    .authenticated()
            );

        // Nonaktifkan form login default dan basic authentication jika Anda akan menggunakan JWT
        http.httpBasic(basic -> basic.disable());

        return http.build();
    }
}
