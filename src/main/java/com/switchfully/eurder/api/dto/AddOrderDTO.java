package com.switchfully.eurder.api.dto;

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
