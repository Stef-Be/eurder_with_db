package com.switchfully.eurder.service.validation;

import com.switchfully.eurder.service.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderValidationService extends ValidationService {
    public void validateNoEmptyFields(AddOrderDTO newOrder) {
        validateFieldsNotNull(newOrder);
    }

    private void validateFieldsNotNull(AddItemGroupDTO newItemOrder){
        validateFieldNotNull(newItemOrder.getItemId(), "Item id ");
        validateFieldNotZero(newItemOrder.getAmount());
    }

    private void validateFieldsNotNull(AddOrderDTO newOrder) {
        newOrder.getItemGroupDTOList().stream()
                .forEach(itemGroupDTO -> validateFieldsNotNull(itemGroupDTO));
    }
}
