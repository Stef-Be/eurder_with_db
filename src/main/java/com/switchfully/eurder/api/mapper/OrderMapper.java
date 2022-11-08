package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.service.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.service.dto.order.AddOrderDTO;
import com.switchfully.eurder.service.dto.order.PrintItemGroupDTO;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.repository.ItemRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ItemRepository itemRepository;

    public OrderMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Order mapToOrder(AddOrderDTO newOrder) {
        List<ItemGroup> itemGroupList = newOrder
                .getItemGroupDTOList()
                .stream().map(itemGroupDTO->mapToItemGroup(itemGroupDTO, itemRepository.calculateShippingDate(itemGroupDTO.getItemId(), itemGroupDTO.getAmount())))
                .toList();
        return new Order(itemGroupList);
    }

    public ItemGroup mapToItemGroup(AddItemGroupDTO itemGroupDTO, LocalDate shippingDate){
        return new ItemGroup(itemGroupDTO.getItemId(), itemGroupDTO.getAmount(), shippingDate);
    }

    public List<PrintItemGroupDTO> mapAddToPrintItemGroupDTO(List<AddItemGroupDTO> itemGroupDTOList) {
        return itemGroupDTOList.stream().map(itemGroupDTO -> new PrintItemGroupDTO()
                        .setItemId(itemGroupDTO.getItemId())
                        .setAmount(itemGroupDTO.getAmount()).setShippingDate(itemRepository.calculateShippingDate(itemGroupDTO.getItemId(), itemGroupDTO.getAmount())))
                        .collect(Collectors.toList());
    }
}
