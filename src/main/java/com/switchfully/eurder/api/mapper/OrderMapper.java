package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.domain.repository.OrderRepository;
import com.switchfully.eurder.service.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class OrderMapper {

    private ItemRepository itemRepository;

    private CustomerRepository customerRepository;

    private OrderRepository orderRepository;

    public OrderMapper(ItemRepository itemRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public Order mapToOrder(AddOrderDTO newOrder, double finalPrice) {
        return new Order(customerRepository.findById(newOrder.getCustomerId()).orElseThrow(), finalPrice);
    }

    public ItemGroup mapToItemGroup(AddItemGroupDTO itemGroupDTO, LocalDate shippingDate, Order orderToConnectToItemGroup){
        return new ItemGroup(itemRepository.findById(itemGroupDTO.getItemId()).orElseThrow(), itemGroupDTO.getAmount(), shippingDate, orderToConnectToItemGroup);
    }
}
