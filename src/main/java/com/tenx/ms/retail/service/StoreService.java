package com.tenx.ms.retail.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.repository.StoreRepository;
import com.tenx.ms.retail.rest.dto.CreateStore;
import com.tenx.ms.retail.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Long createStore(CreateStore store) {
        return storeRepository.save(convertToEntity(store)).getStoreId();
    }

    /**
     * Retrieves all stores
     * @param pageable the pageable options used for paginated results
     * @param baseLinkPath the base path to the resource used for pagination link generation
     * @return Paginated Iterable of stores
     */
    public Paginated<Store> getAllStores(Pageable pageable, String baseLinkPath) {
        Page<StoreEntity> page = storeRepository.findAll(pageable);

        List<Store> stores = page.getContent().stream()
                .map(store -> convertToDTO(store))
                .collect(Collectors.toList());

        return Paginated.wrap(page, stores, baseLinkPath);
    }

    public Optional<Store> getStoreById(Long storeId) {
        return storeRepository.findOneByStoreId(storeId)
                .map(store->convertToDTO(store));
    }

    private StoreEntity convertToEntity(CreateStore store) {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName(store.getName());

        return storeEntity;
    }

    private Store convertToDTO(StoreEntity store) {
        Store s = new Store();
        s.setStoreId(store.getStoreId());
        s.setName(store.getName());

        return s;
    }
}
