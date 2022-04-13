package com.epam.esm.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Configuration
@ComponentScan("com.epam.esm")
@Profile("dev")
public class DevRepositoryConfig {

    private static final String SCHEMA = "classpath:schema.sql";
    private static final String START_DATA = "classpath:data.sql";

    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(SCHEMA)
                .addScript(START_DATA)
                .build();
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
