package net.bot.crypto.application.aop.asepct;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;

@Aspect
@Slf4j(topic = "exception")
@Profile("local")
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "execution(* *(..))", throwing = "ex")
    public void logAfterThrowingException(JoinPoint joinPoint, Exception ex) {
        log.error("Resolved Exception: {} / When Executing: {} / Message: {}",
                ex.getClass().getSimpleName(), joinPoint.getSignature(), ex.getMessage());
    }

}
