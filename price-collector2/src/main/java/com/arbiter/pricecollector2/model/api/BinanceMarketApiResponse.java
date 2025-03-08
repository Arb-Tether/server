package com.arbiter.pricecollector2.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceMarketApiResponse {
    @JsonProperty("symbols")
    private BinanceMarketDto[] symbols;
}
