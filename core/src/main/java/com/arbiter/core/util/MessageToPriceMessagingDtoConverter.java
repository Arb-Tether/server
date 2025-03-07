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
            priceMessageDto.setTicker(priceMessageDto.getTicker().replaceAll(".*-", ""));
            return priceMessageDto;
        } catch (JsonProcessingException e) {
            throw new InvalidMessageTypeException(ErrorCode.INVALID_MESSAGE_TYPE);
        }
    }
}
