package com.arbiter.pricecollector2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinanceMessageDto {
    @JsonProperty("c")
    private Double price;

    @JsonProperty("s")
    private String ticker;

    @JsonProperty("E")
    private Long timestamp;

    private String market;
}
