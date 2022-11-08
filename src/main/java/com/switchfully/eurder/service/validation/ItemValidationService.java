package com.switchfully.eurder.service.validation;

import com.switchfully.eurder.service.dto.item.AddItemDTO;
import com.switchfully.eurder.service.dto.item.UpdatedItemDTO;
import com.switchfully.eurder.api.mapper.ItemMapper;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.item.Validatable;
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

    private void validateFieldsNotNull(Validatable validatable){
        validateFieldNotNull(validatable.getName(), "Name ");
        validateFieldNotNull(validatable.getDescription(), "Description");
        validateFieldNotZero(validatable.getPrice());
        validateFieldNotZero(validatable.getAmount());
    }

    public void checkIfItemIsAlreadyInRepo(AddItemDTO newItem) {
        if(itemRepository.getItems().contains(itemMapper.mapToItem(newItem))){
            throw new IllegalArgumentException("This item is already in the repository");
        }
    }
}
