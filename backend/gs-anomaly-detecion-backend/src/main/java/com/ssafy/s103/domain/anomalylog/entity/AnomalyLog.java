package com.ssafy.s103.domain.anomalylog.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Hidden
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnomalyLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long prdId;

	@Column(nullable = false)
	private Integer totalScore;

	@CreationTimestamp
	@Column(columnDefinition = "timestamp(0)", updatable = false)
	private LocalDateTime createdAt;

	@Builder
	public AnomalyLog(Long prdId, Integer totalScore) {
		this.prdId = prdId;
		this.totalScore = totalScore;
	}
}