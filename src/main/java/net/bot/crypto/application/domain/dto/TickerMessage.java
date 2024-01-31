package net.bot.crypto.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TickerMessage(
        @JsonProperty("ty") String type,
        @JsonProperty("cd") String code,
        @JsonProperty("hp") String highPrice,
        @JsonProperty("lp") String lowPrice,
        @JsonProperty("tp") String tradePrice,
        @JsonProperty("tms") String timestamp,
        @JsonProperty("st") String status
) {
}
