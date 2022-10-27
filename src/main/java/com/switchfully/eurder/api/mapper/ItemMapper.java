package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.api.dto.AddItemDTO;
import com.switchfully.eurder.domain.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item mapToItem(AddItemDTO newItem) {
        return new Item(newItem.getName(), newItem.getDescription(), newItem.getPrice(), newItem.getAmount());
    }
}
