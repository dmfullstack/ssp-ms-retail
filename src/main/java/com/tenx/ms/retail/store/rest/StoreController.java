package com.tenx.ms.retail.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.CreateStore;
import com.tenx.ms.retail.store.rest.dto.Store;
import com.tenx.ms.retail.store.service.StoreService;
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

@Api(value = "retail", description = "Store API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Creates a new Store")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful creation of store"),
        @ApiResponse(code = 400, message = "Store already exists"),
        @ApiResponse(code = 412, message = "Validation failure"),
        @ApiResponse(code = 500, message = "Error creating store")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> createStore(@Validated @RequestBody CreateStore store, HttpServletRequest request) {

        LOGGER.debug("Creating a new Store: {}", store.getName());

        return new ResourceCreated<>(storeService.createStore(store));
    }

    @ApiOperation(value = "Get list of stores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of stores"),
            @ApiResponse(code = 500, message = "Error retrieving list of stores")
    })
    @RequestMapping(method = RequestMethod.GET)
    public Paginated<Store> getAllStores(Pageable pageable) {

        LOGGER.debug("Fetching all stores: {}", pageable);

        return storeService.getAllStores(pageable, RestConstants.VERSION_ONE + "/retail");
    }

    @ApiOperation(value = "Get store details by store id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of store detail"),
            @ApiResponse(code = 404, message = "Store with that given id does not exist"),
            @ApiResponse(code = 500, message = "Error retrieving store details")
    })
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public Store getStoreById(@ApiParam(name = "storeId", value = "The store id") @PathVariable long storeId) {

        LOGGER.debug("Fetching store by id {}", storeId);

        return storeService.getStoreById(storeId).get();
    }

    @ApiOperation(value = "Get store details by store name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of store detail"),
            @ApiResponse(code = 404, message = "Store with that given name does not exist"),
            @ApiResponse(code = 500, message = "Error retrieving store details")
    })
    @RequestMapping(value = {"/name/{storeName}"}, method = RequestMethod.GET)
    public Store getStoreByName(@ApiParam(name = "storeName", value = "The store name") @PathVariable String storeName) {

        LOGGER.debug("Fetching store by name {}", storeName);

        return storeService.getStoreByName(storeName).get();
    }
}
