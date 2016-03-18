package com.tenx.ms.retail.product.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.CreateProduct;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "products", description = "Product API")
@RestController("productControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Creates a new Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful creation of product"),
            @ApiResponse(code = 400, message = "Product already exists"),
            @ApiResponse(code = 412, message = "Validation failure"),
            @ApiResponse(code = 500, message = "Error creating product")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> createProduct(@Validated @RequestBody CreateProduct product, HttpServletRequest request) {

        LOGGER.debug("Creating a new Product: {}", product.getName());

        return new ResourceCreated<>(productService.createProduct(product));
    }

    @ApiOperation(value = "Get list of products for store by store id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of products"),
            @ApiResponse(code = 500, message = "Error retrieving list of products")
    })
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public Paginated<Product> getStoreProducts(@ApiParam(name = "storeId", value = "The store id") @PathVariable long storeId, Pageable pageable) {

        LOGGER.debug("Fetching all products for store id {} : {}", storeId, pageable);

        return productService.getStoreProducts(storeId, pageable, RestConstants.VERSION_ONE + "/retail");
    }
}
