package com.switchfully.eurder.service.dto.order;

import java.util.List;

public class OneOrderForReportDTO {
    private long id;
    private List<PrintAllItemGroupsDTO> printAllItemGroupsDTOList;
    private double totalPriceOfOneOrder;

    public OneOrderForReportDTO(long id, List<PrintAllItemGroupsDTO> printAllItemGroupsDTOList, double totalPriceOfOneOrder) {
        this.id = id;
        this.printAllItemGroupsDTOList = printAllItemGroupsDTOList;
        this.totalPriceOfOneOrder = totalPriceOfOneOrder;
    }

    public long getId() {
        return id;
    }

    public List<PrintAllItemGroupsDTO> getPrintAllItemGroupsDTOList() {
        return printAllItemGroupsDTOList;
    }

    public double getTotalPriceOfOneOrder() {
        return totalPriceOfOneOrder;
    }
}
