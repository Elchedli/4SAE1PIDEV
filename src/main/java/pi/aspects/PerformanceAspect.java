package pi.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PerformanceAspect {
	
	@Around("execution(* tn.esprit.spring.services.*.*(..))")
    public void profile(ProceedingJoinPoint pjp) throws Throwable {
            long start = System.currentTimeMillis();
            pjp.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Method execution time: " + elapsedTime + " milliseconds.");
    }
}

