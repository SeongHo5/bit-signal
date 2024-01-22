package net.bot.crypto.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @param market               마켓명
 * @param candleDateTimeUtc    캔들 기준 시각(UTC 기준)
 * @param candleDateTimeKst    캔들 기준 시각(KST 기준)
 * @param openingPrice         시가
 * @param highPrice            고가
 * @param lowPrice             저가
 * @param tradePrice           종가
 * @param timestamp            해당 캔들에서 마지막 틱이 저장된 시각
 * @param candleAccTradePrice  누적 거래 금액
 * @param candleAccTradeVolume 누적 거래량
 * @param unit                 분 단위(유닛)
 */
public record MarketPrice(@JsonProperty("market") String market,
                          @JsonProperty("candle_date_time_utc") String candleDateTimeUtc,
                          @JsonProperty("candle_date_time_kst") String candleDateTimeKst,
                          @JsonProperty("opening_price") BigDecimal openingPrice,
                          @JsonProperty("high_price") BigDecimal highPrice,
                          @JsonProperty("low_price") BigDecimal lowPrice,
                          @JsonProperty("trade_price") BigDecimal tradePrice,
                          @JsonProperty("timestamp") long timestamp,
                          @JsonProperty("candle_acc_trade_price") BigDecimal candleAccTradePrice,
                          @JsonProperty("candle_acc_trade_volume") BigDecimal candleAccTradeVolume,
                          @JsonProperty("unit") int unit) {

}
