package com.ssafy.s103.gspadsmock.domain.gsproduct.schedule;

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
public class GsShopProductQuartz {
    private final Scheduler scheduler;

    public GsShopProductQuartz(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void fetchGsProductSchedule() throws SchedulerException {
        JobDetail gsFetchJob = JobBuilder.newJob(GsShopProductJob.class)
                .withIdentity(GsShopProductJob.JOB_NAME)
                .build();

        Trigger gsFetchJobTrigger = TriggerBuilder.newTrigger()
                .withIdentity(GsShopProductJob.JOB_NAME + "Trigger")
                .startNow()
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?"))
                .forJob(gsFetchJob)
                .build();

        scheduler.scheduleJob(gsFetchJob, gsFetchJobTrigger);
    }
}
