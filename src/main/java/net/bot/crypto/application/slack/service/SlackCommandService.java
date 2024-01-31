package net.bot.crypto.application.slack.service;


import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.application.crypto.service.CryptoScheduledService;
import net.bot.crypto.application.crypto.service.CryptoService;
import net.bot.crypto.application.domain.dto.response.MarketList;
import net.bot.crypto.application.slack.enums.CommandType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.bot.crypto.application.slack.constant.SlackContstant.ARGUMENTS_SEPARATOR;
import static net.bot.crypto.application.slack.template.ResponseTemplate.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SlackCommandService {

    private final CryptoScheduledService cryptoScheduledService;
    private final CryptoService cryptoService;
    private final RedisService redisService;

    protected String callMarketListService() {
        List<MarketList> marketList = cryptoService.getMarketAll();
        return createMarketListResponse(marketList);
    }

    protected String callInfoService(String channelId, String marketName) {
        cacheInfoTask(channelId, marketName);
        cryptoScheduledService.startCurrenyInfoTask();
        return MESSAGE_WHEN_INFO_START;
    }

    protected String callAlarmService(String channelId, int targetPrice) {
        cacheAlarmTask(channelId, targetPrice);
        cryptoScheduledService.startCurrencyAlarmTask();
        return MESSAGE_WHEN_ALARM_START;
    }

    protected String stopScheduledTask() {
        cryptoScheduledService.stopScheduledTask();
        return MESSAGE_WHEN_ALARM_STOP;
    }

    private void cacheInfoTask(String channelId, String marketName) {
        String value = channelId + ARGUMENTS_SEPARATOR + marketName;
        redisService.setData(CommandType.INFO.getPrefix(), value);
    }

    private void cacheAlarmTask(String channelId, int targetPrice) {
        String value = channelId + ARGUMENTS_SEPARATOR + targetPrice;
        redisService.setData(CommandType.ALARM.getPrefix(), value);
    }


}
