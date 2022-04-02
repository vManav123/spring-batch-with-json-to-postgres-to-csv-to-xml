package com.vmanav123.batch.config;

import com.vmanav123.batch.model.Postgres;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.net.MalformedURLException;

@Configuration
@Slf4j
public class DBConfiguration {


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


//    @SneakyThrows
//    @Bean
//    @Autowired
//    @Qualifier("batchDatasource")
//    public DataSourceInitializer batchDatasourceInitializer(DataSource dataSource)
//    {
//        DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        return initializer;
//    }

    @Bean
    @Autowired
    @SneakyThrows
    public JobRepository getJobRepository(@Qualifier("batchDatasource") DataSource dataSource,
                                           @Qualifier("transactionManager") PlatformTransactionManager transactionManager)
    {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Autowired
    @Bean("jobLauncher")
    public JobLauncher getJobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
