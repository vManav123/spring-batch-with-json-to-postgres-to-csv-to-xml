package com.vmanav123.batch.repository;

import com.vmanav123.batch.model.data.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("userDocumentRepository")
public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {
}
