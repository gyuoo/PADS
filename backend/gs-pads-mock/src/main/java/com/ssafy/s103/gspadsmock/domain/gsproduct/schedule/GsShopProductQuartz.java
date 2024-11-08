package com.ssafy.s103.gspadsmock.domain.gsproduct.schedule;

import com.ssafy.s103.gspadsmock.global.schedule.job.NewProductJob;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
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
    public void scheduleNewProductJob() throws SchedulerException {
        JobDetail gsFetchJob = JobBuilder.newJob(GsShopProductJob.class)
                .withIdentity(GsShopProductJob.JOB_NAME)
                .build();

        Trigger gsFetchJobTrigger = TriggerBuilder.newTrigger()
                .withIdentity(GsShopProductJob.JOB_NAME + "Trigger")
                .startNow()
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?"))
                .forJob(gsFetchJob)
                .build();


//        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(gsFetchJob, gsFetchJobTrigger);
    }
}
