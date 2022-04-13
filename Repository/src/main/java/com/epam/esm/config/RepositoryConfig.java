package com.epam.esm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import static com.epam.esm.stringsStorage.RepositoryStringsStorage.*;

@Configuration
@Profile("prod")
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
@PropertySource("classpath:databaseConfig.properties")
public class RepositoryConfig {

    private final Environment environment;

    @Autowired
    public RepositoryConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource getDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(PROD_DATABASE_POOL_SIZE))));
        dataSource.setDriverClassName(environment.getProperty(DRIVER));
        dataSource.setJdbcUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USER));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone("UTC");
    }

    @Bean
    public DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    }

    @Bean("date")
    @Scope("prototype")
    public Date getDate() {
        return new Date();
    }

    @Bean
    public ApplicationContext getApplicationContext() {
        return new AnnotationConfigApplicationContext();
    }
}
