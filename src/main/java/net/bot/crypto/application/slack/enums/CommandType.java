package net.bot.crypto.application.slack.enums;

import lombok.Getter;

@Getter
public enum CommandType {
    INFO("::INFO::"),
    ALARM("::ALARM::");

    private final String prefix;

    CommandType(String prefix) {
        this.prefix = prefix;
    }
}
