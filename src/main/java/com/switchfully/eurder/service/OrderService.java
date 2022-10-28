package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.AddOrderDTO;
import com.switchfully.eurder.api.dto.PrintOrderDTO;
import com.switchfully.eurder.api.mapper.OrderMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.domain.repository.OrderRepository;
import com.switchfully.eurder.domain.user.Customer;
import org.springframework.stereotype.Service;

import static com.switchfully.eurder.domain.user.Feature.ORDER_ITEMS;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemRepository itemRepository;

    private final CustomerRepository customerRepository;
    private final SecurityService securityService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ItemRepository itemRepository, CustomerRepository customerRepository, SecurityService securityService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.securityService = securityService;
    }

    public PrintOrderDTO addNewOrder(String authorization, AddOrderDTO newOrder) {
        securityService.validateAuthorization(authorization, ORDER_ITEMS);
        Customer orderingCustomer = customerRepository.getCustomerbyEmail(securityService.getEmail(authorization));
        orderRepository.addNewOrder(orderingCustomer, orderMapper.mapToOrder(newOrder));
        return new PrintOrderDTO().setPrintItemGroupDTO(orderMapper.mapAddToPrintItemGroupDTO(newOrder.getItemGroupDTOList())).setFinalPrice(("Final price: " + orderRepository.calculateFinalPrice(newOrder)));
    }
}
