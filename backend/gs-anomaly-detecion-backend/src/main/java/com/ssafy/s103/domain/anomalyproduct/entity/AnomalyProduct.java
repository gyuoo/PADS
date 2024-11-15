package com.ssafy.s103.domain.anomalyproduct.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Schema(name = "이상 탐지 상품 데이터")
public class AnomalyProduct {

    @Id
    @Column(name = "prd_id")
    private Long prdId;

    @Column(name = "view_name")
    private String viewName;

    @Column(name = "cate1_nm")
    private String cate1Nm;

    @Column(name = "cate2_nm")
    private String cate2Nm;

    @Column(name = "cate3_nm")
    private String cate3Nm;

    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "class_name")
    private String className;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discprice")
    private Integer discprice;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "review_score")
    private Integer reviewScore;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "prd_adult_flag")
    private Boolean prdAdultFlag;

    @Column(name = "brand_name")
    private String brandName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AnomalyStatus status;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @Column(name = "update_dt")
    private LocalDateTime updateDt;

    @Column(name = "batch_id")
    private String batchId;

    public AnomalyProduct() {
    }

}
