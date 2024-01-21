package net.bot.crypto.application.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionStatus {
    NO_SUCH_COMMAND(400, "존재하지 않는 명령어입니다."),
    INVALID_INPUT_VALUE(400, "잘못된 입력값입니다."),
    FAILED_HTTP_REQUEST(400, "잘못된 요청입니다."),
    EXTERNAL_API_ERROR(500, "외부 API 호출 중 에러가 발생하였습니다."),
    UNKNOWN_ERROR(500, "알 수 없는 에러가 발생하였습니다.");

    private final Integer statusCode;
    private final String message;

    ExceptionStatus(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
