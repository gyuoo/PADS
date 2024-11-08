package com.ssafy.s103.gspadsmock.domain.gsproduct.schedule;

import com.ssafy.s103.gspadsmock.domain.gsproduct.batch.GsProductBatch;
import com.ssafy.s103.gspadsmock.domain.gsproduct.service.RedisTimeService;
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
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@Getter
@Setter
public class GsShopProductJob extends QuartzJobBean {
    public static final String JOB_NAME = "GS_NEW_PRODUCT_FETCH";
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;
    private final RedisTimeService redisTimeService;

    public GsShopProductJob(JobLauncher jobLauncher, JobRegistry jobRegistry, RedisTimeService redisTimeService) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
        this.redisTimeService = redisTimeService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime startDt = redisTimeService.getStartTime();
        if (startDt == null || startDt.isAfter(LocalDateTime.now())) {
//            startDt = LocalDateTime.now().withMinute((LocalDateTime.now().getMinute() / 5) * 5).withSecond(0).withNano(0);
            startDt = LocalDateTime.now().withMinute((LocalDateTime.now().getMinute() / 2) * 2).withSecond(0).withNano(0);
            redisTimeService.updateStartTime(startDt);
        }
        LocalDateTime endDt = startDt.plusMinutes(2).minusSeconds(1L);
//        LocalDateTime endDt = startDt.plusMinutes(2).minusSeconds(1L);

        redisTimeService.updateStartTime(startDt);

        JobParameters params = new JobParametersBuilder()
                .addString("startDt", startDt.format(FORMATTER))
                .addString("endDt", endDt.format(FORMATTER))
                .toJobParameters();
        try {
            jobLauncher.run(jobRegistry.getJob(GsProductBatch.GS_PRODUCT_FETCH_BATCH), params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | NoSuchJobException e) {
            throw new RuntimeException(e);
        }
    }
}
