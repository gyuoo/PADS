package com.ssafy.s103.domain.anomaly.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnomalyReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "anomaly_log_id", nullable = false)
	private AnomalyLog anomalyLog;

	@Column(nullable = false)
	private String anomalyCode;

	@Column(nullable = false)
	private Integer anomalyScore;

	@Builder
	public AnomalyReport(AnomalyLog anomalyLog, String anomalyCode, Integer anomalyScore) {
		this.anomalyLog = anomalyLog;
		this.anomalyCode = anomalyCode;
		this.anomalyScore = anomalyScore;
	}
}
