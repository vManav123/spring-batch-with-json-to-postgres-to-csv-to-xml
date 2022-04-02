package com.vmanav123.batch.jobs.s3Operation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;

@Service("s3Operation")
@Slf4j
public class S3Operation {

    @Autowired
    private AmazonS3 amazonS3;

    @Value(value = "${aws.bucket.name:spring-batch-testing}")
    public final String BUCKET_NAME = "spring-batch-testing";


    @SneakyThrows
    public String uploadFile(final File file) {
        String[] fileName = file.getName().split("\\.");
        String finalFileName = fileName[0] + "_" + LocalDate.now() + "." + fileName[1];
        amazonS3.putObject(new PutObjectRequest(BUCKET_NAME, finalFileName, file));
        return "SuccessFully Uploaded : " + finalFileName + " ✔";
    }

    public InputStreamResource downloadFile(String fileName) {
        S3Object s3Object = amazonS3.getObject(BUCKET_NAME, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        return new InputStreamResource(inputStream.getDelegateStream());
    }


    public String deleteFile(String fileName) {
        amazonS3.deleteObject(BUCKET_NAME, fileName);
        return fileName + " removed ...";
    }
}
