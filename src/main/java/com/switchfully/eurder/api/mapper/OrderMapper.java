package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import com.switchfully.eurder.service.dto.order.PrintItemGroupDTO;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
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

    public Order mapToOrder(AddOrderDTO newOrder, double finalPrice) {
        return new Order(customerRepository.findById(newOrder.getCustomerId()).orElseThrow(), finalPrice);
    }

    /* public ItemGroup mapToItemGroup(AddItemGroupDTO itemGroupDTO, LocalDate shippingDate){
        return new ItemGroup(itemRepository.findById(itemGroupDTO.getItemId()).orElseThrow(), itemGroupDTO.getAmount(), shippingDate, itemGroupDTO.getOrder());
    }*/

    public List<PrintItemGroupDTO> mapAddToPrintItemGroupDTO(List<AddItemGroupDTO> itemGroupDTOList) {
        return itemGroupDTOList.stream().map(itemGroupDTO -> new PrintItemGroupDTO()
                        .setItemId(itemGroupDTO.getItemId())
                        .setAmount(itemGroupDTO.getAmount()).setShippingDate(calculateShippingDate(itemGroupDTO.getItemId(), itemGroupDTO.getAmount())))
                        .collect(Collectors.toList());
    }

    //todo find another spot for these methods. Putting it in OrderService causes circular dependency
    public LocalDate calculateShippingDate(long itemId, int amount) {
        if (isInStock(itemId, amount)) return LocalDate.now().plusDays(1);
        return LocalDate.now().plusDays(7);
    }

    private boolean isInStock(long itemId, int amount) {
        return itemRepository.findById(itemId).orElseThrow().getAmount() >= amount;
    }
}
