package com.arbiter.pricecollector.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpbitMessageDto {
    @JsonProperty("trade_price")
    private Double price;

    @JsonProperty("code")
    private String ticker;

    @JsonProperty("trade_timestamp")
    private Long timestamp;

    private String market;
}
