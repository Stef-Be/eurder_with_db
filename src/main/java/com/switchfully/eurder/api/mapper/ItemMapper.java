package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.api.dto.item.AddItemDTO;
import com.switchfully.eurder.api.dto.item.PrintItemDTO;
import com.switchfully.eurder.domain.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item mapToItem(AddItemDTO newItem) {
        return new Item(newItem.getName(), newItem.getDescription(), newItem.getPrice(), newItem.getAmount());
    }


    public PrintItemDTO mapToItemToPrint(Item item) {
        return new PrintItemDTO().setID(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setAmount(item.getAmount())
                .setPrice(item.getPrice());
    }
}
