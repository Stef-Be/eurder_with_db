package com.switchfully.eurder.api.dto;

import java.util.List;
public class PrintOrderDTO {
    private List<PrintItemGroupDTO> printItemGroupDTO;
    private String finalPrice;

    public PrintOrderDTO(List<PrintItemGroupDTO> printItemGroupDTO, String finalPrice) {
        this.printItemGroupDTO = printItemGroupDTO;
        this.finalPrice = finalPrice;
    }

    public List<PrintItemGroupDTO> getPrintItemGroupDTOS() {
        return printItemGroupDTO;
    }

    public String getFinalPrice() {
        return finalPrice;
    }
}
