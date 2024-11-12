package com.ssafy.s103.domain.anomaly.application.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.domain.anomaly.dto.request.AnomalyLogCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

	private final ObjectMapper objectMapper;
	private final RedisService redisService;
	private final AnomalyService anomalyService;

	@KafkaListener(topics = "${spring.kafka.topic.log-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void listenLogRequest(ConsumerRecord<String, String> record) {
		try {
			AnomalyLogCreateRequest anomalyLogRequest = objectMapper.readValue(record.value(),
				AnomalyLogCreateRequest.class);
			anomalyService.saveAnomalyLog(anomalyLogRequest);
			redisService.filterAndSaveAnomalyCategory(anomalyLogRequest.report(), anomalyLogRequest.productId());
			log.info("받은 메시지: " + record.value());
		} catch (Exception e) {
			log.error("메시지 처리 중 오류 발생: ", e);
		}
	}

}
