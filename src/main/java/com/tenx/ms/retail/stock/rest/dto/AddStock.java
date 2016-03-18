package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Represents the request payload used to add stock
 */
public class AddStock {

    @NotNull
    @ApiModelProperty("The product id")
    private Long productId;

    @NotNull
    @ApiModelProperty("The store id")
    private Long storeId;

    @NotNull
    @ApiModelProperty("The stock count to add")
    private Integer count;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
