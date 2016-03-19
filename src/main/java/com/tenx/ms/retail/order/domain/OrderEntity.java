package com.tenx.ms.retail.order.domain;

import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.order.constants.OrderStatusEnum;
import com.tenx.ms.retail.store.domain.StoreEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false, updatable = false)
    private StoreEntity store;

    @NotNull
    @Column(name="order_date")
    private Date orderDate;

    @NotNull
    @Column(name = "status_id")
    private Integer statusId;

    @OneToMany(mappedBy = "order")
    @NotNull
    private Set<OrderProductEntity> orderProducts;

    @NotNull
    @Column(name = "first_name", length = 50)
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Column(name = "last_name", length = 50)
    @Size(max = 50)
    private String lastName;

    @Email
    @NotNull
    @Column(name = "email", length = 50)
    @Size(max = 50)
    private String email;

    @PhoneNumber
    @NotNull
    @Column(name = "phone", length = 10)
    @Length(max = 10)
    private String phone;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatusEnum getStatus() {
        return OrderStatusEnum.parse(statusId);
    }

    public void setStatus(OrderStatusEnum statusId) {
        this.statusId = statusId.ordinal();
    }

    public Set<OrderProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProductEntity> orderProducts) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderEntity that = (OrderEntity) o;

        if (!store.equals(that.store)) {
            return false;
        }
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) {
            return false;
        }
        if (!statusId.equals(that.statusId)) {
            return false;
        }
        if (!orderProducts.equals(that.orderProducts)) {
            return false;
        }
        if (!firstName.equals(that.firstName)) {
            return false;
        }
        if (!lastName.equals(that.lastName)) {
            return false;
        }
        if (!email.equals(that.email)) {
            return false;
        }
        return phone.equals(that.phone);

    }

    @Override
    public int hashCode() {
        int result = store.hashCode();
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + statusId.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }
}
