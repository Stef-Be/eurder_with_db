package com.switchfully.eurder.service.dto.order;

import java.util.List;

public class OrderReportDTO {
    private List<OneOrderForReportDTO> oneOrderForReportDTO;
    private double totalPriceOfAllOrders;

    public OrderReportDTO(List<OneOrderForReportDTO> oneOrderForReportDTO, double totalPriceOfAllOrders) {
        this.oneOrderForReportDTO = oneOrderForReportDTO;
        this.totalPriceOfAllOrders = totalPriceOfAllOrders;
    }

    public List<OneOrderForReportDTO> getOneOrderForReportDTO() {
        return oneOrderForReportDTO;
    }

    public double getTotalPriceOfAllOrders() {
        return totalPriceOfAllOrders;
    }
}
