package com.ssafy.s103.gspadsmock.domain.anomalyproduct.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnomalyProductService {
    private final String TOPIC_NAME = "pads_product_topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }
}
