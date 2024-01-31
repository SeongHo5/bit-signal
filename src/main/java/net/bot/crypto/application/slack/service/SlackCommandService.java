package net.bot.crypto.application.slack.service;


import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.application.crypto.service.CryptoScheduledService;
import net.bot.crypto.application.crypto.service.CryptoService;
import net.bot.crypto.application.domain.dto.MarketList;
import net.bot.crypto.application.slack.enums.CommandType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static net.bot.crypto.application.slack.constant.SlackContstant.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SlackCommandService {

    private final CryptoScheduledService cryptoScheduledService;
    private final CryptoService cryptoService;
    private final RedisService redisService;

    protected String callMarketListService() {
        List<MarketList> marketList = cryptoService.getMarketAll();
        return convertTitleToKorean(marketList);
    }

    protected String callInfoService(String channelId) {
        cacheInfoTask(channelId);
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

    private void cacheInfoTask(String channelId) {
        redisService.setData(CommandType.INFO.getPrefix(), channelId);
    }

    private void cacheAlarmTask(String channelId, int targetPrice) {
        String value = channelId + ARGUMENTS_SEPARATOR + targetPrice;
        redisService.setData(CommandType.ALARM.getPrefix(), value);
    }

    private String convertTitleToKorean(List<MarketList> marketLists) {
        return marketLists.stream()
                .map(market -> String.format("시장 정보 : %s, 한글명 : %s, 영문명 : %s, 유의 종목 여부 : %s",
                        market.market(), market.koreanName(), market.englishName(), market.marketWarning()))
                .collect(Collectors.joining("\n"));
    }

}
