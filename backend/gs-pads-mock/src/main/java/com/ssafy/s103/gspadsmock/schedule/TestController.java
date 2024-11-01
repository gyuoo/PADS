package com.ssafy.s103.gspadsmock.schedule;

import com.ssafy.s103.gspadsmock.schedule.batch.ProductBatch;
import com.ssafy.s103.gspadsmock.schedule.entity.Product;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public TestController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @GetMapping("/test")
    public ResponseEntity<Boolean> batchTest(@RequestParam("batchId") String batchId)
            throws NoSuchJobException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder().addString("date", batchId).toJobParameters();

        jobLauncher.run(jobRegistry.getJob(ProductBatch.PRODUCT_BATCH_NAME), jobParameters);

        return ResponseEntity.ok(true);
    }
}
