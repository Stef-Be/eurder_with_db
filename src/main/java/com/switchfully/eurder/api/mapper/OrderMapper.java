package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class OrderMapper {

    private ItemRepository itemRepository;

    private CustomerRepository customerRepository;

    public OrderMapper(ItemRepository itemRepository, CustomerRepository customerRepository) {
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
    }

    public Order mapToOrder(long id, double finalPrice) {
        return new Order(customerRepository.findById(id).orElseThrow(), finalPrice);
    }

    public ItemGroup mapToItemGroup(AddItemGroupDTO itemGroupDTO, LocalDate shippingDate, Order orderToConnectToItemGroup){
        return new ItemGroup(itemRepository.findById(itemGroupDTO.getItemId()).orElseThrow(), itemGroupDTO.getAmount(), shippingDate, orderToConnectToItemGroup);
    }
}
