package com.vmanav123.batch.jobs.jsonToPostgres;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author mverma
 * @version Latest
 * @apiNote Job Flow : JSON(s3) -> PostgresDB -> CSV(S3) -> MongoDB
 * @implSpec    Fetch User.json File from S3 -> Read using JsonItemReader -> Process using JsonToPostgresProcessor  -> Write into PostgresDB using PostgresItemWriter
 */

@Configuration
@EnableBatchProcessing
@Slf4j
public class JobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;




    /**
     *
     * @param listener
     * @param jsonToPostgresStep
     * @param postgresToCsvStep
     * @param csvToMongoStep
     * @return
     */

    @Bean("jsonToPostgresJob")
    @Autowired
    @SneakyThrows
    public Job jsonToPostgresJob(@Qualifier("batchJobExecutionListener") JobExecutionListener listener,
                                 @Qualifier("jsonToPostgresStep") Step jsonToPostgresStep,
                                 @Qualifier("postgresToCsvStep") Step postgresToCsvStep,
                                 @Qualifier("csvToMongoStep") Step csvToMongoStep) {

        return this.jobBuilderFactory
                .get("jsonToPostgresJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(jsonToPostgresStep)
                .next(postgresToCsvStep)
                .next(csvToMongoStep)
                .build();
    }
}
