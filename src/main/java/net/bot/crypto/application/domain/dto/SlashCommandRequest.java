package net.bot.crypto.application.domain.dto;

public record SlashCommandRequest(
        String channelId,
        String channelName,
        String userId,
        String userName,
        String command,
        String text
) {

}
