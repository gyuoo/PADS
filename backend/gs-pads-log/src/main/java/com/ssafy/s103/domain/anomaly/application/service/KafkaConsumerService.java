package com.ssafy.s103.domain.anomaly.application.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
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
	private final AnomalyTrakingService anomalyTrakingService;
	private final AnomalyService anomalyService;

	@KafkaListener(topics = "${spring.kafka.topic.log-topic}", groupId = "${spring.kafka.consumer.group-id}")
	@RetryableTopic(
		attempts = "5",
		backoff = @Backoff(delay = 1000, multiplier = 2.0),
		dltTopicSuffix = ".dlt"
	)
	public void listenLogRequest(ConsumerRecord<String, String> record) throws Exception {

		AnomalyLogCreateRequest anomalyLogRequest = objectMapper.readValue(record.value(),
			AnomalyLogCreateRequest.class);
		anomalyService.saveAnomalyLog(anomalyLogRequest);
		anomalyTrakingService.filterAndSaveAnomalyCategory(anomalyLogRequest.report(), anomalyLogRequest.prdId());
		log.info("받은 메시지: " + record.value());
	}
}
