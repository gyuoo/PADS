package com.ssafy.s103.gspadsmock.domain.gsproduct.schedule;

import com.ssafy.s103.gspadsmock.domain.gsproduct.batch.GsProductBatch;
import com.ssafy.s103.gspadsmock.domain.gsproduct.service.RedisTimeService;
import com.ssafy.s103.gspadsmock.global.util.TimeFormat;
import java.sql.BatchUpdateException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Getter
@Setter
public class GsShopProductJob extends QuartzJobBean {
    public static final String JOB_NAME = "GS_NEW_PRODUCT_FETCH";

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;
    private final RedisTimeService redisTimeService;

    public GsShopProductJob(JobLauncher jobLauncher, JobRegistry jobRegistry, RedisTimeService redisTimeService) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
        this.redisTimeService = redisTimeService;
    }

    @Retryable(
            value = {CannotAcquireLockException.class, BatchUpdateException.class},  // PostgreSQL 충돌 예외
            maxAttempts = 3,  // 최대 3회 재시도
            backoff = @Backoff(delay = 1000)  // 1초 지연 후 재시도
    )
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobParameters params = new JobParametersBuilder()
                .addLocalDateTime(JOB_NAME,LocalDateTime.now())
                .toJobParameters();
        try {
            jobLauncher.run(jobRegistry.getJob(GsProductBatch.GS_PRODUCT_FETCH_BATCH), params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | NoSuchJobException e) {
            throw new RuntimeException(e);
        }
    }
}
