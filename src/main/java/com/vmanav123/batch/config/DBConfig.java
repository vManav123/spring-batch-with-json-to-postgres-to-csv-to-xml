package com.vmanav123.batch.config;

import com.vmanav123.batch.model.properties.Postgres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DBConfig {

    @Autowired
    @Bean(name = "postgresDatasource")
    public DataSource postgresqlDataSource(Postgres postgres) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        log.info("Postgres Details : {}" , postgres.toString());
        dataSource.setDriverClassName(postgres.getDriverClassName());
        dataSource.setUsername(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        dataSource.setUrl(postgres.getUrl());
        dataSource.setSchema(postgres.getSchema());
        return dataSource;
    }

}
