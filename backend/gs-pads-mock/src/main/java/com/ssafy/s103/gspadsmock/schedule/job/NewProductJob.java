package com.ssafy.s103.gspadsmock.schedule.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@Slf4j
@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
public class NewProductJob implements Job {
    public static String FIRST_KEY = "job1";
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        int tmpIdx = map.getInt(FIRST_KEY);
        log.info("One : {} 번째 실행", tmpIdx);
        map.put(FIRST_KEY, tmpIdx + 1);
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("One : {} 번째 완료", tmpIdx);
    }
}
