package net.bot.crypto.application.crypto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.application.common.service.SchedulingService;
import net.bot.crypto.application.domain.dto.response.MarketPrice;
import net.bot.crypto.application.domain.dto.response.TickerMessage;
import net.bot.crypto.application.slack.enums.CommandType;
import net.bot.crypto.application.slack.event.SlackNotificationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static net.bot.crypto.application.crypto.service.CryptoWebSocketHandler.TICKER_KEY;
import static net.bot.crypto.application.slack.constant.SlackContstant.ARGUMENTS_SEPARATOR;
import static net.bot.crypto.application.slack.enums.CommandType.ALARM;
import static net.bot.crypto.application.slack.enums.CommandType.INFO;
import static net.bot.crypto.application.slack.template.ResponseTemplate.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoScheduledService {

    private final RedisService redisService;
    private final SchedulingService schedulingService;
    private final UpbitFeignClient upbitClient;
    private final ApplicationEventPublisher eventPublisher;

    public void startCurrenyInfoTask() {
        schedulingService.startScheduledTask(this::fetchCurrencyInfo, Duration.ofMinutes(1));
        publishNotificationEvent(INFO, MESSAGE_WHEN_INFO_START);
    }

    public void startCurrencyAlarmTask() {
        schedulingService.startScheduledTask(this::fetchTradePrice, Duration.ofSeconds(5));
        publishNotificationEvent(ALARM, MESSAGE_WHEN_ALARM_START);
    }

    public void stopScheduledTask() {
        schedulingService.stopScheduledTask();
    }

    // ========== PRIVATE METHODS ========== //

    private void fetchCurrencyInfo() {
        String infoData = redisService.getData(CommandType.INFO.getPrefix(), String.class);
        List<MarketPrice> data = upbitClient.getCandlesMinutes(1, parseArgumentFromData(infoData), 1);
        publishNotificationEvent(INFO, generateCurrencyInfoResponse(data));
    }

    /**
     * 거래 가격을 조회하고, 목표가에 도달했는지 확인한다.
     */
    private void fetchTradePrice() {
        TickerMessage tickerMessage = redisService.getData(TICKER_KEY, TickerMessage.class);
        String alarmData = redisService.getData(CommandType.ALARM.getPrefix(), String.class);
        checkIfReachedTargetPrice(tickerMessage.tradePrice(), new BigDecimal(parseArgumentFromData(alarmData)));
    }

    private void checkIfReachedTargetPrice(BigDecimal tradePrice, BigDecimal targetPrice) {
        boolean isReachedTargetPrice = tradePrice.compareTo(targetPrice) >= 0;
        if (isReachedTargetPrice) {
            publishNotificationEvent(ALARM, generateAlarmResponse(targetPrice, tradePrice));
        }
    }

    private String parseArgumentFromData(String data) {
        return data.split(ARGUMENTS_SEPARATOR)[1];
    }

    protected void publishNotificationEvent(CommandType type, String message) {
        eventPublisher.publishEvent(new SlackNotificationEvent(this, type, message));
    }
}

