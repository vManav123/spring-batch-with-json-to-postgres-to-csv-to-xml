package com.vmanav123.batch.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("jsonToPostgresJob")
    private Job jsonToPostgresJob;


    @GetMapping(path = "/jsonToPostgres")
    @SneakyThrows
    public String jsonToPostgresJobLauncher(@RequestParam("fileName") String fileName) {
        DateTime dateTime = DateTime.now();
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("startAt" , dateTime.toDate())
                .addString("fileName" , fileName)
                .toJobParameters();
        log.info("Job is Starting with this name : {}",jsonToPostgresJob.getName());
        return"Job ended with status : "+jobLauncher.run(jsonToPostgresJob , jobParameters).getStatus().getBatchStatus()+" at "+DateTime.now();
    }
}
