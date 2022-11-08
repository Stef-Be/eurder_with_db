package com.switchfully.eurder.service.dto.order;

import java.util.List;

public class AddOrderDTO {
    private List<AddItemGroupDTO> itemGroupDTOList;

    public AddOrderDTO setItemGroupDTOList(List<AddItemGroupDTO> itemGroupDTOList){
        this.itemGroupDTOList = itemGroupDTOList;
        return this;
    }

    public List<AddItemGroupDTO> getItemGroupDTOList() {
        return itemGroupDTOList;
    }
}
