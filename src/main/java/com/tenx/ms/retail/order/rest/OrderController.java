package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.order.rest.dto.CreateOrder;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderStatus;
import com.tenx.ms.retail.order.service.OrderService;
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

@Api(value = "orders", description = "Order API")
@RestController
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Creates a new Order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful creation of order"),
            @ApiResponse(code = 412, message = "Validation failure"),
            @ApiResponse(code = 500, message = "Error creating order")
    })
    @RequestMapping(method = RequestMethod.POST)
    public OrderStatus createOrder(@Validated @RequestBody CreateOrder order, HttpServletRequest request) {

        LOGGER.debug("Creating a new Order: {}", order);

        return orderService.createOrder(order);
    }

    @ApiOperation(value = "Get list of orders for store by store id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of orders"),
            @ApiResponse(code = 500, message = "Error retrieving list of orders")
    })
    @RequestMapping(value = {"/store/{storeId:\\d+}"}, method = RequestMethod.GET)
    public Paginated<Order> getStoreProducts(@ApiParam(name = "storeId", value = "The store id") @PathVariable long storeId, Pageable pageable) {

        LOGGER.debug("Fetching all orders for store id {} : {}", storeId, pageable);

        return orderService.getStoreOrders(storeId, pageable, RestConstants.VERSION_ONE + "/orders");
    }

}
