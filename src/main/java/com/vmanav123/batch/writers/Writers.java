package com.vmanav123.batch.writers;

import com.vmanav123.batch.model.data.UserDocument;
import com.vmanav123.batch.model.data.UserEntity;
import com.vmanav123.batch.repository.UserDocumentRepository;
import com.vmanav123.batch.repository.UserEntityRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class Writers {

    @Autowired
    @Qualifier("userDocumentRepository")
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    @Lazy
    private UserEntityRepository userRepository;


    @Bean("postgresItemWriter")
    public ItemWriter<UserEntity> jsonToPostgresWriter() {
        RepositoryItemWriter<UserEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean("mongoItemWriter")
    public ItemWriter<UserDocument> mongoItemWriter() {
        RepositoryItemWriter<UserDocument> writer = new RepositoryItemWriter<>();
        writer.setRepository(userDocumentRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean("csvItemWriter")
    public ItemWriter<UserEntity> csvItemWriter() {
        BeanWrapperFieldExtractor<UserEntity> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]
                {
                        "id",
                        "fullName",
                        "age",
                        "emailAddress",
                        "phoneNumber",
                        "username",
                        "password",
                        "adultStatus"
                }
        );
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<UserEntity> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<UserEntity> writer = new FlatFileItemWriter<>();
        writer.setResource(new ClassPathResource("user.csv"));
        writer.setLineAggregator(lineAggregator);
        return writer;
    }

}
