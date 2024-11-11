package com.ssafy.s103.domain.anomaly.application.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyLogRepository;
import com.ssafy.s103.domain.anomaly.application.repository.AnomalyReportRepository;
import com.ssafy.s103.domain.anomaly.dto.request.AnomalyLogCreateRequest;
import com.ssafy.s103.domain.anomaly.entity.AnomalyLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

	private final AnomalyLogRepository anomalyLogRepository;
	private final AnomalyReportRepository anomalyReportRepository;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = "${spring.kafka.topic.log-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void listenLogRequest(ConsumerRecord<String, String> record) {
		try {
			AnomalyLogCreateRequest anomalyLogRequest = objectMapper.readValue(record.value(), AnomalyLogCreateRequest.class);
			AnomalyLog anomalyLog = anomalyLogRequest.toAnomalyLog();
			anomalyLogRepository.save(anomalyLog);
			anomalyReportRepository.saveAll(anomalyLogRequest.toAnomalyLogDetailList(anomalyLog));

			log.info("받은 메시지: " + record.value());
		} catch (Exception e) {
			log.error("메시지 처리 중 오류 발생: ", e);
		}
	}

}
