package net.bot.crypto.application.slack.event.listener;

import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.domain.dto.request.RequestSlackMessage;
import net.bot.crypto.domain.entity.SlackNotificationHistory;
import net.bot.crypto.application.slack.event.SlackNotificationEvent;
import net.bot.crypto.infrastructure.repository.SlackNotificationHistoryRepository;
import net.bot.crypto.application.slack.service.SlackFeignClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static net.bot.crypto.application.slack.constant.SlackContstant.ARGUMENTS_SEPARATOR;

@Component
@RequiredArgsConstructor
public class SlackEventListener {

    private final SlackFeignClient slackClient;
    private final RedisService redisService;
    private final SlackNotificationHistoryRepository historyRepository;

    @EventListener(classes = SlackNotificationEvent.class)
    public void handleNotificationEvent(SlackNotificationEvent event) {
        String channelId = getChannelId(event);
        saveNotificationHistory(event, channelId);

        RequestSlackMessage request = new RequestSlackMessage(channelId, event.getMessage());

        slackClient.postMessage(request);
    }

    private String getChannelId(SlackNotificationEvent event) {
        String key = event.getType().getPrefix();
        String data = redisService.getData(key, String.class);
        return parseChannelId(data);
    }

    private String parseChannelId(String data) {
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
