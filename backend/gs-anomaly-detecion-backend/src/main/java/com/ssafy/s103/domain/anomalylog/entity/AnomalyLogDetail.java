package com.ssafy.s103.domain.anomalylog.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Hidden
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnomalyLogDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "anomaly_log_id", nullable = false)
	private AnomalyLog anomalyLog;

	private String code;

	private String subCode;

	private Integer score;

	private String message;

	@Builder
	public AnomalyLogDetail(AnomalyLog anomalyLog, String code, String subCode, Integer score, String message) {
		this.anomalyLog = anomalyLog;
		this.code = code;
		this.subCode = subCode;
		this.score = score;
		this.message = message;
	}
}
