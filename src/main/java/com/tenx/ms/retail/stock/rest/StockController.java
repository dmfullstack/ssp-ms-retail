package com.tenx.ms.retail.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.stock.rest.dto.AddStock;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.service.StockService;
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

@Api(value="stocks", description = "Stock API")
@RestController("stockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stocks")
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Add Stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful addition of stock"),
            @ApiResponse(code = 412, message = "Validation failure"),
            @ApiResponse(code = 500, message = "Error adding stock")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> addStock(@Validated @RequestBody AddStock stock, HttpServletRequest request) {

        LOGGER.debug("Adding stock: {}", stock);

        return new ResourceCreated<>(stockService.addStock(stock));
    }

    @ApiOperation(value = "Get list of stocks for store by store id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of stocks"),
            @ApiResponse(code = 500, message = "Error retrieving list of stocks")
    })
    @RequestMapping(value = {"/store/{storeId:\\d+}"}, method = RequestMethod.GET)
    public Paginated<Stock> getStoreProducts(@ApiParam(name = "storeId", value = "The store id") @PathVariable long storeId, Pageable pageable) {

        LOGGER.debug("Fetching all products for store id {} : {}", storeId, pageable);

        return stockService.getStoreStocks(storeId, pageable, RestConstants.VERSION_ONE + "/stocks");
    }

    @ApiOperation(value = "Get stock details by product id for a store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of stock detail"),
            @ApiResponse(code = 404, message = "Stock with that given store id and product id does not exist"),
            @ApiResponse(code = 500, message = "Error retrieving stock details")
    })

    @RequestMapping(value = {"/store/{storeId:\\d+}/product/{productId:\\d+}"}, method = RequestMethod.GET)
    public Stock getStoreStockByProductId(@ApiParam(name = "storeId", value = "The store id") @PathVariable long storeId,
                                       @ApiParam(name = "productId", value = "The product id") @PathVariable long productId) {

        LOGGER.debug("Fetching stock by id {} for store {}", productId, storeId);

        return stockService.getStockByStoreIdAndProductId(storeId, productId).get();
    }
}
