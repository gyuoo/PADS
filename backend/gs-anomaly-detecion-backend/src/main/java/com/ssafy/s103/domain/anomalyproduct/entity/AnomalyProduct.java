package com.ssafy.s103.domain.anomalyproduct.entity;

import com.ssafy.s103.domain.gsproduct.entity.GsShopProduct;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Hidden
@Entity
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

    @Column(name = "disc_price")
    private Integer discPrice;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "review_score")
    private Integer reviewScore;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "prd_adult_flag")
    private Boolean prdAdultFlag;

    @Column(name = "brd_name")
    private String brdName;

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

    public static AnomalyProduct gsProductConvertOf(GsShopProduct data, LocalDateTime fetchTime) {
        fetchTime = fetchTime.withMinute((fetchTime.getMinute() / 5) * 5).withSecond(0).withNano(0);

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
            .createDt(fetchTime)
            .updateDt(fetchTime)
            .build();
    }
}