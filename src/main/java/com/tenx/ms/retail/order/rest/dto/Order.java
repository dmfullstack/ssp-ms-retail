package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.retail.order.constants.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

    @ApiModelProperty("The order id (read-only)")
    private Long orderId;

    @ApiModelProperty("The store id this order is for (read-only)")
    private Long storeId;

    @ApiModelProperty("Datetime the order was placed")
    private Date orderDate;

    @ApiModelProperty("The order status")
    private OrderStatusEnum status;

    @ApiModelProperty("The order's products")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @ApiModelProperty("The customer's first name")
    private String firstName;

    @ApiModelProperty("The customer's last name")
    private String lastName;

    @ApiModelProperty("The customer's email")
    private String email;

    @ApiModelProperty("The customer's phone")
    private String phone;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
