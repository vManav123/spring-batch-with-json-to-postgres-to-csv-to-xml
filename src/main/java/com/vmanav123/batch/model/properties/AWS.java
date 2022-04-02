package com.vmanav123.batch.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AWS {
    private String accessKey;
    private String secretKey;
    private String region;
}
