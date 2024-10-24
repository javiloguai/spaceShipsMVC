package com.w2m.spaceShips.infrastructure.config.aspects;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author javiloguai
 * <p>
 * Class for logging when a spaceship with a negative id is retrieved
 * and and undertanding difference between aspect user defined annotation and packaged controlled aspect trasnsactions
 */
@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class NegativeIdLogger {

    private static final Logger LOGGER = LogManager.getLogger(NegativeIdLogger.class);

    @Around("@annotation(com.w2m.spaceShips.infrastructure.config.aspects.annotations.LogNegativeIdParam)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        if (args.length > 0 && (long) args[0] < 0) {
            LOGGER.info(String.format("Detected a transaction with a negative ID value. \r\nClass Name: %s. Method Name: %s. parameter value is : %s",
                    proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName(), Arrays.toString(args)));
        }

        Object o = proceedingJoinPoint.proceed();

        return o;
    }

}
