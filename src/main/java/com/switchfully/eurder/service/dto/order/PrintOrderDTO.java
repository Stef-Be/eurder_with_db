package com.switchfully.eurder.service.dto.order;

public class PrintOrderDTO {

    private long customerId;
    private String finalPrice;

    public PrintOrderDTO setCustomerId(long customerId){
        this.customerId = customerId;
        return this;
    }

    public PrintOrderDTO setFinalPrice(String finalPrice){
        this.finalPrice = finalPrice;
        return this;
    }
}
