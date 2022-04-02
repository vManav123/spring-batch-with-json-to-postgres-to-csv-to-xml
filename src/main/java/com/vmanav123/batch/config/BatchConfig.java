package com.vmanav123.batch.config;

import com.vmanav123.batch.model.properties.BatchThread;
import lombok.SneakyThrows;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    @Bean(name = "multiThreadedTaskExecutor",destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor multiThreadedTaskExecutor(BatchThread batchThread) {
        final ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(batchThread.getCorePoolSize());
        threadPool.setAllowCoreThreadTimeOut(batchThread.isAllowCoreThreadTimeOut());
        threadPool.setKeepAliveSeconds(batchThread.getKeepAliveSeconds());
        threadPool.setQueueCapacity(batchThread.getQueueCapacity());
        return threadPool;
    }

    @Bean
    @Autowired
    @SneakyThrows
    public JobRepository getJobRepository(@Qualifier("batchDatasource") DataSource dataSource,
                                          @Qualifier("resourceLessTransactionManager") PlatformTransactionManager transactionManager)
    {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "resourceLessTransactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }
}
