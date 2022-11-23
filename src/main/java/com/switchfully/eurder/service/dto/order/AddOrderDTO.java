package com.switchfully.eurder.service.dto.order;

import java.util.List;

public class AddOrderDTO {

    private long customerId;

    private List<AddItemGroupDTO> itemGroupDTOList;

    public AddOrderDTO(long customerId, List<AddItemGroupDTO> itemGroupDTOList) {
        this.customerId = customerId;
        this.itemGroupDTOList = itemGroupDTOList;
    }

    public List<AddItemGroupDTO> getItemGroupDTOList() {
        return itemGroupDTOList;
    }

    public long getCustomerId() {
        return customerId;
    }
}
