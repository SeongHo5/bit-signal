package net.bot.crypto.application.aop.exception;

public class NoSuchServiceException extends ApiException {
    public NoSuchServiceException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
