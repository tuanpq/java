package io.github.tuanpq.trace.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pure AspectJ aspect (woven at build time by the AspectJ compiler, not Spring AOP)
 * that logs the entry and exit of every method in the application.
 */
@Aspect
public class MethodTraceAspect {

    private static final Logger log = LoggerFactory.getLogger("io.github.tuanpq.trace.TRACE");

    /** Matches every method execution in the application, except the aspect itself. */
    @Pointcut("execution(* io.github.tuanpq.trace..*(..)) && !within(io.github.tuanpq.trace.aspect..*)")
    public void applicationMethods() {
    }

    @Around("applicationMethods()")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        String signature = joinPoint.getSignature().toShortString();
        log.info("ENTER {}", signature);
        long start = System.nanoTime();
        try {
            Object result = joinPoint.proceed();
            long tookMs = (System.nanoTime() - start) / 1_000_000;
            log.info("EXIT  {} ({} ms)", signature, tookMs);
            return result;
        } catch (Throwable t) {
            long tookMs = (System.nanoTime() - start) / 1_000_000;
            log.info("EXIT  {} ({} ms) threw {}", signature, tookMs, t.getClass().getName());
            throw t;
        }
    }
}
