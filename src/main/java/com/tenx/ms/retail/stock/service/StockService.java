package com.tenx.ms.retail.stock.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRespository;
import com.tenx.ms.retail.stock.rest.dto.AddStock;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRespository stockRespository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    public Long addStock(AddStock stock) {

        Optional<StockEntity> existingStock = stockRespository.findOneByStoreStoreIdAndProductProductId(stock.getStoreId(), stock.getProductId());

        StockEntity newStock;
        if(existingStock.isPresent()) {
            newStock = existingStock.get();
            newStock.setCount(newStock.getCount() + stock.getCount());
        } else {
            newStock = convertToEntity(stock);
        }

        return stockRespository.save(newStock).getStockId();
    }

    private StockEntity convertToEntity(AddStock stock) {

        StoreEntity store = storeRepository.findOneByStoreId(stock.getStoreId()).get();
        ProductEntity product = productRepository.findByStoresStoreIdAndProductId(stock.getStoreId(), stock.getProductId()).get();

        StockEntity stockEntity = new StockEntity();
        stockEntity.setProduct(product);
        stockEntity.setStore(store);
        stockEntity.setCount(stock.getCount());

        return stockEntity;
    }

    /**
     * Retrieves all stocks for a store
     * @param storeId the store id
     * @param pageable the pageable options used for paginated results
     * @param baseLinkPath the base path to the resource used for pagination link generation
     * @return Paginated Iterable of stocks
     */
    public Paginated<Stock> getStoreStocks(Long storeId, Pageable pageable, String baseLinkPath) {
        Page<StockEntity> page = stockRespository.findByStoreStoreId(storeId, pageable);

        List<Stock> stocks = page.getContent().stream()
                .map(stock -> convertToDTO(stock))
                .collect(Collectors.toList());

        return Paginated.wrap(page, stocks, baseLinkPath);
    }

    public Optional<Stock> getStockByStoreIdAndProductId(Long storeId, Long productId) {
        return stockRespository.findOneByStoreStoreIdAndProductProductId(storeId, productId)
                .map(product->convertToDTO(product));
    }

    private Stock convertToDTO(StockEntity stock) {

        Stock s = new Stock();
        s.setCount(stock.getCount());
        s.setStoreId(stock.getStore().getStoreId());
        s.setProductId(stock.getProduct().getProductId());

        return s;
    }
}
