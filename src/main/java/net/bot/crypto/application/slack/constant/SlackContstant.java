package net.bot.crypto.application.slack.constant;

public final class SlackContstant {

    private SlackContstant() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    public static final String COMMAND_PREFIX = "/";
    public static final String COMMAND_INFO = "info";
    public static final String COMMAND_ALARM = "alarm";
    public static final String COMMAND_STOP = "stop";
    public static final String ARGUMENTS_SEPARATOR = "::";
    public static final String MESSAGE_WHEN_INFO_START = "1분마다 시세 정보를 알려드릴게요 :smile:";
    public static final String MESSAGE_WHEN_ALARM_START = "말씀하신 가격에 도달하면 알려드릴게요 :smile:";
    public static final String MESSAGE_WHEN_ALARM_STOP = ":bell: 실행 중인 알람을 모두 중지했어요.";

}
