package com.vmanav123.batch.config;

import com.vmanav123.batch.model.properties.Postgres;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.CompositeDatabasePopulator;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DBConfig {

    @Primary
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


    @Autowired
    @Bean(name = "batchDatasource")
    public DataSource batchDataSource(Postgres postgres) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        log.info("Postgres Details : {}" , postgres.toString());
        dataSource.setDriverClassName(postgres.getDriverClassName());
        dataSource.setUsername(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/batchDB");
        dataSource.setSchema("batch_schema");
        return dataSource;
    }


    @SneakyThrows
    @Bean
    @Autowired
    @Qualifier("batchDatasource")
    public DataSourceInitializer batchDatasourceInitializer(DataSource dataSource)
    {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        return initializer;
    }




}
