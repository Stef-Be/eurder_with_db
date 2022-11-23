package com.switchfully.eurder.service.dto.order;

import java.util.List;

public class AddOrderDTO {

    private List<AddItemGroupDTO> itemGroupDTOList;

    public AddOrderDTO(List<AddItemGroupDTO> itemGroupDTOList) {
        this.itemGroupDTOList = itemGroupDTOList;
    }

    public AddOrderDTO(){} //doet niks maar is nodig om tests te doen slagen??

    public List<AddItemGroupDTO> getItemGroupDTOList() {
        return itemGroupDTOList;
    }
}
