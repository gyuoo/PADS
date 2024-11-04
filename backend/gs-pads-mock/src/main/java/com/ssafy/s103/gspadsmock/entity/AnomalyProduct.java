package com.ssafy.s103.gspadsmock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "anomaly_product")
@Getter
public class AnomalyProduct {
    @Id
    private Long prdId;
    private String viewName;
    private String cate1Nm;
    private String cate2Nm;
    private String cate3Nm;
    private String supplierCode;
    private String className;
    private Double price;
    private Double discPrice;
    private Integer buyCount;
    private Double reviewScore;
    private Integer reviewCount;
    private Boolean prdAdultFlag;
    private String brdName;
}
