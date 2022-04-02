package com.vmanav123.batch.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component("batchJobExecutionListener")
@Slf4j
public class BatchJobExecutionListener extends JobExecutionListenerSupport {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        log.info("Job Name : "+jobExecution.getJobInstance().getJobName()+" is Started!!!");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job Name : "+jobExecution.getJobInstance().getJobName()+" is COMPLETED!!!");
        }
        else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("Job Name : "+jobExecution.getJobInstance().getJobName()+" is FAILED!!!");
        }
    }
}
