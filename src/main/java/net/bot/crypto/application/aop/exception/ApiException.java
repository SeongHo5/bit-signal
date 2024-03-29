package net.bot.crypto.application.aop.exception;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {

    protected final Integer statusCode;
    protected final String message;

    protected ApiException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.statusCode = exceptionStatus.getStatusCode();
        this.message = exceptionStatus.getMessage();
    }

}
