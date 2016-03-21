package com.tenx.ms.retail.product.domain;

import com.tenx.ms.retail.store.domain.StoreEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "products_stores",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "store_id", referencedColumnName = "store_id")})
    private Set<StoreEntity> stores = new HashSet<>();

    //TODO: unique on store id and product name
    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    //TODO: unique on store id and sku
    @NotNull
    @Size(min = 5, max = 10)
    @Column(name = "sku", length = 10)
    private String sku;

    @NotNull
    @Column(name = "price", scale = 2)
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Set<StoreEntity> getStores() {
        return stores;
    }

    public void setStores(Set<StoreEntity> stores) {
        this.stores = stores;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

        if (!stores.equals(that.stores)) {
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
        int result = stores.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + sku.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
