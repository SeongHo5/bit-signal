package net.bot.crypto.application.aop.exception;

public class EntityNotFoundException extends ApiException {

    public EntityNotFoundException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }

}
