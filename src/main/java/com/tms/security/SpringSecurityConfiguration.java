package com.tms.security;

import com.tms.security.filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SpringSecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET, "/user").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/authentication").permitAll()
                                .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user/last/{lastname}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user/first/{firstname}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/user").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/user").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/song").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/song/song_name/{songName}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/song").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/song").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/song/{id}").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/artist").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/artist/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/artist").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/artist").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/artist/{id}").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}