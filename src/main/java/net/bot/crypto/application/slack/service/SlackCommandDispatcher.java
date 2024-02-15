package net.bot.crypto.application.slack.service;

import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.aop.exception.NoSuchServiceException;
import net.bot.crypto.domain.dto.request.RequestSlashCommand;
import org.springframework.stereotype.Service;

import static net.bot.crypto.application.aop.exception.ExceptionStatus.INVALID_INPUT_VALUE;
import static net.bot.crypto.application.aop.exception.ExceptionStatus.NO_SUCH_COMMAND;
import static net.bot.crypto.application.slack.constant.SlackContstant.*;
@Service
@RequiredArgsConstructor
public class SlackCommandDispatcher {

    private final SlackCommandService slackCommandService;


    /**
     * 슬랙으로부터 받은 슬래시 커맨드 명령어를 처리해 적절한 서비스를 호출하고, 응답을 반환한다.
     * <br>
     * <i>※주의※</i><br>
     * <i>슬래시 커맨드에 대한 응답은 요청을 정상적으로 수신했는지에 대한 응답이므로,
     * 존재하지 않는 커맨드에 대해서도 3초 이내에 응답을 보내야 한다.</i>
     * @param request 슬래시 커맨드 정보
     * @return 응답 메시지
     */
    public String handleSlashCommandInternal(RequestSlashCommand request) {
        slackCommandService.saveCommandHistory(request);

        String channelId = request.channelId();
        String parsedCommand = parseCommand(request.command());
        String arguments = request.text();

        return switch (parsedCommand) {
            case COMMAND_MARKET_LIST -> slackCommandService.callMarketListService();
            case COMMAND_INFO -> slackCommandService.callInfoService(channelId, arguments);
            case COMMAND_ALARM -> slackCommandService.callAlarmService(channelId, Integer.parseInt(arguments));
            case COMMAND_STOP -> slackCommandService.stopScheduledTask();
            default -> NO_SUCH_COMMAND.getMessage();
        };
    }

    private String parseCommand(String command) {
        if (command.startsWith(COMMAND_PREFIX)) {
            return command.substring(COMMAND_PREFIX.length());
        }
        throw new NoSuchServiceException(INVALID_INPUT_VALUE);
    }

}

