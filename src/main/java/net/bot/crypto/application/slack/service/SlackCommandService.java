package net.bot.crypto.application.slack.service;


import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.application.crypto.service.CryptoScheduledService;
import net.bot.crypto.application.crypto.service.CryptoService;
import net.bot.crypto.domain.dto.request.RequestSlashCommand;
import net.bot.crypto.domain.dto.response.MarketList;
import net.bot.crypto.domain.entity.SlackCommandHistory;
import net.bot.crypto.application.slack.enums.CommandType;
import net.bot.crypto.infrastructure.repository.SlackCommandHistoryRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.bot.crypto.application.slack.constant.SlackContstant.ARGUMENTS_SEPARATOR;
import static net.bot.crypto.application.slack.template.ResponseTemplate.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SlackCommandService {

    private final SlackCommandHistoryRepository historyRepository;
    private final CryptoScheduledService cryptoScheduledService;
    private final CryptoService cryptoService;
    private final RedisService redisService;

    @Async
    public void saveCommandHistory(RequestSlashCommand request) {
        SlackCommandHistory history = SlackCommandHistory.of(request);
        historyRepository.save(history);
    }

    protected String callMarketListService() {
        List<MarketList> marketList = cryptoService.getMarketAll();
        return generateMarketListResponse(marketList);
    }

    protected String callInfoService(String channelId, String marketName) {
        storeInfoTaskInCache(channelId, marketName);
        cryptoScheduledService.startCurrenyInfoTask();
        return MESSAGE_WHEN_INFO_START;
    }

    protected String callAlarmService(String channelId, int targetPrice) {
        storeAlarmTaskInCache(channelId, targetPrice);
        cryptoScheduledService.startCurrencyAlarmTask();
        return MESSAGE_WHEN_ALARM_START;
    }

    protected String stopScheduledTask() {
        cryptoScheduledService.stopScheduledTask();
        return MESSAGE_WHEN_ALARM_STOP;
    }

    private void storeInfoTaskInCache(String channelId, String marketName) {
        String value = channelId + ARGUMENTS_SEPARATOR + marketName;
        redisService.setData(CommandType.INFO.getPrefix(), value);
    }

    private void storeAlarmTaskInCache(String channelId, int targetPrice) {
        String value = channelId + ARGUMENTS_SEPARATOR + targetPrice;
        redisService.setData(CommandType.ALARM.getPrefix(), value);
    }

}
