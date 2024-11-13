package com.ssafy.s103.gspadsmock.domain.anomalyproduct.shcedule;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.batch.AnomalyProductBatchConfig;
import com.ssafy.s103.gspadsmock.domain.gsproduct.batch.GsProductBatch;
import com.ssafy.s103.gspadsmock.domain.gsproduct.service.RedisTimeService;
import com.ssafy.s103.gspadsmock.global.util.TimeFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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

@RequiredArgsConstructor
@Slf4j
@Getter
@Setter
public class AnomalyScheduleJob extends QuartzJobBean {
    public static final String JOB_NAME = "ANOMALY_SCHEDULE_JOB";
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        JobParameters params = new JobParametersBuilder()
                .addLocalDateTime("job-start-time", LocalDateTime.now())
                .toJobParameters();
        try {
            jobLauncher.run(jobRegistry.getJob(AnomalyProductBatchConfig.PRODUCT_BATCH_NAME), params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | NoSuchJobException e) {
            throw new RuntimeException(e);
        }
    }
}
