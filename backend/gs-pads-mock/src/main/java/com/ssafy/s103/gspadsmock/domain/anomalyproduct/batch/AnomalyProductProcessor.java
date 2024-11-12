package com.ssafy.s103.gspadsmock.domain.anomalyproduct.batch;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProductBatch;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductBatchRepository;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class AnomalyProductProcessor implements ItemProcessor<AnomalyProduct, AnomalyProduct> {
    private final AnomalyProductBatchRepository productBatchRepository;
    private final AnomalyProductRepository productRepository;
    private final List<AnomalyProduct> productsToUpdate = new ArrayList<>();
    private AnomalyProductBatch batch;

    public AnomalyProductProcessor(AnomalyProductBatchRepository productBatchRepository,
                                   AnomalyProductRepository productRepository) {
        this.productBatchRepository = productBatchRepository;
        this.productRepository = productRepository;
    }

    @Override
    public AnomalyProduct process(AnomalyProduct item) {
        productsToUpdate.add(item);
        return item;
    }

    @AfterStep
    public void updateProduct(StepExecution stepExecution) {

        if (!productsToUpdate.isEmpty()) {
            this.batch = productBatchRepository.save(new AnomalyProductBatch());
            log.info("Batch ID : {}", this.batch.getId());
            productRepository.bulkUpdateBatchId(this.productsToUpdate, this.batch.getId());
        }
    }

}
