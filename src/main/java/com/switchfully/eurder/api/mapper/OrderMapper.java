package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.dto.order.OneOrderForReportDTO;
import com.switchfully.eurder.service.dto.order.OrderReportDTO;
import com.switchfully.eurder.service.dto.order.PrintAllItemGroupsDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


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

    public OneOrderForReportDTO mapToOrderForReport(Order order, List<ItemGroup> itemGroups) {
        return new OneOrderForReportDTO(order.getId(), mapToReportItemGroups(itemGroups), order.getFinalPrice());
    }

    private List<PrintAllItemGroupsDTO> mapToReportItemGroups(List<ItemGroup> itemGroups) {
        return itemGroups.stream().map(itemGroup -> mapToReportSingleItemGroup(itemGroup)).collect(Collectors.toList());
    }

    private PrintAllItemGroupsDTO mapToReportSingleItemGroup(ItemGroup itemGroup) {
        return new PrintAllItemGroupsDTO(itemGroup.getItem().getName(), itemGroup.getAmount(), itemGroup.calculateFinalPrice());
    }
}
