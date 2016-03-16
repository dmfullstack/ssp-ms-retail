package com.tenx.ms.retail.service;

import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.repository.StoreRepository;
import com.tenx.ms.retail.rest.dto.CreateStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Long createStore(CreateStore store) {
        return storeRepository.save(convertToEntity(store)).getStoreId();
    }

    private StoreEntity convertToEntity(CreateStore store) {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName(store.getName());

        return storeEntity;
    }
}
