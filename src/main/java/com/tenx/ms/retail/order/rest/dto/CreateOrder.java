package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.order.constants.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the request payload used to create a new order
 */
public class CreateOrder {

    @ApiModelProperty("The store this order is for")
    @NotNull(message = "The store id is empty")
    private Long storeId;

    @ApiModelProperty("Datetime the order was placed")
    private Date orderDate;

    @ApiModelProperty("The order status")
    private OrderStatusEnum status;

    @ApiModelProperty("The order's products")
    @NotNull
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @ApiModelProperty("The customer's first name")
    @NotNull
    @Length(max=50)
    private String firstName;

    @ApiModelProperty("The customer's last name")
    @NotNull
    @Length(max=50)
    private String lastName;

    @ApiModelProperty("The customer's email")
    @Email
    @NotNull
    @Length(max=50)
    private String email;

    @ApiModelProperty("The customer's phone")
    @PhoneNumber
    @Length(max = 10)
    private String phone;

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
