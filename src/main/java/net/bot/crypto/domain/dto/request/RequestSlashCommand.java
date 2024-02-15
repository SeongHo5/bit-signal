package net.bot.crypto.domain.dto.request;

public record RequestSlashCommand(
        String channelId,
        String channelName,
        String userId,
        String userName,
        String command,
        String text
) {

}
