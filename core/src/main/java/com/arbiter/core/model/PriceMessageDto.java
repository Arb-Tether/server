package com.arbiter.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceMessageDto {
    private Double price;
    private String ticker;
    private Long timestamp;
    private String market;
}


