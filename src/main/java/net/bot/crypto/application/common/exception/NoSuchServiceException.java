package net.bot.crypto.application.common.exception;

public class NoSuchServiceException extends ApiException {
    public NoSuchServiceException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
