package net.bot.crypto.application.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record TickerMessage(
        @JsonProperty("ty") String type,
        @JsonProperty("cd") String code,
        @JsonProperty("hp") BigDecimal highPrice,
        @JsonProperty("lp") BigDecimal lowPrice,
        @JsonProperty("tp") BigDecimal tradePrice,
        @JsonProperty("tms") String timestamp,
        @JsonProperty("st") String status
) {
}
