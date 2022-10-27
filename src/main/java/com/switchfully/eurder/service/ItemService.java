package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.AddItemDTO;
import com.switchfully.eurder.api.mapper.ItemMapper;
import com.switchfully.eurder.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;

import static com.switchfully.eurder.domain.user.Feature.ADD_ITEM;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private final ValidationService validationService;

    private final SecurityService securityService;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper, ValidationService validationService, SecurityService securityService) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.validationService = validationService;
        this.securityService = securityService;
    }

    public void addNewItem(String authorization, AddItemDTO newItem) {
        securityService.validateAuthorization(authorization, ADD_ITEM);
        validationService.validateNoEmptyFields(newItem);
        itemRepository.addNewItem(itemMapper.mapToItem(newItem));
    }
}
