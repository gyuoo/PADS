package com.ssafy.s103.gspadsmock.domain.anomalyproduct.shcedule;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AnomalyScheduleQuartz {
    private final Scheduler scheduler;

    public AnomalyScheduleQuartz(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void fetchGsProductSchedule() throws SchedulerException {
        JobDetail gsFetchJob = JobBuilder.newJob(AnomalyScheduleJob.class)
                .withIdentity(AnomalyScheduleJob.JOB_NAME)
                .build();

        Trigger gsFetchJobTrigger = TriggerBuilder.newTrigger()
                .withIdentity(AnomalyScheduleJob.JOB_NAME + "Trigger")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .forJob(gsFetchJob)
                .build();

        scheduler.scheduleJob(gsFetchJob, gsFetchJobTrigger);
    }
}
