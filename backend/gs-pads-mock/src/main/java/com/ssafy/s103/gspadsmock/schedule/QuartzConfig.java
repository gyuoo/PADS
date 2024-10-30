package com.ssafy.s103.gspadsmock.schedule;

import com.ssafy.s103.gspadsmock.schedule.job.NewProductJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail findNewProduct() {
        return JobBuilder.newJob(NewProductJob.class)
                .withIdentity("newProductJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger findNewProductTrigger(JobDetail findNewProduct) {
        return TriggerBuilder.newTrigger()
                .withIdentity("newProductTrigger")
                .forJob(findNewProduct)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */5 * * * ?"))
                .build();
    }

//    @Bean
//    public Scheduler scheduler(JobDetail findNewProduct, Trigger findNewProductTrigger) throws SchedulerException {
//        SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler();
//        scheduler.scheduleJob(findNewProduct, findNewProductTrigger); // Job과 Trigger를 명시적으로 스케줄링
//        scheduler.start();
//        return scheduler;
//    }
}
