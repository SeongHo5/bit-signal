package net.bot.crypto.application.slack.event.listener;

import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.application.slack.enums.CommandType;
import net.bot.crypto.application.slack.event.SlackNotificationEvent;
import net.bot.crypto.application.slack.repository.SlackNotificationHistoryRepository;
import net.bot.crypto.application.slack.service.SlackFeignClient;
import net.bot.crypto.domain.dto.SlackMessageRequest;
import net.bot.crypto.domain.entity.SlackNotificationHistory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static net.bot.crypto.application.slack.constant.SlackContstant.ARGUMENTS_SEPARATOR;

@Component
@RequiredArgsConstructor
public class SlackEventListener {

    private final SlackFeignClient slackClient;
    private final RedisService redisService;
    private final SlackNotificationHistoryRepository historyRepository;

    @EventListener
    public void handleNotificationEvent(SlackNotificationEvent event) {
        String channelId = getChannelId(event);
        saveNotificationHistory(event, channelId);

        SlackMessageRequest request = new SlackMessageRequest(channelId, event.getMessage());

        slackClient.postMessage(request);
    }

    private String getChannelId(SlackNotificationEvent event) {
        String key = event.getType().getPrefix();
        String data = redisService.getData(key);
        if (key.equals(CommandType.ALARM.getPrefix())) {
            return parseAlarmData(data);
        }
        return data;
    }

    private String parseAlarmData(String data) {
        String[] parsedData = data.split(ARGUMENTS_SEPARATOR);
        return parsedData[0];
    }

    private void saveNotificationHistory(SlackNotificationEvent event, String channelId) {
        SlackNotificationHistory history = SlackNotificationHistory.builder()
                .channelId(channelId)
                .type(event.getType().name())
                .message(event.getMessage())
                .build();
        historyRepository.save(history);
    }

}
