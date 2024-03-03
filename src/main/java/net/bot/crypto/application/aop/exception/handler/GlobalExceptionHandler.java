package net.bot.crypto.application.aop.exception.handler;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import net.bot.crypto.application.aop.exception.ApiException;
import net.bot.crypto.application.aop.exception.NoSuchServiceException;
import net.bot.crypto.domain.ErrorResponse;
import net.bot.crypto.domain.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static net.bot.crypto.domain.ResponseHandler.createResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final String MESSAGE_ERROR_500 = "서버 내부 에러가 발생했습니다.";

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ResponseHandler<ErrorResponse>> handleUncaughtException(Exception ex) {
        return handleExceptionInternal(MESSAGE_ERROR_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ApiException.class})
    protected ResponseEntity<ResponseHandler<ErrorResponse>> handleApiException(ApiException ex) {
        return handleExceptionInternal(ex.getMessage(), HttpStatus.valueOf(ex.getStatusCode()));
    }

    /**
     * 존재하지 않는 Slash Command 명령어를 입력했을 경우 / 명령어는 존재하지 않지만 커맨드 요청을 정상 수신했으므로 OK로 응답해야 한다.
     */
    @ExceptionHandler({NoSuchServiceException.class})
    protected ResponseEntity<ResponseHandler<ErrorResponse>> handleNoSuchServiceException(NoSuchServiceException ex) {
        return handleExceptionInternal(ex.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler({RedisException.class})
    protected ResponseEntity<ResponseHandler<ErrorResponse>> handleRedisException(RedisException ex) {
        log.error("Resolved {} - Reason : {}", ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        return handleExceptionInternal(MESSAGE_ERROR_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ResponseHandler<ErrorResponse>> handleExceptionInternal(String message, HttpStatus status) {
        return createResponse(status, message, null);
    }

}
