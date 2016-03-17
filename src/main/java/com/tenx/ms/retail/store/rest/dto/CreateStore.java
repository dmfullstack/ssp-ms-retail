package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represents the request payload used to create a new store
 */
public class CreateStore {

    @NotEmpty(message = "Name is empty")
    @ApiModelProperty("The store's name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
