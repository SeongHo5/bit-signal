package net.bot.crypto.application.slack.controller;

import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.domain.dto.SlashCommandRequest;
import net.bot.crypto.application.slack.service.SlackService;
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
            @RequestParam("channel_id") final String channelId,
            @RequestParam("channel_name") final String channelName,
            @RequestParam("user_id") final String userId,
            @RequestParam("user_name") final String userName,
            @RequestParam("command") final String command,
            @RequestParam("text") final String text
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