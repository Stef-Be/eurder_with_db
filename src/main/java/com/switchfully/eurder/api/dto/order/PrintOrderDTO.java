package com.switchfully.eurder.api.dto.order;

import java.util.List;
public class PrintOrderDTO {
    private List<PrintItemGroupDTO> printItemGroupDTO;
    private String finalPrice;

    public PrintOrderDTO setPrintItemGroupDTO(List<PrintItemGroupDTO> printItemGroupDTO){
        this.printItemGroupDTO = printItemGroupDTO;
        return this;
    }

    public PrintOrderDTO setFinalPrice(String finalPrice){
        this.finalPrice = finalPrice;
        return this;
    }

    public List<PrintItemGroupDTO> getPrintItemGroupDTO() {
        return printItemGroupDTO;
    }

    public String getFinalPrice() {
        return finalPrice;
    }
}
