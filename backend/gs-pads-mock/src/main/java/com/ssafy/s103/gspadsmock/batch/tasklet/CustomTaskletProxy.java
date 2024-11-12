package com.ssafy.s103.gspadsmock.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class CustomTaskletProxy implements CustomTasklet {
    private final CustomTasklet targetTasklet;

    public CustomTaskletProxy(CustomTasklet targetTasklet) {
        this.targetTasklet = targetTasklet;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("Starting execution of Tasklet: {}", targetTasklet.getClass().getSimpleName());

        RepeatStatus status = targetTasklet.execute(contribution, chunkContext);

        long endTime = System.currentTimeMillis();
        log.info("Completed execution of Tasklet: {} in {} ms", targetTasklet.getClass().getSimpleName(), (endTime - startTime));

        return status;
    }
}
