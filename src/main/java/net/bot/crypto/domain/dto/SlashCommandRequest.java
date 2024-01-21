package net.bot.crypto.domain.dto;

public record SlashCommandRequest(
        String channelId,
        String channelName,
        String userId,
        String userName,
        String command,
        String text
) {

}
