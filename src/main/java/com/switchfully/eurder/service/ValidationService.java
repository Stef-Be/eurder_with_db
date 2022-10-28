package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.item.AddItemDTO;
import com.switchfully.eurder.api.dto.item.UpdatedItemDTO;
import com.switchfully.eurder.api.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.api.dto.order.AddOrderDTO;
import com.switchfully.eurder.api.dto.customer.CreateCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.api.mapper.ItemMapper;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;
@Service
public class ValidationService {

    private final CustomerMapper customerMapper;
    private final ItemMapper itemMapper;

    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    public ValidationService(CustomerMapper customerMapper, ItemMapper itemMapper, CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerMapper = customerMapper;
        this.itemMapper = itemMapper;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    public void validateNoEmptyFields(CreateCustomerDTO newCustomerDTO) {
        validateFieldsNotNull(newCustomerDTO);
    }

    public void validateNoEmptyFields(AddItemDTO newItem) {
        validateFieldsNotNull(newItem);
    }
    public void validateNoEmptyFields(AddOrderDTO newOrder) {
        validateFieldsNotNull(newOrder);
    }
    public void validateNoEmptyFields(UpdatedItemDTO updatedItem) {
        validateFieldsNotNull(updatedItem);
    }

    private void validateFieldsNotNull(CreateCustomerDTO newCustomerDTO) {
        validateFieldNotNull(newCustomerDTO.getFirstName(), "First name");
        validateFieldNotNull(newCustomerDTO.getLastName(), "Last name");
        validateFieldNotNull(newCustomerDTO.getEmail(), "Email");
        validateFieldNotNull(newCustomerDTO.getAddress(), "Address");
        validateFieldNotNull(newCustomerDTO.getPhoneNumber(), "Phone number");
    }

    private void validateFieldsNotNull(UpdatedItemDTO updatedItem){
        validateFieldNotNull(updatedItem.getName(), "Name ");
        validateFieldNotNull(updatedItem.getDescription(), "Description");
        validateFieldNotZero(updatedItem.getAmount());
        validateFieldNotZero(updatedItem.getPrice());
    }

    private void validateFieldsNotNull(AddItemGroupDTO newItemOrder){
        validateFieldNotNull(newItemOrder.getItemId(), "Item id ");
        validateFieldNotZero(newItemOrder.getAmount());
    }

    private void validateFieldsNotNull(AddItemDTO newItem) {
        validateFieldNotNull(newItem.getName(), "Name");
        validateFieldNotNull(newItem.getDescription(), "Description");
        validateFieldNotZero(newItem.getPrice());
        validateFieldNotZero(newItem.getAmount());
    }

    private void validateFieldNotZero(double price) {
        if(price <= 0) throw new IllegalArgumentException("Price must be more than 0!");
    }
    private void validateFieldNotZero(int amount) {
        if(amount <= 0) throw new IllegalArgumentException("Amount must be more than 0!");
    }

    private void validateFieldNotNull(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " can not be empty!");
    }

    public void checkIfUserIsAlreadyCustomer(CreateCustomerDTO newCustomerDTO) {
        Customer customerToAdd = customerMapper.mapToCreatedCustomer(newCustomerDTO);
        if (customerRepository.getAllCustomers().contains(customerToAdd)) {
            throw new IllegalArgumentException("You are already a customer!");
        }
    }

    public void checkIfItemIsAlreadyInRepo(AddItemDTO newItem) {
        if(itemRepository.getItems().contains(itemMapper.mapToItem(newItem))){
            throw new IllegalArgumentException("This item is already in the repository");
        }
    }
    private void validateFieldsNotNull(AddOrderDTO newOrder) {
        newOrder.getItemGroupDTOList().stream()
                .forEach(itemGroupDTO -> validateFieldsNotNull(itemGroupDTO));
    }

}
