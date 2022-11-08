package com.switchfully.eurder.service.validation;

import com.switchfully.eurder.api.dto.item.AddItemDTO;
import com.switchfully.eurder.api.dto.item.UpdatedItemDTO;
import com.switchfully.eurder.api.mapper.ItemMapper;
import com.switchfully.eurder.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemValidationService extends ValidationService {
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemValidationService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public void validateNoEmptyFields(AddItemDTO newItem) {
        validateFieldsNotNull(newItem);
    }

    public void validateNoEmptyFields(UpdatedItemDTO updatedItem) {
        validateFieldsNotNull(updatedItem);
    }

    private void validateFieldsNotNull(UpdatedItemDTO updatedItem){
        validateFieldNotNull(updatedItem.getName(), "Name ");
        validateFieldNotNull(updatedItem.getDescription(), "Description");
        validateFieldNotZero(updatedItem.getAmount());
        validateFieldNotZero(updatedItem.getPrice());
    }
    private void validateFieldsNotNull(AddItemDTO newItem) {
        validateFieldNotNull(newItem.getName(), "Name");
        validateFieldNotNull(newItem.getDescription(), "Description");
        validateFieldNotZero(newItem.getPrice());
        validateFieldNotZero(newItem.getAmount());
    }

    public void checkIfItemIsAlreadyInRepo(AddItemDTO newItem) {
        if(itemRepository.getItems().contains(itemMapper.mapToItem(newItem))){
            throw new IllegalArgumentException("This item is already in the repository");
        }
    }
}
