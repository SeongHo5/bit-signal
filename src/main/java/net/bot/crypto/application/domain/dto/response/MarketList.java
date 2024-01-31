package net.bot.crypto.application.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 거래 가능한 마켓 목록
 * @param market 시장 정보
 * @param koreanName 한글명
 * @param englishName 영문명
 * @param marketWarning 유의 종목 여부
 */
public record MarketList(
        @JsonProperty("market") String market,
        @JsonProperty("korean_name") String koreanName,
        @JsonProperty("english_name") String englishName,
        @JsonProperty("market_warning") String marketWarning
) {
}
