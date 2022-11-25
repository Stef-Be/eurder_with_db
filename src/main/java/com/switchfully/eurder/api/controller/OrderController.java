package com.switchfully.eurder.api.controller;

import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import com.switchfully.eurder.service.dto.order.OrderReportDTO;
import com.switchfully.eurder.service.dto.order.PrintOrderDTO;
import com.switchfully.eurder.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("orders")
@RestController
public class OrderController {

    private OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PrintOrderDTO addNewOrder(@RequestHeader(required = false) String authorization, @RequestBody AddOrderDTO newOrder) {
        logger.info("Adding new order");
        return orderService.addNewOrder(authorization, newOrder);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public OrderReportDTO getAllOrders(@RequestHeader(required = false) String authorization){
        logger.info("Getting all your orders");
        return orderService.getAllOrders(authorization);
    }
}
