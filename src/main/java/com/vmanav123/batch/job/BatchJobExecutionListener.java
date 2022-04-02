package com.vmanav123.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component("batchJobExecutionListener")
@Slf4j
public class BatchJobExecutionListener extends JobExecutionListenerSupport {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        log.info("Job Name : "+jobExecution.getJobConfigurationName()+" is Started!!!");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job Name : "+jobExecution.getJobConfigurationName()+" is COMPLETED!!!");
        }
        else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("Job Name : "+jobExecution.getJobConfigurationName()+" is FAILED!!!");
        }
    }
}
