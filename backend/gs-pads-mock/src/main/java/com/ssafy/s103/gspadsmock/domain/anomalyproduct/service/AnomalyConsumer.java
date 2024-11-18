package com.ssafy.s103.gspadsmock.domain.anomalyproduct.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.dto.AckMessage;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnomalyConsumer {
    private final AnomalyProductService anomalyProductService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "pads_ack_topic", groupId = "pads-consumer-group")
    public void consumeMessage(ConsumerRecord<String, String> message) {
        try {
            AckMessage ackMessage = objectMapper.readValue(message.value(), AckMessage.class);
            anomalyProductService.updateStatusByBatchId(ackMessage.getBatchId(), AnomalyStatus.PROGRESS);
        } catch (Exception e) {
            log.error("메시지 처리 중 오류 발생: ", e);
        }
    }
}
