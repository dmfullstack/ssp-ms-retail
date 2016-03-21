package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.retail.order.constants.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus {

    @ApiModelProperty("The order id")
    private Long orderId;

    @ApiModelProperty("Order status")
    private OrderStatusEnum status;

    @ApiModelProperty("List of ordered products")
    private List<OrderProduct> orderedProducts = new ArrayList<>();

    @ApiModelProperty("Back ordered products")
    private List<OrderProduct> backorderedProducts = new ArrayList<>();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public List<OrderProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public List<OrderProduct> getBackorderedProducts() {
        return backorderedProducts;
    }

    public void setBackorderedProducts(List<OrderProduct> backorderedProducts) {
        this.backorderedProducts = backorderedProducts;
    }
}
