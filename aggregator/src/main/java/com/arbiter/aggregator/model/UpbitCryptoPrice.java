package com.arbiter.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("upbit_price")
@AllArgsConstructor
public class UpbitCryptoPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String ticker;

    private Double price;

    private Long timestamp;
}
