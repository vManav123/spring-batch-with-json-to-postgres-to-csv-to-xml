package com.vmanav123.batch.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "database.postgres")
@Getter
@Setter
@ToString
public class Postgres {
    private String driverClassName;
    private String username;
    private String password;
    private String url;
    private String schema;
}
