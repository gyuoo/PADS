package com.ssafy.s103.domain.anomaly.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnomalyLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productId;

	@CreationTimestamp
	@Column(columnDefinition = "timestamp(0)", updatable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "anomalyLog", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnomalyReport> anomalyReports = new ArrayList<>();

	@Builder
	public AnomalyLog(String productId, List<AnomalyReport> anomalyReports) {
		this.productId = productId;
		if (anomalyReports != null)
			this.anomalyReports = anomalyReports;
	}
}