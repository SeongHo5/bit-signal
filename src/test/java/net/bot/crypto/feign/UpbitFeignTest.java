package net.bot.crypto.feign;

import net.bot.crypto.application.crypto.service.UpbitFeignClient;
import net.bot.crypto.application.domain.dto.response.MarketList;
import net.bot.crypto.application.domain.dto.response.MarketPrice;
import net.bot.crypto.config.OpenFeignTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@OpenFeignTest
class UpbitFeignTest {

    @Autowired
    private UpbitFeignClient upbitFeignClient;

    @Test
    @DisplayName("거래 가능 마켓 조회 테스트")
    void getMarketAllTest() {
        // Given & When
        List<MarketList> result = upbitFeignClient.getMarketAll(true);

        // Then
        assertThat(result).isNotNull();
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestStream() {
        // Given
        int unit = 1;
        String market = "KRW-BTC";
        String wrongMarket = "KRW-KRW";
        int count = 1;

        // When & Then
        return Stream.of(
                DynamicTest.dynamicTest("분봉 데이터 조회 성공", () -> {
                    List<MarketPrice> result = upbitFeignClient.getCandlesMinutes(unit, market, count);
                    assertThat(result).isNotNull();
                }),
                DynamicTest.dynamicTest("분봉 데이터 조회 실패 - 없는 마켓명", () -> assertThrows(Exception.class, () -> upbitFeignClient.getCandlesMinutes(unit, wrongMarket, count)))
        );
    }

    @Test
    @DisplayName("분봉 데이터 조회 테스트")
    void getCandlesMinutesTest() {
        // Given
        int unit = 1;
        String market = "KRW-BTC";
        int count = 1;

        // When
        List<MarketPrice> result = upbitFeignClient.getCandlesMinutes(unit, market, count);

        // Then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("현재가 조회 테스트")
    void getTickerTest() {
        // Given
        String markets = "KRW-BTC";

        // When
        String result = upbitFeignClient.getTicker(markets);

        // Then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("호가 정보 조회 테스트")
    void getOrderBookTest() {
        // Given
        String markets = "KRW-BTC";

        // When
        String result = upbitFeignClient.getOrderBook(markets);

        // Then
        assertThat(result).isNotNull();
    }
}
