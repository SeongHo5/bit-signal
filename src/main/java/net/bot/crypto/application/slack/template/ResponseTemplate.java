package net.bot.crypto.application.slack.template;

import net.bot.crypto.application.domain.dto.response.MarketList;
import net.bot.crypto.application.domain.dto.response.MarketPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static net.bot.crypto.application.common.util.BigDecimalUtil.convertBigDecimalToWonFormat;

public final class ResponseTemplate {
    public static final String MESSAGE_WHEN_INFO_START = "1분마다 시세 정보를 알려드릴게요 :smile:";
    public static final String MESSAGE_WHEN_ALARM_START = "말씀하신 가격에 도달하면 알려드릴게요 :smile:";
    public static final String MESSAGE_WHEN_ALARM_STOP = ":bell: 실행 중인 알람을 모두 중지했어요.";

    private ResponseTemplate() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    public static String createMarketListResponse(List<MarketList> marketList) {
        List<MarketList> topTenList = marketList.stream()
                .limit(10)
                .toList();
        return """
                :moneybag: 거래 가능한 마켓 목록 :moneybag:
                %s
                (총 %s개의 마켓이 조회되었어요.)
                (최대 10개의 마켓만 보여드려요.)
                (모든 마켓을 보고 싶으시다면 홈페이지에 방문해주세요.:money_with_wings:)
                ========================
                """
                .formatted(
                        convertMarketTitleToKorean(topTenList),
                        marketList.size());
    }


    public static String createCurrencyInfoResponse(List<MarketPrice> marketPriceList) {
        MarketPrice marketPrice = marketPriceList.get(0);
        return """
                :money_mouth_face: %s 시세 정보 :money_mouth_face:
                현재가: %s원
                고가: %s원
                저가: %s원
                기준 시간: %s
                ========================
                """
                .formatted(
                        marketPrice.market(),
                        convertBigDecimalToWonFormat(marketPrice.tradePrice()),
                        convertBigDecimalToWonFormat(marketPrice.highPrice()),
                        convertBigDecimalToWonFormat(marketPrice.lowPrice()),
                        marketPrice.candleDateTimeKst());
    }

    public static String createAlarmResponse(BigDecimal targetPrice, BigDecimal tradePrice) {
        return """
                :bell: 알람이 울렸어요! :bell:
                현재가: %s원
                목표가: %s원
                ========================
                """
                .formatted(
                        convertBigDecimalToWonFormat(tradePrice),
                        convertBigDecimalToWonFormat(targetPrice));
    }

    private static String convertMarketTitleToKorean(List<MarketList> marketLists) {
        return marketLists.stream()
                .map(market -> String.format("시장 정보 : %s, 한글명 : %s, 영문명 : %s, 유의 종목 여부 : %s",
                        market.market(), market.koreanName(), market.englishName(), market.marketWarning()))
                .collect(Collectors.joining("\n"));
    }
}
