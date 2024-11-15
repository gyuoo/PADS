package com.ssafy.s103.domain.batch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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

    public void updateUrl(String s3Url) {
        this.s3Url = s3Url;
    }
}