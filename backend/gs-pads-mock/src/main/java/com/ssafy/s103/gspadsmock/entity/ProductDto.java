package com.ssafy.s103.gspadsmock.entity;

import lombok.Data;

@Data
public class ProductDto {
    private String prdId;
    private Boolean anomalyFlag;

    public ProductDto(String prdId, Boolean anomalyFlag) {
        this.prdId = prdId;
        this.anomalyFlag = anomalyFlag;
    }

    public ProductDto() {
    }
}
