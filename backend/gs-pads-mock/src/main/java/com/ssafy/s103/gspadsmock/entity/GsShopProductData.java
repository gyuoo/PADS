package com.ssafy.s103.gspadsmock.entity;


import lombok.Data;

@Data
public class GsShopProductData {
    private String prdId;
    private String viewName;
    private String prdNm;
    private String cate1Nm;
    private String cate2Nm;
    private String cate3Nm;
    private String cate4Nm;
    private String brdName;
    private String supplierCode;
    private String className;
    private String clsCd;
    private Integer orgItemCd;
    private Boolean dealFlag;
    private Boolean tvFlag;
    private Boolean tempoutFlag;
    private Integer price;
    private Integer discprice;
    private Integer buyCount;
    private Integer reviewScore;
    private Integer reviewCount;
    private String createdate;
    private String updatedate;
    private String upsertdate;
    private String prdDiscountDate;
    private String attrCharVal1;
    private String attrCharVal2;
    private String attrCharVal3;
    private String attrCharVal4;
    private String attrCharVal5;
    private String attrCharVal6;
    private String attrCharVal7;
    private String attrCharVal8;
    private String couponDesc;
    private String couponNum;
    private String deliveryCode;
    private String dtctCd;
    private String dlvPickMthodCd;
    private Boolean prdAdultFlag;
}
