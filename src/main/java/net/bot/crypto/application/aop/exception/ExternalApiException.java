package net.bot.crypto.application.aop.exception;

public class ExternalApiException extends ApiException {
    public ExternalApiException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
