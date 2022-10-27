package com.switchfully.eurder.api.controller;

import com.switchfully.eurder.api.dto.AddItemDTO;
import com.switchfully.eurder.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("items")
@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    private final Logger logger= LoggerFactory.getLogger(ItemController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewItem(@RequestHeader (required = false) String authorization, @RequestBody AddItemDTO newItem){
        logger.info("Adding new item");
        itemService.addNewItem(authorization, newItem);
    }

}
