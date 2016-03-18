package com.tenx.ms.retail.product.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.CreateProduct;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Long createProduct(CreateProduct product) {

        return productRepository.save(convertToEntity(product)).getProductId();
    }

    /**
     * Retrieves all products for a store
     * @param storeId the store id
     * @param pageable the pageable options used for paginated results
     * @param baseLinkPath the base path to the resource used for pagination link generation
     * @return Paginated Iterable of products
     */
    public Paginated<Product> getStoreProducts(Long storeId, Pageable pageable, String baseLinkPath) {
        Page<ProductEntity> page = productRepository.findByStoreId(storeId, pageable);

        List<Product> products = page.getContent().stream()
                .map(product -> convertToDTO(product))
                .collect(Collectors.toList());

        return Paginated.wrap(page, products, baseLinkPath);
    }

    public Optional<Product> getStoreProductById(Long storeId, Long productId) {
        return productRepository.findOneByStoreIdAndProductId(storeId, productId)
                .map(product->convertToDTO(product));
    }

    public Optional<Product> getStoreProductByName(Long storeId, String name) {
        return productRepository.findOneByStoreIdAndName(storeId, name)
                .map(product->convertToDTO(product));
    }

    private ProductEntity convertToEntity(CreateProduct product) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setStoreId(product.getStoreId());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setSku(product.getSku());

        return productEntity;
    }

    private Product convertToDTO(ProductEntity product) {

        Product p = new Product();
        p.setDescription(product.getDescription());
        p.setSku(product.getSku());
        p.setPrice(product.getPrice());
        p.setName(product.getName());
        p.setStoreId(product.getStoreId());
        p.setProductId(product.getProductId());

        return p;
    }
}
