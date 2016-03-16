package com.tenx.ms.retail.repository;

import com.tenx.ms.retail.domain.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findOneByName(final String name);
}
