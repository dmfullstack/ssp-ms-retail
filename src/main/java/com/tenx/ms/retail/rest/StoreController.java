package com.tenx.ms.retail.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.rest.dto.CreateStore;
import com.tenx.ms.retail.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "retail", description = "Store API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/store")
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
        LOGGER.info("Creating a new Store: {}", store.getName());

        return new ResourceCreated<>(storeService.createStore(store));
    }
}
