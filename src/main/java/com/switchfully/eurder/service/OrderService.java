package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.repository.ItemGroupRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import com.switchfully.eurder.service.dto.order.PrintOrderDTO;
import com.switchfully.eurder.api.mapper.OrderMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.OrderRepository;
import com.switchfully.eurder.service.validation.OrderValidationService;
import org.springframework.stereotype.Service;

import javax.persistence.PostPersist;
import javax.transaction.Transactional;

import java.time.LocalDate;

import static com.switchfully.eurder.domain.user.role.Feature.ORDER_ITEMS;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerRepository customerRepository;

    private final ItemRepository itemRepository;

    private final ItemGroupRepository itemGroupRepository;
    private final SecurityService securityService;

    private final OrderValidationService orderValidationService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CustomerRepository customerRepository, ItemRepository itemRepository, ItemGroupRepository itemGroupRepository, SecurityService securityService, OrderValidationService orderValidationService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.itemGroupRepository = itemGroupRepository;
        this.securityService = securityService;
        this.orderValidationService = orderValidationService;
    }

    public PrintOrderDTO addNewOrder(String authorization, AddOrderDTO newOrder) {
        securityService.validateAuthorization(authorization, ORDER_ITEMS);
        orderValidationService.validateNoEmptyFields(newOrder);

        Order orderToSave = orderMapper.mapToOrder(newOrder, calculateFinalPrice(newOrder));

        orderRepository.save(orderToSave);

        saveItemGroups(newOrder, orderToSave);

        return getPrintOrderDTO(newOrder);
    }

    public void saveItemGroups(AddOrderDTO newOrder, Order orderToSave) {
        newOrder.getItemGroupDTOList().forEach(addItemGroupDTO -> itemGroupRepository.save(orderMapper.mapToItemGroup(addItemGroupDTO, calculateShippingDate(addItemGroupDTO.getItemId(), addItemGroupDTO.getAmount()), orderToSave)));
    }

    public LocalDate calculateShippingDate(long itemId, int amount) {
        if (isInStock(itemId, amount)) return LocalDate.now().plusDays(1);
        return LocalDate.now().plusDays(7);
    }

    private boolean isInStock(long itemId, int amount) {
        return itemRepository.findById(itemId).orElseThrow().getAmount() >= amount;
    }

    private PrintOrderDTO getPrintOrderDTO(AddOrderDTO newOrder) {
        return new PrintOrderDTO().setCustomerId(newOrder.getCustomerId()).setFinalPrice(("Final price: " + calculateFinalPrice(newOrder)));
    }

    public double calculateFinalPrice(AddOrderDTO newOrder) {
        return newOrder.getItemGroupDTOList().stream().mapToDouble(itemGroupDTO -> itemGroupDTO.getAmount() * itemRepository.getItemPrice(itemGroupDTO.getItemId())).sum();
    }
}
