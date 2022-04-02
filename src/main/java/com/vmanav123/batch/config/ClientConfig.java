package com.vmanav123.batch.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.vmanav123.batch.model.AWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppClientConfiguration {

    @Bean
    @Autowired
    public AmazonS3 amazonS3(AWS aws) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        aws.getAccessKey(),
                                        aws.getSecretKey()
                                )
                        ))
                .withRegion(aws.getRegion())
                .build();
    }

}
