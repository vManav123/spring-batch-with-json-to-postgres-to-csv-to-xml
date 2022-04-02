package com.vmanav123.batch.model.data;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String fullName;
    private int age;
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private String password;
}
