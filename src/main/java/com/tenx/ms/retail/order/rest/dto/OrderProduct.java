package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

public class OrderProduct {

    @ApiModelProperty("The product id")
    private Long productId;

    @Min(value = 1)
    @ApiModelProperty("The product count")
    private Integer count;

    public OrderProduct() {

    }

    public OrderProduct(Long productId, Integer count) {

        this.productId = productId;
        this.count = count;
    }

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
