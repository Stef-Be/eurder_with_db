package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.item.AddItemDTO;
import com.switchfully.eurder.api.dto.item.PrintItemDTO;
import com.switchfully.eurder.api.dto.item.UpdatedItemDTO;
import com.switchfully.eurder.api.mapper.ItemMapper;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.eurder.domain.user.Feature.*;

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
        securityService.validateAuthorization(authorization, CRUD_ITEMS);
        validationService.validateNoEmptyFields(newItem);
        validationService.checkIfItemIsAlreadyInRepo(newItem);
        itemRepository.addNewItem(itemMapper.mapToItem(newItem));
    }

    public List<PrintItemDTO> getAllItems(String authorization) {
        securityService.validateAuthorization(authorization, CRUD_ITEMS);
        List<Item> foundItems = itemRepository.getItems();

        return foundItems.stream().map(item -> itemMapper.mapToItemToPrint(item)).collect(Collectors.toList());
    }


    public void updateItem(UpdatedItemDTO updatedItem, String authorization, String id) {
        securityService.validateAuthorization(authorization,CRUD_ITEMS);
        validationService.validateNoEmptyFields(updatedItem);
        itemRepository.updateItem(id, itemMapper.mapToItem(updatedItem));
    }
}
