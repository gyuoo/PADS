package com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity;

import com.ssafy.s103.gspadsmock.global.util.UniqueIDGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Table(name = "anomaly_product_batch")
@Getter
@Entity
public class AnomalyProductBatch {
    @Id
    private String id;

    private LocalDateTime createDt;

    public AnomalyProductBatch() {
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UniqueIDGenerator.generateUniqueId();
        }
        this.createDt = LocalDateTime.now();
    }
}
