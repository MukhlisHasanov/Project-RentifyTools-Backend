package org.rentifytools.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Отключаем CSRF защиту
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll() // Разрешаем доступ ко всем запросам
//                );
//
//        return http.build();
//    }
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.POST, "api/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/users/{id}").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.GET,"api/users").permitAll()
                                .requestMatchers(HttpMethod.PATCH,"api/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "api/tools").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/tools").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.PUT, "api/tools/{toolId}").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"api/tools/{toolId}").hasAnyRole("USER","ADMIN")
                                .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
