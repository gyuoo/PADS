package com.ssafy.s103.gspadsmock.batch.tasklet;

import com.ssafy.s103.gspadsmock.schedule.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class JpaCustomTasklet implements CustomTasklet {
    private final ProductRepository productRepository;

    public JpaCustomTasklet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

//        List<Product> productList = productRepository.findAllByAnomalyFlagFalse();
//        List<ProductDto> productList = productRepository.findAllByAnomalyFlagFalse();

//        log.info("size : {}", productList.size());
        return null;
    }
}
