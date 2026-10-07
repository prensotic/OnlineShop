package ru.loginov.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        System.out.println("=== AUTHENTICATION MANAGER CREATED ===");
        System.out.println("UserDetailsService: " + userDetailsService);
        System.out.println("PasswordEncoder: " + passwordEncoder);

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager) throws Exception {

        System.out.println("=== SECURITY CONFIG CREATED ===");
        System.out.println("AuthenticationManager: " + authenticationManager);

        http
                .authenticationManager(authenticationManager)

                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/registration",
                                "/auth/login",
                                "/products"
                        ).permitAll()

                        .requestMatchers("/profile/**")
                        .authenticated()

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/products", true)
                        .failureHandler((request, response, exception) -> {
                            System.out.println("=== AUTHENTICATION FAILED ===");
                            System.out.println("Exception: " + exception.getClass().getName());
                            System.out.println("Message: " + exception.getMessage());

                            response.sendRedirect("/auth/login?error");
                        })
                        .permitAll()
                );

        return http.build();
    }
}