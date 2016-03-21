package com.tenx.ms.retail.order.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.order.constants.OrderStatusEnum;
import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.order.repository.OrderProductRepository;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.CreateOrder;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderProduct;
import com.tenx.ms.retail.order.rest.dto.OrderStatus;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRespository;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("PMD")
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private StockRespository stockRespository;

    public OrderStatus createOrder(CreateOrder order) {

        OrderEntity orderEntity = convertToEntity(order);
        Long id = orderRepository.save(orderEntity).getOrderId();
        orderProductRepository.save(orderEntity.getOrderProducts());

        OrderStatus status = new OrderStatus();
        status.setStatus(OrderStatusEnum.PACKING);
        status.setOrderId(id);

        Optional<StockEntity> stockEntity;
        StockEntity stock;
        int count;
        Long productId;
        for(OrderProductEntity o : orderEntity.getOrderProducts()) {
            stockEntity = stockRespository.findOneByStoreStoreIdAndProductProductId(order.getStoreId(), o.getProduct().getProductId());

            if(stockEntity.isPresent()) {
                stock = stockEntity.get();
                productId = o.getProduct().getProductId();

                if(o.getCount() > stock.getCount()) {
                    count = 0;
                    status.getOrderedProducts().add(new OrderProduct(productId, stock.getCount()));
                    status.getBackorderedProducts().add(new OrderProduct(productId, o.getCount() - stock.getCount()));
                } else {
                    count = stock.getCount() - o.getCount();
                    status.getOrderedProducts().add(new OrderProduct(productId, o.getCount()));
                }
                stock.setCount(count);
                stockRespository.save(stock);
            }

        }

        return status;
    }

    /**
     * Retrieves all orders for a store
     * @param storeId the store id
     * @param pageable the pageable options used for paginated results
     * @param baseLinkPath the base path to the resource used for pagination link generation
     * @return Paginated Iterable of orders
     */
    public Paginated<Order> getStoreOrders(Long storeId, Pageable pageable, String baseLinkPath) {
        Page<OrderEntity> page = orderRepository.findByStoreStoreId(storeId, pageable);

        List<Order> orders = page.getContent().stream()
                .map(order -> convertToDTO(order))
                .collect(Collectors.toList());

        return Paginated.wrap(page, orders, baseLinkPath);
    }

    private OrderEntity convertToEntity(CreateOrder order) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPhone(order.getPhone());
        orderEntity.setStatus(order.getStatus());

        Date orderDate = order.getOrderDate();
        if(order.getOrderDate() == null) {
            orderDate = Calendar.getInstance().getTime();
        }
        orderEntity.setOrderDate(orderDate);

        orderEntity.setEmail(order.getEmail());
        orderEntity.setFirstName(order.getFirstName());
        orderEntity.setLastName(order.getLastName());

        StoreEntity store = storeRepository.findOneByStoreId(order.getStoreId()).get();
        orderEntity.setStore(store);

        Set<OrderProductEntity> orderProductSet = order.getOrderProducts().stream()
                .map(orderProduct->convertOrderProductToEntity(orderEntity, orderProduct))
                .collect(Collectors.toSet());
        orderEntity.setOrderProducts(orderProductSet);

        return orderEntity;

    }

    private Order convertToDTO(OrderEntity order) {

        Order o = new Order();
        o.setStoreId(order.getStore().getStoreId());
        o.setEmail(order.getEmail());
        o.setFirstName(order.getFirstName());
        o.setLastName(order.getLastName());
        o.setOrderDate(order.getOrderDate());
        o.setOrderId(order.getOrderId());

        Set<OrderProduct> orderProductSet = order.getOrderProducts().stream()
                .map(orderProduct->convertOrderProductEntityToDTO(orderProduct))
                .collect(Collectors.toSet());
        o.setOrderProducts(orderProductSet);

        o.setStatus(order.getStatus());
        o.setPhone(order.getPhone());

        return o;
    }

    private OrderProductEntity convertOrderProductToEntity(OrderEntity orderEntity, OrderProduct orderProduct) {

        OrderProductEntity p = new OrderProductEntity();
        p.setCount(orderProduct.getCount());

        ProductEntity productEntity = productRepository.findByStoresStoreIdAndProductId(orderEntity.getStore().getStoreId(), orderProduct.getProductId()).get();
        p.setProduct(productEntity);
        p.setOrder(orderEntity);

        return p;
    }

    private OrderProduct convertOrderProductEntityToDTO(OrderProductEntity orderProductEntity) {

        OrderProduct p = new OrderProduct();
        p.setCount(orderProductEntity.getCount());
        p.setProductId(orderProductEntity.getProduct().getProductId());

        return p;
    }
}
