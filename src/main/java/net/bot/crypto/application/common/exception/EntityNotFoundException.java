package net.bot.crypto.application.common.exception;

public class EntityNotFoundException extends ApiException {

    public EntityNotFoundException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }

}
