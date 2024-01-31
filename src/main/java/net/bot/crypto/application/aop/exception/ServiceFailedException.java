package net.bot.crypto.application.aop.exception;

public class ServiceFailedException extends ApiException {
    public ServiceFailedException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
