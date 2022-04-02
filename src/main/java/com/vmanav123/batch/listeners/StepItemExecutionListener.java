package com.vmanav123.batch.listeners;

import com.vmanav123.batch.jobs.s3Operation.S3Operation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component("stepItemExecutionListener")
public class StepItemExecutionListener implements StepExecutionListener {

    @Autowired
    private S3Operation s3Operation;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    @SneakyThrows
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(stepExecution.getExitStatus().equals(ExitStatus.COMPLETED))
        {
            Resource resource = new ClassPathResource("user.csv");
            String result = s3Operation.uploadFile(resource.getFile());
            log.info("CSV File is {} ",result);
            Files.writeString(Path.of(resource.getFile().getAbsolutePath()),"");
        }
        return stepExecution.getExitStatus();
    }
}
