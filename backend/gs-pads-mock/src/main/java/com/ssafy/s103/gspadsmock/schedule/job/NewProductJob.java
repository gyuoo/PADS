package com.ssafy.s103.gspadsmock.schedule.job;

import com.ssafy.s103.gspadsmock.batch.ProductBatch;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Getter
@Setter
public class NewProductJob extends QuartzJobBean {
    public static String JOB_NAME = "NEW_PRODUCT_CREATE";
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public NewProductJob(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())  // 매번 다른 파라미터로 새로운 Job 실행
                    .toJobParameters();

            jobLauncher.run(jobRegistry.getJob(ProductBatch.PRODUCT_BATCH_NAME), jobParameters);
        } catch (Exception e) {
            log.info("error : {}", e);
        }
    }
}
