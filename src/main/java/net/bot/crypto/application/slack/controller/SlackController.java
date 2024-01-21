package net.bot.crypto.application.slack.controller;

import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.slack.service.SlackService;
import net.bot.crypto.application.domain.dto.SlashCommandRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slack")
public class SlackController {

    private final SlackService slackService;

    @PostMapping("/slash-command")
    public ResponseEntity<String> handleSlashCommand(
            final @RequestParam("channel_id") String channelId,
            final @RequestParam("channel_name") String channelName,
            final @RequestParam("user_id") String userId,
            final @RequestParam("user_name") String userName,
            final @RequestParam("command") String command,
            final @RequestParam("text") String text
    ) {
        SlashCommandRequest commandDto =
                new SlashCommandRequest(
                        channelId,
                        channelName,
                        userId,
                        userName,
                        command,
                        text);
        return slackService.handleSlashCommand(commandDto);
    }
}
