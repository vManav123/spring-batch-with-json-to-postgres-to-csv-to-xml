package com.vmanav123.batch.readers;

import com.vmanav123.batch.jobs.s3Operation.S3Operation;
import com.vmanav123.batch.model.data.UserDocument;
import com.vmanav123.batch.model.data.UserEntity;
import com.vmanav123.batch.repository.UserEntityRepository;
import lombok.SneakyThrows;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.HashMap;

import static com.vmanav123.batch.utility.AppUtility.*;


@Configuration
public class Reader {

    @Autowired
    @Qualifier("s3Operation")
    private S3Operation s3Operation;

    @Autowired
    @Lazy
    private UserEntityRepository userRepository;


    @Bean("jsonItemReader")
    @Lazy
    public ItemReader<UserDocument> jsonToPostgresReader() {
        return new JsonItemReaderBuilder<UserDocument>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(UserDocument.class))
                .resource(s3Operation.downloadFile(USER+"_"+LocalDate.now()+JSON_EXTENSION))
                .name("userJsonItemReader")
                .build();
    }

    @Bean("postgresItemReader")
    public ItemReader<UserEntity> postgresToCsvReader() {
        RepositoryItemReader<UserEntity> reader = new RepositoryItemReader<>();
        reader.setRepository(userRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(100);
        final HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        reader.setSort(sorts);
        return reader;
    }


    @SneakyThrows
    @Bean("csvItemReader")
    @Lazy
    public FlatFileItemReader<UserEntity> csvItemReader()
    {
        FlatFileItemReader<UserEntity> reader = new FlatFileItemReader<>();
        reader.setResource(s3Operation.downloadFile(USER+"_"+ LocalDate.now()+CSV_EXTENSION));
        reader.setLineMapper(new DefaultLineMapper<>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("id",
                                "fullName",
                                "age",
                                "emailAddress",
                                "phoneNumber",
                                "username",
                                "password",
                                "adultStatus");
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(UserEntity.class);
                    }
                });
            }
        });
        return reader;
    }
}
