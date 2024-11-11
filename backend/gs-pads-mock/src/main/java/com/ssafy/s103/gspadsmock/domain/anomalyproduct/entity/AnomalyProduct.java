package com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Hidden
@Schema(name = "이상 탐지 상품 데이터")
@Entity
@Table(name = "anomaly_product")
@Builder
@AllArgsConstructor
@Getter
public class AnomalyProduct {
    @Id
    private Long prdId;
    @Column(name = "view_name")
    private String viewName;
    @Column(name = "cate1_nm")
    private String cate1Nm;
    @Column(name = "cate2_nm")
    private String cate2Nm;
    @Column(name = "cate3_nm")
    private String cate3Nm;
    private String supplierCode;
    private String className;
    private Integer price;
    private Integer discPrice;
    private Integer buyCount;
    private Integer reviewScore;
    private Integer reviewCount;
    private Boolean prdAdultFlag;
    private String brdName;
    @Enumerated(EnumType.STRING)
    private AnomalyStatus status;

    public AnomalyProduct() {}

    public static AnomalyProduct gsProductConvertOf(GsShopProduct data) {
        return AnomalyProduct.builder()
                .prdId(Long.parseLong(data.getPrdId()))  // String을 Long으로 변환
                .viewName(data.getViewName())
                .cate1Nm(data.getCate1Nm())
                .cate2Nm(data.getCate2Nm())
                .cate3Nm(data.getCate3Nm())
                .supplierCode(data.getSupplierCode())
                .className(data.getClassName())
                .price(data.getPrice())  // Integer를 Double로 변환
                .discPrice(data.getDiscprice())  // Integer를 Double로 변환
                .buyCount(data.getBuyCount())
                .reviewScore(data.getReviewScore())  // Integer를 Double로 변환
                .reviewCount(data.getReviewCount())
                .prdAdultFlag(data.getPrdAdultFlag())
                .brdName(data.getBrdName())
                .build();
    }
}
