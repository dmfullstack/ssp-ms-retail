package com.tenx.ms.retail.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store")
    private Long productId;

    //TODO: product can be at many stores
    //TODO: check that store id exist
//    @ManyToMany
//    @JoinTable(
//            name="product_stores",
//            joinColumns = {@JoinColumn(name = "store_id")}
//    )
    @NotNull
    @Column(name = "store_id")
    private Long storeId;

    //TODO: make name unique?
    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    //TODO: make sku unique?
    @NotNull
    @Size(min = 5, max = 10)
    @Column(name = "sku", length = 10)
    private String sku;

    @NotNull
    @Column(name = "price", scale = 2)
    private Double price;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductEntity that = (ProductEntity) o;

        if (!storeId.equals(that.storeId)) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (!sku.equals(that.sku)) {
            return false;
        }
        return price.equals(that.price);

    }

    @Override
    public int hashCode() {
        int result = storeId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + sku.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
