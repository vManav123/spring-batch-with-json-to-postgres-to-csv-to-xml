package com.vmanav123.batch.job.s3Operation;

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

@Service("s3Operation")
@Slf4j
public class S3Operation {

    @Autowired
    private AmazonS3 amazonS3;

    @Value(value = "${aws.bucket.name:spring-batch-testing}")
    public final String BUCKET_NAME = "spring-batch-testing";


    @SneakyThrows
    public String uploadFile(final File file) {
        String fileName = file.getName()+ "_" + System.currentTimeMillis();
        amazonS3.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file));

//        ClassLoader classLoader = getClass().getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream(TEMP_FILE_PATH+fileName);
//        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME,BUCKET_KEY,inputStream,null);
//        amazonS3.putObject(putObjectRequest);
        return "SuccessFully Uploaded : "+fileName+" ✔";
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
