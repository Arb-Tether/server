package com.arbiter.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class KimpMessageDto {
    private Double price;
    private String ticker;
    private Double kimp;

    private Long timestamp;

    private String market;

    public KimpMessageDto of(PriceMessageDto priceMessageDto, Double kimp) {
        this.price = priceMessageDto.getPrice();
        this.ticker = priceMessageDto.getTicker();
        this.kimp = kimp;
        this.timestamp = priceMessageDto.getTimestamp();
        this.market = priceMessageDto.getMarket();
        return this;
    }
}


