package com.vmanav123.batch.model.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
@PropertySource("classpath:application-dev.properties")
public class AWS {
    private String accessKey;
    private String secretKey;
    private String region;
}
