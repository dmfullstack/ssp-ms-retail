package com.tenx.ms.retail.stock.repository;

import com.tenx.ms.retail.stock.domain.StockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRespository extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findOneByStoreStoreIdAndProductProductId(final Long storeId, final Long productId);

    Page<StockEntity> findByStoreStoreId(final Long storeId, Pageable pageable);

}
