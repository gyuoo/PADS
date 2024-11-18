package com.ssafy.s103.gspadsmock.domain.anomalyproduct.service;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.dto.AckMessage;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyStatus;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnomalyProductService {
    private final String TOPIC_NAME = "pads_product_topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AnomalyProductRepository anomalyProductRepository;
    public void sendMessage(Object message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    public void updateStatusByBatchId(String batchId, AnomalyStatus status) {
        anomalyProductRepository.updateAnomalyStatusByBatchId(batchId, status);
    }
}
