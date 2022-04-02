package com.vmanav123.batch.steps;

import com.vmanav123.batch.model.data.UserDocument;
import com.vmanav123.batch.model.data.UserEntity;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class Steps {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("multiThreadedTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    @Bean("jsonToPostgresStep")
    public Step jsonToPostgresStep(@Qualifier("jsonItemReader") ItemReader<UserDocument> itemReader,
                                   @Qualifier("jsonToPostgresProcessor") ItemProcessor<UserDocument, UserEntity> itemProcessor,
                                   @Qualifier("postgresItemWriter") ItemWriter<UserEntity> itemWriter) {

        return this.stepBuilderFactory
                .get("jsonToPostgresStep")
                .<UserDocument, UserEntity>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                //.taskExecutor(threadPoolTaskExecutor)
                .build();
    }


    @Autowired
    @Bean("postgresToCsvStep")
    public Step postgresToCsvStep(@Qualifier("postgresItemReader") ItemReader<UserEntity> itemReader,
                                  @Qualifier("postgresToCsvProcessor") ItemProcessor<UserEntity, UserEntity> itemProcessor,
                                  @Qualifier("csvItemWriter") ItemWriter<UserEntity> itemWriter,
                                  @Qualifier("stepItemExecutionListener") StepExecutionListener stepExecutionListener) {

        return this.stepBuilderFactory
                .get("postgresToCsvStep")
                .<UserEntity, UserEntity>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .listener(stepExecutionListener)
                //.taskExecutor(threadPoolTaskExecutor)
                .build();
    }

    @Autowired
    @Bean("csvToMongoStep")
    public Step csvToMongoStep(@Qualifier("csvItemReader") ItemReader<UserEntity> itemReader,
                               @Qualifier("csvToMongoProcessor") ItemProcessor<UserEntity, UserDocument> itemProcessor,
                               @Qualifier("mongoItemWriter") ItemWriter<UserDocument> itemWriter) {

        return this.stepBuilderFactory
                .get("csvToMongoStep")
                .<UserEntity, UserDocument>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                //.taskExecutor(threadPoolTaskExecutor)
                .build();
    }
}
