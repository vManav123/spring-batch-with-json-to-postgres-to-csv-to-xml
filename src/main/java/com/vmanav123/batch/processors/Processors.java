package com.vmanav123.batch.processors;

import com.vmanav123.batch.model.data.UserDocument;
import com.vmanav123.batch.model.data.UserEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Processors {

    @Bean("jsonToPostgresProcessor")
    public ItemProcessor<UserDocument, UserEntity> jsonToPostgresProcessor() {
        return userDocument -> {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userDocument, userEntity);
            return userEntity;
        };
    }

    @Bean("postgresToCsvProcessor")
    public ItemProcessor<UserEntity, UserEntity> postgresToCsvProcessor() {
        return user -> {
            if (user.getAge() < 18) {
                user.setAdultStatus("Very Young and UnMatured");
            }
            else if (user.getAge() >= 18 && user.getAge() <= 45)
                user.setAdultStatus("Young and Matured");
            else
                user.setAdultStatus("Adult and Completely Matured");
            return user;
        };
    }

    @Bean("csvToMongoProcessor")
    public ItemProcessor<UserEntity, UserDocument> csvToMongoProcessor() {
        return userEntity -> {
            UserDocument userDocument = new UserDocument();
            BeanUtils.copyProperties(userEntity,userDocument);

            if(userDocument.getAge()>20 && userDocument.getAdultStatus().contains("UnMatured"))
                userDocument.setCareerStatus("Studying");
            else
                userDocument.setCareerStatus("Working");
            return userDocument;
        };
    }

}
