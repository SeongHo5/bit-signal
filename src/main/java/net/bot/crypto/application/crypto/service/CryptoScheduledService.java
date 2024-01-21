package net.bot.crypto.application.crypto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.application.common.service.SchedulingService;
import net.bot.crypto.application.slack.enums.CommandType;
import net.bot.crypto.application.slack.event.SlackNotificationEvent;
import net.bot.crypto.domain.dto.MarketPriceDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static net.bot.crypto.application.slack.constant.SlackContstant.*;
import static net.bot.crypto.application.slack.enums.CommandType.ALARM;
import static net.bot.crypto.application.slack.enums.CommandType.INFO;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoScheduledService {

    private final RedisService redisService;
    private final SchedulingService schedulingService;
    private final UpbitFeignClient upbitClient;
    private final ApplicationEventPublisher eventPublisher;
    public static final String MARKET_BTC = "KRW-BTC";

    public void startCurrenyInfoTask() {
        schedulingService.startScheduledTask(this::fetchCurrencyInfo, Duration.ofMinutes(1));
        publishNotificationEvent(INFO, MESSAGE_WHEN_INFO_START);
    }

    public void startCurrencyAlarmTask() {
        schedulingService.startScheduledTask(this::fetchTradePrice, Duration.ofMinutes(1));
        publishNotificationEvent(ALARM, MESSAGE_WHEN_ALARM_START);
    }

    public void stopScheduledTask() {
        schedulingService.stopScheduledTask();
        publishNotificationEvent(ALARM, MESSAGE_WHEN_ALARM_STOP);
    }

    // ========== PRIVATE METHODS ========== //

    /**
     * 거래 가격을 조회하고, 목표가에 도달했는지 확인한다.
     */
    private void fetchTradePrice() {
        List<MarketPriceDto> response = upbitClient.getCandlesMinutes(1, MARKET_BTC, 1);
        BigDecimal tradePrice = response.get(0).tradePrice();
        checkIfReachedTargetPrice(tradePrice);
    }

    private void fetchCurrencyInfo() {
        List<MarketPriceDto> response = upbitClient.getCandlesMinutes(1, MARKET_BTC, 1);
        BigDecimal tradePrice = response.get(0).tradePrice();
        String message = "현재가: " + tradePrice;
        publishNotificationEvent(INFO, message);
    }

    private void checkIfReachedTargetPrice(BigDecimal tradePrice) {
        String data = redisService.getData(CommandType.ALARM.getPrefix());
        String targetPrice = parseAlarmData(data);

        boolean isReached = tradePrice.compareTo(new BigDecimal(targetPrice)) >= 0;
        if (isReached) {
            String message = "목표가 도달! 현재가: " + tradePrice + ", 목표가: " + targetPrice;
            publishNotificationEvent(ALARM, message);
        }
    }

    private String parseAlarmData(String data) {
        String[] parsedData = data.split(ARGUMENTS_SEPARATOR);
        return parsedData[1];
    }

    private void publishNotificationEvent(CommandType type, String message) {
        eventPublisher.publishEvent(new SlackNotificationEvent(this, type, message));
    }
}

