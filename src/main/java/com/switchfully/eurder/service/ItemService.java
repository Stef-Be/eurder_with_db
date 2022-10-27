package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.AddItemDTO;
import com.switchfully.eurder.api.mapper.ItemMapper;
import com.switchfully.eurder.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public void addNewItem(AddItemDTO newItem) {
        itemRepository.addNewItem(itemMapper.mapToItem(newItem));
    }
}
