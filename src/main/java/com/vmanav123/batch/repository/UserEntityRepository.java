package com.vmanav123.batch.repository;

import com.vmanav123.batch.model.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userEntityRepository")
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
}
