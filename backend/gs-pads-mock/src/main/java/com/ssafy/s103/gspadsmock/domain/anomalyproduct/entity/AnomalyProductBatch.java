package com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity;

import com.ssafy.s103.gspadsmock.global.util.UniqueIDGenerator;
import jakarta.persistence.Column;
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

    @Column(name = "s3_url")
    private String s3Url;
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

    public void updateUrl(String s3Url){
        this.s3Url = s3Url;
    }
}
