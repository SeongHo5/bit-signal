package net.bot.crypto.application.slack.service;


import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.common.exception.NoSuchServiceException;
import net.bot.crypto.application.common.service.RedisService;
import net.bot.crypto.application.common.service.SchedulingService;
import net.bot.crypto.application.crypto.service.CryptoScheduledService;
import net.bot.crypto.application.slack.enums.CommandType;
import net.bot.crypto.application.slack.repository.SlackCommandHistoryRepository;
import net.bot.crypto.domain.dto.SlashCommandRequest;
import net.bot.crypto.domain.entity.SlackCommandHistory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.bot.crypto.application.common.exception.ExceptionStatus.INVALID_INPUT_VALUE;
import static net.bot.crypto.application.common.exception.ExceptionStatus.NO_SUCH_COMMAND;
import static net.bot.crypto.application.slack.constant.SlackContstant.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SlackService {

    private final SlackCommandHistoryRepository historyRepository;
    private final CryptoScheduledService cryptoScheduledService;
    private final RedisService redisService;

    /**
     * 슬랙으로부터 받은 슬래시 커맨드 명령어를 처리해 적절한 서비스를 호출하고, 응답을 반환한다.
     * <br>
     * <i>※주의※</i><br>
     * <i>슬래시 커맨드에 대한 응답은 요청을 정상적으로 수신했는지에 대한 응답이므로,
     * 존재하지 않는 커맨드에 대해서도 3초 이내에 응답을 보내야 한다.</i>
     * @param request 슬래시 커맨드 정보
     * @return 응답 메시지
     */
    public ResponseEntity<String> handleSlashCommand(SlashCommandRequest request) {
        saveCommandHistory(request);

        String channelId = request.channelId();
        String parsedCommand = parseCommand(request.command());
        String arguments = request.text();

        return switch (parsedCommand) {
            case COMMAND_INFO -> callInfoService(channelId);
            case COMMAND_ALARM -> callAlarmService(channelId, Integer.parseInt(arguments));
            case COMMAND_STOP -> stopScheduledTask();
            default -> ResponseEntity.ok().body(NO_SUCH_COMMAND.getMessage());
        };
    }

    private ResponseEntity<String> callInfoService(String channelId) {
        cacheInfoTask(channelId);
        return startScheduledTask(cryptoScheduledService::startCurrenyInfoTask, MESSAGE_WHEN_INFO_START);
    }

    private ResponseEntity<String> callAlarmService(String channelId, int targetPrice) {
        cacheAlarmTask(channelId, targetPrice);
        return startScheduledTask(cryptoScheduledService::startCurrencyAlarmTask, MESSAGE_WHEN_ALARM_START);
    }

    private ResponseEntity<String> startScheduledTask(Runnable task, String message) {
        task.run();
        return ResponseEntity.ok(message);
    }

    private ResponseEntity<String> stopScheduledTask() {
        cryptoScheduledService.stopScheduledTask();
        return ResponseEntity.ok(MESSAGE_WHEN_ALARM_STOP);
    }

    private String parseCommand(String command) {
        if (command.startsWith(COMMAND_PREFIX)) {
            return command.substring(COMMAND_PREFIX.length());
        }
        throw new NoSuchServiceException(INVALID_INPUT_VALUE);
    }

    private void cacheInfoTask(String channelId) {
        redisService.setData(CommandType.INFO.getPrefix(), channelId);
    }

    private void cacheAlarmTask(String channelId, int targetPrice) {
        String value = channelId + ARGUMENTS_SEPARATOR + targetPrice;
        redisService.setData(CommandType.ALARM.getPrefix(), value);
    }

    private void saveCommandHistory(SlashCommandRequest request) {
        SlackCommandHistory history = SlackCommandHistory.of(request);
        historyRepository.save(history);
    }
}
