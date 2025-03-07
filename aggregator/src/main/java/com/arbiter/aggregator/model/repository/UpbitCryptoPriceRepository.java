package com.arbiter.aggregator.model.repository;

import com.arbiter.aggregator.model.UpbitCryptoPrice;
import org.springframework.data.repository.CrudRepository;

public interface UpbitCryptoPriceRepository extends CrudRepository<UpbitCryptoPrice, String> {
}
