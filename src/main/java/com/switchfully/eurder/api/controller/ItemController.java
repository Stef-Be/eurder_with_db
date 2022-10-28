package com.switchfully.eurder.api.controller;

import com.switchfully.eurder.api.dto.AddItemDTO;
import com.switchfully.eurder.api.dto.AddOrderDTO;
import com.switchfully.eurder.api.dto.PrintOrderDTO;
import com.switchfully.eurder.service.ItemService;
import com.switchfully.eurder.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RequestMapping("items")
@RestController
public class ItemController {
    private final ItemService itemService;

    private final OrderService orderService;

    public ItemController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    private final Logger logger= LoggerFactory.getLogger(ItemController.class);

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewItem(@RequestHeader (required = false) String authorization, @RequestBody AddItemDTO newItem){
        logger.info("Adding new item");
        itemService.addNewItem(authorization, newItem);
    }

    @PostMapping(path = "order", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PrintOrderDTO addNewOrder(@RequestHeader (required = false) String authorization, @RequestBody AddOrderDTO newOrder){
        logger.info("Adding new order");
        return orderService.addNewOrder(authorization, newOrder);
    }


}
