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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class QuartzConfig {
    private final Scheduler scheduler;

    public QuartzConfig(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

//    @Bean
//    public JobDetail findNewProduct() {
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.put(NewProductJob.FIRST_KEY, 1);
//        return JobBuilder.newJob(NewProductJob.class)
//                .withIdentity("newProductJob")
//                .setJobData(jobDataMap)
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger findNewProductTrigger(JobDetail findNewProduct) {
//        return TriggerBuilder.newTrigger()
//                .withIdentity("newProductTrigger")
//                .forJob(findNewProduct)
//                .startNow()
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
//                .build();
//    }


    @PostConstruct
    public void scheduleNewProductJob() throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(NewProductJob.FIRST_KEY, 1);

        JobDetail jobDetail = JobBuilder.newJob(NewProductJob.class)
                .withIdentity("newProductJob")
                .setJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("newProductTrigger")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .forJob(jobDetail)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
