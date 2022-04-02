package com.vmanav123.batch.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document(collection = "user")
public class UserDocument {
    @Id
    private String objectId;
    private Long id;
    private String fullName;
    private int age;
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private String password;
    private String adultStatus;
    private String careerStatus;
}
