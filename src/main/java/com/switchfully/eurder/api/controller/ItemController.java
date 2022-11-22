package com.switchfully.eurder.api.controller;

import com.switchfully.eurder.service.dto.item.AddItemDTO;
import com.switchfully.eurder.service.dto.item.PrintItemDTO;
import com.switchfully.eurder.service.dto.item.UpdatedItemDTO;
import com.switchfully.eurder.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RequestMapping("items")
@RestController
public class ItemController {
    private final ItemService itemService;
    private final Logger logger= LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewItem(@RequestHeader (required = false) String authorization, @RequestBody AddItemDTO newItem){
        logger.info("Adding new item");
        itemService.addNewItem(authorization, newItem);
    }

    @PutMapping(path="{id}",consumes =  APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateItem(@RequestHeader(required = false) String authorization, @RequestBody UpdatedItemDTO updatedItem, @PathVariable long id){
        logger.info("Updating item");
        itemService.updateItem(updatedItem, authorization, id);
    }

    @GetMapping(produces =  APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PrintItemDTO> getItems(@RequestHeader (required = false) String authorization){
        logger.info("Showing all items");
        return itemService.getAllItems(authorization);
    }


}
