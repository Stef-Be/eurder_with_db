package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import com.switchfully.eurder.service.dto.order.PrintOrderDTO;
import com.switchfully.eurder.api.mapper.OrderMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.OrderRepository;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.service.validation.OrderValidationService;
import org.springframework.stereotype.Service;

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
    private final SecurityService securityService;

    private final OrderValidationService orderValidationService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CustomerRepository customerRepository, ItemRepository itemRepository, SecurityService securityService, OrderValidationService orderValidationService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.securityService = securityService;
        this.orderValidationService = orderValidationService;
    }

    public PrintOrderDTO addNewOrder(String authorization, AddOrderDTO newOrder) {
        securityService.validateAuthorization(authorization, ORDER_ITEMS);
        orderValidationService.validateNoEmptyFields(newOrder);
        Customer orderingCustomer = customerRepository.getCustomerByEmail(securityService.getEmail(authorization));

        orderRepository.addNewOrder(orderingCustomer, orderMapper.mapToOrder(newOrder));

        return getPrintOrderDTO(newOrder);
    }

    private PrintOrderDTO getPrintOrderDTO(AddOrderDTO newOrder) {
        return new PrintOrderDTO().setPrintItemGroupDTO(orderMapper.mapAddToPrintItemGroupDTO(newOrder.getItemGroupDTOList())).setFinalPrice(("Final price: " + orderRepository.calculateFinalPrice(newOrder)));
    }
}
