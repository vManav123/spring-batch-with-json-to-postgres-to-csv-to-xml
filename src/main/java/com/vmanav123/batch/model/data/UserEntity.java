package com.vmanav123.batch.model.data;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private int age;
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private String password;
    @Transient
    private String adultStatus;
}
