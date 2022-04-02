package com.vmanav123.batch.model.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "batch.thread")
@Getter
@Setter
@PropertySource("classpath:application-dev.properties")
public class BatchThread {
    private int corePoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;
    private boolean allowCoreThreadTimeOut;
}
