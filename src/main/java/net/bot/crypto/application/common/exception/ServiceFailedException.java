package net.bot.crypto.application.common.exception;

public class ServiceFailedException extends ApiException {
    public ServiceFailedException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
