package ru.loginov.dev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({
        "ru.loginov.dev.services",
        "ru.loginov.dev.repositories"
})
@Import({
        HibernateConfig.class,
        SecurityConfig.class
})
public class RootConfig {
}