package com.switchfully.eurder.api.dto;

import java.util.List;
public class PrintOrderDTO {
    private List<AddItemGroupDTO> itemGroupDTOList;
    private String finalPrice;

    public PrintOrderDTO(List<AddItemGroupDTO> itemGroupDTOList, String finalPrice) {
        this.itemGroupDTOList = itemGroupDTOList;
        this.finalPrice = finalPrice;
    }
}
