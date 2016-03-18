package com.tenx.ms.retail.stock.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "stocks", uniqueConstraints=@UniqueConstraint(columnNames={"store_id", "product_id"}))
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_id")
    private Long stockId;

    @OneToOne(optional=false)
    @JoinColumn(name="product_id", nullable=false, updatable=false)
    private ProductEntity product;

    @ManyToOne(optional=false)
    @JoinColumn(name="store_id", nullable=false, updatable=false)
    private StoreEntity store;

    @NotNull
    private Integer count;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockEntity that = (StockEntity) o;

        if (!product.equals(that.product)) {
            return false;
        }
        if (!store.equals(that.store)) {
            return false;
        }
        return count.equals(that.count);

    }

    @Override
    public int hashCode() {
        int result = product.hashCode();
        result = 31 * result + store.hashCode();
        result = 31 * result + count.hashCode();
        return result;
    }
}
