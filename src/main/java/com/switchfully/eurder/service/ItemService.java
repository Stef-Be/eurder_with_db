package com.switchfully.eurder.service;

import com.switchfully.eurder.service.dto.item.AddItemDTO;
import com.switchfully.eurder.service.dto.item.PrintItemDTO;
import com.switchfully.eurder.service.dto.item.UpdatedItemDTO;
import com.switchfully.eurder.api.mapper.ItemMapper;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.validation.ItemValidationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.switchfully.eurder.domain.user.role.Feature.*;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private final ItemValidationService itemValidationService;

    private final SecurityService securityService;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper, ItemValidationService itemValidationService, SecurityService securityService) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.itemValidationService = itemValidationService;
        this.securityService = securityService;
    }

    public void addNewItem(String authorization, AddItemDTO newItem) {
        securityService.validateAuthorization(authorization, CRUD_ITEMS);
        itemValidationService.validateNoEmptyFields(newItem);
        itemValidationService.checkIfItemIsAlreadyInRepo(newItem);
        itemRepository.save(itemMapper.mapToItem(newItem));
    }

    public List<PrintItemDTO> getAllItems(String authorization) {
        securityService.validateAuthorization(authorization, CRUD_ITEMS);
        List<Item> foundItems = itemRepository.findAll();

        return foundItems.stream().map(itemMapper::mapToItemToPrint).toList();
    }

    public void updateItem(UpdatedItemDTO updatedItem, String authorization, long id) {
        securityService.validateAuthorization(authorization,CRUD_ITEMS);
        itemValidationService.validateNoEmptyFields(updatedItem);

        Item itemToUpdate = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such item exists!"));

        itemToUpdate.setName(updatedItem.getName());
        itemToUpdate.setDescription(updatedItem.getDescription());
        itemToUpdate.setAmount(updatedItem.getAmount());
        itemToUpdate.setPrice(updatedItem.getPrice());

        itemRepository.save(itemToUpdate);
    }
}
