package net.bot.crypto.application.slack.controller;

import lombok.RequiredArgsConstructor;
import net.bot.crypto.domain.dto.request.RequestSlashCommand;
import net.bot.crypto.application.slack.service.SlackCommandDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slack")
public class SlackController {

    private final SlackCommandDispatcher commandDispatcher;

    @PostMapping("/slash-command")
    public ResponseEntity<String> handleSlashCommand(
            @RequestParam("channel_id") final String channelId,
            @RequestParam("channel_name") final String channelName,
            @RequestParam("user_id") final String userId,
            @RequestParam("user_name") final String userName,
            @RequestParam("command") final String command,
            @RequestParam("text") final String text
    ) {
        RequestSlashCommand commandDto =
                new RequestSlashCommand(
                        channelId,
                        channelName,
                        userId,
                        userName,
                        command,
                        text);
        return ResponseEntity.ok(commandDispatcher.handleSlashCommandInternal(commandDto));
    }
}
