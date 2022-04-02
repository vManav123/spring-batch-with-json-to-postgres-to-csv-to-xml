package com.vmanav123.batch.job.jsonToPostgres;

import com.vmanav123.batch.job.s3Operation.S3Operation;
import com.vmanav123.batch.model.data.User;
import com.vmanav123.batch.model.data.UserEntity;
import com.vmanav123.batch.repository.UserEntityRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.jsr.job.flow.support.JsrFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    private static final String FILE_NAME = "User.json";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("s3Operation")
    private S3Operation s3Operation;

    @Autowired
    @Lazy
    private UserEntityRepository userRepository;


    @Bean("jsonToPostgresReader")
    public ItemReader<User> reader() {
        return new JsonItemReaderBuilder<User>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(User.class))
                .resource(s3Operation.downloadFile(FILE_NAME))
                .name("userJsonItemReader")
                .build();
    }

    @Bean("jsonToPostgresWriter")
    public ItemWriter<UserEntity> writer() {
        RepositoryItemWriter<UserEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean("jsonToPostgresProcessor")
    public ItemProcessor<User, UserEntity> processor() {
        return user -> {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(user, userEntity);
            return userEntity;
        };
    }


    @Bean("jsonToPostgresStep")
    public Step jsonToPostgresStep(@Qualifier("jsonToPostgresReader") ItemReader<User> itemReader,
                                   @Qualifier("jsonToPostgresProcessor") ItemProcessor<User, UserEntity> processor,
                                   @Qualifier("jsonToPostgresWriter") ItemWriter<UserEntity> itemWriter) {

        return this.stepBuilderFactory
                .get("step1")
                .<User, UserEntity>chunk(100)
                .reader(itemReader)
                .processor(processor)
                .writer(itemWriter)
                .build();
    }

    @Bean("jsonToPostgresJob")
    @Autowired
    public Job jsonToPostgresJob(@Qualifier("batchJobExecutionListener") JobExecutionListener listener,
                                 @Qualifier("jsonToPostgresStep") Step step,
                                 @Qualifier("multiThreadedTaskExecutor") ThreadPoolTaskExecutor taskExecutor)
            throws Exception {

        return this.jobBuilderFactory
                .get("jsonToPostgresJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step)
                .build();
    }
}
