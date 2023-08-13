package com.vedha.blog.configuration;

import com.vedha.blog.security.JwtAuthenticationEntryPoint;
import com.vedha.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
@SecurityScheme(name = "Jwt Authentication", description = "Jwt Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class BlogSecurityConfiguration {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManager -> authorizationManager.requestMatchers(HttpMethod.GET, "/api/blog/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/versioning/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll() // For Redirect To Swagger-Ui
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // /swagger-ui/** -> Web UI Page(Internally Uses -> /v3/api-docs/**), /v3/api-docs/** -> Json Format
                        .anyRequest().authenticated());

        // Jwt Security Filter
        httpSecurity.exceptionHandling(httpSecurityExceptionHandling -> httpSecurityExceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        httpSecurity.sessionManagement(httpSecuritySessionManagement -> httpSecuritySessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

//    /*@Bean
//    public UserDetailsService inMemoryUserDetails() {
//
//        UserDetails user = User.builder().username("vedha").password(getPasswordEncoder().encode("vedha123")).roles("USER").build();
//
//        UserDetails admin = User.builder().username("admin").password(getPasswordEncoder().encode("admin123")).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }*/

}
