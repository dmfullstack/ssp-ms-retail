package com.tenx.ms.retail.product.repository;

import com.tenx.ms.retail.product.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByStoreId(final Long storeId, Pageable pageable);

    Optional<ProductEntity> findOneByStoreIdAndProductId(final Long storeId, final Long productId);

    Optional<ProductEntity> findOneByStoreIdAndName(final Long storeId, final String name);

}
