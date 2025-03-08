package com.arbiter.core.util;

import com.arbiter.core.constant.ErrorCode;
import com.arbiter.core.exception.InvalidMessageTypeException;
import com.arbiter.core.model.PriceMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageToPriceMessagingDtoConverter {
    public static PriceMessageDto convertToPriceMessage(String message, ObjectMapper mapper) {
        try {
            log.info("[MessageToPriceMessagingDtoConverter] Received {}", message);
            PriceMessageDto priceMessageDto = mapper.readValue(message, PriceMessageDto.class);

            // ✅ 티커 변환 로직 (업비트 & 바이낸스 지원)
            String originalTicker = priceMessageDto.getTicker().toUpperCase();

            // 1️⃣ 업비트 형식 (KRW-BTC, USDT-ETH) → BTC, ETH
            if (originalTicker.contains("-")) {
                originalTicker = originalTicker.replaceAll(".*-", "");
            }
            // 2️⃣ 바이낸스 형식 (BTCUSDT, ETHBTC) → USDT, BTC 같은 마켓 심볼 제거
            else {
                originalTicker = originalTicker.replaceAll("(USDT|BTC|ETH|BNB)$", "");
            }

            priceMessageDto.setTicker(originalTicker);
            return priceMessageDto;
        } catch (JsonProcessingException e) {
            throw new InvalidMessageTypeException(ErrorCode.INVALID_MESSAGE_TYPE);
        }
    }

}
