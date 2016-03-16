package com.tenx.ms.retail.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represents the request payload used to create a new store
 */
public class CreateStore {

    @NotEmpty(message = "Name is empty")
    @ApiModelProperty("The store's name")
    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
