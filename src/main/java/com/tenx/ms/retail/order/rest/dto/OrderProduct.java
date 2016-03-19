package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModelProperty;

public class OrderProduct {

    @ApiModelProperty("The product id")
    private Long productId;

    @ApiModelProperty("The product count")
    private Integer count;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
