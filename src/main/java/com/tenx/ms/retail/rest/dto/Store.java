package com.tenx.ms.retail.rest.dto;

import io.swagger.annotations.ApiModelProperty;

public class Store {

    @ApiModelProperty("The store id (read-only)")
    private long storeId;

    @ApiModelProperty("The store name")
    private String name;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
