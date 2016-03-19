package com.tenx.ms.retail.order.repository;

import com.tenx.ms.retail.order.domain.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

    List<OrderProductEntity> findOrderProductByOrderOrderId(final Long orderId);
}
