package net.bot.crypto.application.slack.constant;

public final class SlackContstant {

    private SlackContstant() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    public static final String COMMAND_PREFIX = "/";

    public static final String COMMAND_MARKET_LIST = "market-list";
    public static final String COMMAND_INFO = "info";
    public static final String COMMAND_ALARM = "alarm";
    public static final String COMMAND_STOP = "stop";
    public static final String ARGUMENTS_SEPARATOR = "::";

}
