package net.bot.crypto.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MarketList(
        @JsonProperty("market") String market,
        @JsonProperty("korean_name") String koreanName,
        @JsonProperty("english_name") String englishName,
        @JsonProperty("market_warning") String marketWarning
) {
}
