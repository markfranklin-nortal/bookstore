package com.bookstore.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class FlywayConfig {
    
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    
    @PostConstruct
    public void init() {
        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(url, user, password).load();
        flyway.migrate();
    }
}
