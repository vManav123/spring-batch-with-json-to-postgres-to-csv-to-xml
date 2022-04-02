package com.vmanav123.batch.config;

import com.vmanav123.batch.model.properties.BatchThread;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
}
