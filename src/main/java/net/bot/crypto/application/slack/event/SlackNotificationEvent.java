package net.bot.crypto.application.slack.event;

import lombok.Getter;
import net.bot.crypto.application.slack.enums.CommandType;
import org.springframework.context.ApplicationEvent;

@Getter
public class SlackNotificationEvent extends ApplicationEvent {

    private final CommandType type;
    private final String message;

    public SlackNotificationEvent(Object source, CommandType type, String message) {
        super(source);
        this.type = type;
        this.message = message;
    }

}
