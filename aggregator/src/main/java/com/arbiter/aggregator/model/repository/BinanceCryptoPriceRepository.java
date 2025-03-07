package com.arbiter.aggregator.model.repository;

import com.arbiter.aggregator.model.BinanceCryptoPrice;
import org.springframework.data.repository.CrudRepository;

public interface BinanceCryptoPriceRepository extends CrudRepository<BinanceCryptoPrice, String> {
}
