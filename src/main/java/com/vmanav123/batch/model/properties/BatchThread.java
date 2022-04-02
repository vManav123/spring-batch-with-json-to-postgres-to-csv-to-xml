package com.vmanav123.batch.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "batch.thread")
@Getter
@Setter
public class BatchThread {
    private int corePoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;
    private boolean allowCoreThreadTimeOut;
}
