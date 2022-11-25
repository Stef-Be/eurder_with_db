package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.repository.ItemGroupRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import com.switchfully.eurder.service.dto.order.OneOrderForReportDTO;
import com.switchfully.eurder.service.dto.order.OrderReportDTO;
import com.switchfully.eurder.service.dto.order.PrintOrderDTO;
import com.switchfully.eurder.api.mapper.OrderMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.OrderRepository;
import com.switchfully.eurder.service.validation.OrderValidationService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.eurder.domain.user.role.Feature.ORDER_ITEMS;
import static com.switchfully.eurder.domain.user.role.Feature.VIEW_ALL_ORDERS;

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

        Order orderToSave = orderMapper.mapToOrder(customerRepository.getCustomerByEmail(securityService.getEmail(authorization)).getId(), calculateFinalPriceOnReceipt(newOrder));

        orderRepository.save(orderToSave);

        saveItemGroups(newOrder, orderToSave);

        return getPrintOrderDTO(newOrder, authorization);
    }

    //todo: refactor this method
    public OrderReportDTO getAllOrders(String authorization) {
        securityService.validateAuthorization(authorization, VIEW_ALL_ORDERS);

        long customerIdToGetOrdersFrom = customerRepository.getCustomerByEmail(securityService.getEmail(authorization)).getId();

        List<Order> orderList = orderRepository.findAllByCustomerId(customerIdToGetOrdersFrom);
        List<List<ItemGroup>> itemGroupsList = orderList.stream().map(order -> itemGroupRepository.findAllByOrderId(order.getId())).toList();
        List<ItemGroup> itemGroupList = itemGroupsList.stream().flatMap(List::stream).toList();
        double finalFinalPrice = itemGroupList.stream().mapToDouble(ItemGroup::calculateFinalPrice).sum();

        List<OneOrderForReportDTO> orderDtoList = orderList.stream().map(order -> orderMapper.mapToOrderForReport(order, itemGroupRepository.findAllByOrderId(order.getId()))).toList();

        return new OrderReportDTO(orderDtoList, finalFinalPrice);
    }

    public void saveItemGroups(AddOrderDTO newOrder, Order orderToSave) {
        newOrder.getItemGroupDTOList()
                .forEach(addItemGroupDTO -> itemGroupRepository.save
                        (orderMapper.mapToItemGroup(addItemGroupDTO, calculateShippingDate(addItemGroupDTO.getItemId(), addItemGroupDTO.getAmount()), orderToSave)));
    }

    public LocalDate calculateShippingDate(long itemId, int amount) {
        if (isInStock(itemId, amount)) return LocalDate.now().plusDays(1);
        return LocalDate.now().plusDays(7);
    }

    private boolean isInStock(long itemId, int amount) {
        return itemRepository.findById(itemId).orElseThrow().getAmount() >= amount;
    }

    private PrintOrderDTO getPrintOrderDTO(AddOrderDTO newOrder, String authorization) {
        return new PrintOrderDTO()
                .setCustomerId(customerRepository.getCustomerByEmail(securityService.getEmail(authorization)).getId())
                .setFinalPrice(("Final price: " + calculateFinalPriceOnReceipt(newOrder)));
    }

    public double calculateFinalPriceOnReceipt(AddOrderDTO newOrder) {
        return newOrder.getItemGroupDTOList().stream().mapToDouble(itemGroupDTO -> itemGroupDTO.getAmount() * itemRepository.getItemPrice(itemGroupDTO.getItemId())).sum();
    }
}
