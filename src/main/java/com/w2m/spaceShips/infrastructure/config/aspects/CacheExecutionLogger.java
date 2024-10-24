package com.w2m.spaceShips.infrastructure.config.aspects;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * @author javiloguai
 * <p>
 * Class for logging time execution and compare times between cached JPA transactions
 * and undertanding difference between aspect user defined annotation and packaged controlled aspect trasnsactions
 */
@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class CacheExecutionLogger {

    private static final Logger LOGGER = LogManager.getLogger(CacheExecutionLogger.class);

    @Pointcut("execution(public * com.w2m.spaceShips.infrastructure.restapi.controllers.*.*(..))")
    private void interceptPublicControllerJoinPoint() {
    }

    @Around(value = "interceptPublicControllerJoinPoint()")
    public Object logCacheExecutionTime(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object o = point.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info(String.format("Class Name: %s. Method Name: %s. Time taken for Execution is : %dms",
                point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), (end - start)));
        return o;
    }
}
