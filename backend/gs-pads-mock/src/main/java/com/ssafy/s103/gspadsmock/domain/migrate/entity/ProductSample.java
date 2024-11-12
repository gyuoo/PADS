package com.ssafy.s103.gspadsmock.domain.migrate.entity;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Table(name = "productsample")
@Entity
@Data
public class ProductSample {
    @Id
    @Column(name = "prd_id", length = 255)
    private String prdId;

    @Column(name = "view_name", length = 255)
    private String viewName;

    @Column(name = "prd_nm", length = 255)
    private String prdNm;

    @Column(name = "cate1_nm", length = 255)
    private String cate1Nm;

    @Column(name = "cate2_nm", length = 255)
    private String cate2Nm;

    @Column(name = "cate3_nm", length = 255)
    private String cate3Nm;

    @Column(name = "cate4_nm", length = 255)
    private String cate4Nm;

    @Column(name = "brd_name", length = 255)
    private String brdName;

    @Column(name = "supplier_code", length = 255)
    private String supplierCode;

    @Column(name = "class_name", length = 255)
    private String className;

    @Column(name = "cls_cd", length = 255)
    private String clsCd;

    @Column(name = "org_item_cd")
    private Integer orgItemCd;

    @Column(name = "deal_flag")
    private Boolean dealFlag;

    @Column(name = "tv_flag")
    private Boolean tvFlag;

    @Column(name = "tempout_flag")
    private Boolean tempoutFlag;

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

    @Column(name = "prd_discount_date", length = 255)
    private String prdDiscountDate;

    @Column(name = "attr_char_val_1", length = 255)
    private String attrCharVal1;

    @Column(name = "attr_char_val_2", length = 255)
    private String attrCharVal2;

    @Column(name = "attr_char_val_3", length = 255)
    private String attrCharVal3;

    @Column(name = "attr_char_val_4", length = 255)
    private String attrCharVal4;

    @Column(name = "attr_char_val_5", length = 255)
    private String attrCharVal5;

    @Column(name = "attr_char_val_6", length = 255)
    private String attrCharVal6;

    @Column(name = "attr_char_val_7", length = 255)
    private String attrCharVal7;

    @Column(name = "attr_char_val_8", length = 255)
    private String attrCharVal8;

    @Column(name = "coupon_desc", length = 255)
    private String couponDesc;

    @Column(name = "coupon_num", length = 255)
    private String couponNum;

    @Column(name = "delivery_code", length = 255)
    private String deliveryCode;

    @Column(name = "dtct_cd", length = 255)
    private String dtctCd;

    @Column(name = "dlv_pick_mthod_cd", length = 255)
    private String dlvPickMthodCd;

    @Column(name = "prd_adult_flag")
    private Boolean prdAdultFlag;

    public ProductSample() {
    }

    public GsShopProduct toGsShopProduct() {
        return GsShopProduct.builder()
                .prdId(this.prdId)
                .viewName(this.viewName)
                .prdNm(this.prdNm)
                .cate1Nm(this.cate1Nm)
                .cate2Nm(this.cate2Nm)
                .cate3Nm(this.cate3Nm)
                .cate4Nm(this.cate4Nm)
                .brdName(this.brdName)
                .supplierCode(this.supplierCode)
                .className(this.className)
                .clsCd(this.clsCd)
                .orgItemCd(this.orgItemCd)
                .dealFlag(this.dealFlag)
                .tvFlag(this.tvFlag)
                .tempoutFlag(this.tempoutFlag)
                .price(this.price)
                .discprice(this.discprice)
                .buyCount(this.buyCount)
                .reviewScore(this.reviewScore)
                .reviewCount(this.reviewCount)// LocalDateTime to String
                .prdDiscountDate(this.prdDiscountDate)
                .attrCharVal1(this.attrCharVal1)
                .attrCharVal2(this.attrCharVal2)
                .attrCharVal3(this.attrCharVal3)
                .attrCharVal4(this.attrCharVal4)
                .attrCharVal5(this.attrCharVal5)
                .attrCharVal6(this.attrCharVal6)
                .attrCharVal7(this.attrCharVal7)
                .attrCharVal8(this.attrCharVal8)
                .couponDesc(this.couponDesc)
                .couponNum(this.couponNum)
                .deliveryCode(this.deliveryCode)
                .dtctCd(this.dtctCd)
                .dlvPickMthodCd(this.dlvPickMthodCd)
                .prdAdultFlag(this.prdAdultFlag)
                .build();
    }

}
