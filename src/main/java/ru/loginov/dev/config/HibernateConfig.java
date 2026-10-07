package ru.loginov.dev.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import ru.loginov.dev.entities.*;

import javax.sql.DataSource;

@Configuration
public class HibernateConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/online_shop");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Ilya150906");

        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {

        org.hibernate.cfg.Configuration configuration =
                new org.hibernate.cfg.Configuration();

        configuration
                .addAnnotatedClasses(
                        User.class,
                        UserPersonalData.class,
                        Category.class,
                        Manufacturer.class,
                        Product.class,
                        Cart.class,
                        CartItem.class,
                        Order.class,
                        OrderItem.class,
                        Payment.class,
                        Comment.class
                )
                .addPackage("ru.loginov.dev.entities")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty(
                        "hibernate.current_session_context_class",
                        "org.springframework.orm.jpa.hibernate.SpringSessionContext"
                );

        configuration.getProperties().put(
                "hibernate.connection.datasource",
                dataSource
        );

        return configuration.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        return new HibernateTransactionManager(sessionFactory);
    }
}