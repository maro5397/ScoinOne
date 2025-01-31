package com.scoinone.streamer.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.scoinone.streamer.controller..*(..))")
    public void logControllerEntry(JoinPoint joinPoint) {
        log.info("Entering API: {}", joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.scoinone.streamer.controller..*(..))")
    public void logControllerExit(JoinPoint joinPoint) {
        log.info("Exiting API: {}", joinPoint.getSignature().toShortString());
    }

    @Before("execution(* com.scoinone.streamer.service.impl..*(..))")
    public void logServiceEntry(JoinPoint joinPoint) {
        log.info("Entering Service: {}", joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.scoinone.streamer.service.impl..*(..))")
    public void logServiceExit(JoinPoint joinPoint) {
        log.info("Exiting Service: {}", joinPoint.getSignature().toShortString());
    }
}
