package com.org.chat_room_backend.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:postgresql://localhost:5432/chat-room","postgres", "admin");
    }
}
