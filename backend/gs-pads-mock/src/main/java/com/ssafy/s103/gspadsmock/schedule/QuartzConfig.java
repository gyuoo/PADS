package com.ssafy.s103.gspadsmock.schedule;

import com.ssafy.s103.gspadsmock.schedule.job.NewProductJob;
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
public class QuartzConfig {
    private final Scheduler scheduler;

    public QuartzConfig(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void scheduleNewProductJob() throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(NewProductJob.JOB_NAME, 1);

        JobDetail jobDetail = JobBuilder.newJob(NewProductJob.class)
                .withIdentity("newProductJob")
                .setJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("newProductTrigger")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?"))
                .forJob(jobDetail)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
