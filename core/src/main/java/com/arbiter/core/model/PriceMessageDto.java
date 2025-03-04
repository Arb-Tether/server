package com.arbiter.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceMessageDto {
    @JsonProperty("trade_price") // JSON의 "trade_price"를 price 필드에 매핑
    private Double price;

    @JsonProperty("code") // JSON의 "code"를 ticker 필드에 매핑
    private String ticker;

    @JsonProperty("trade_timestamp") // JSON의 "trade_timestamp"를 timestamp 필드에 매핑
    private Long timestamp;
}


