package com.ssafy.s103.gspadsmock.domain.gsproduct.batch;

import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTasklet;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class GsProductBatch {
    public static final String GS_PRODUCT_FETCH_BATCH = "fetchGsProduct";
    private final JobRepository jobRepository;
    private final CustomTasklet gsProductTask;
    private final PlatformTransactionManager platformTransactionManager;


    public GsProductBatch(JobRepository jobRepository, CustomTasklet gsProductTask,
                          PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.gsProductTask = gsProductTask;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public Job gsProductJob() {
        return new JobBuilder(GS_PRODUCT_FETCH_BATCH, jobRepository)
                .start(gsProductStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step gsProductStep() {
        return new StepBuilder(GS_PRODUCT_FETCH_BATCH + "Step1", jobRepository)
                .tasklet(gsProductTask, platformTransactionManager)
                .build();
    }
}
