package io.spring.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution(* io.spring.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[LOG] {}", joinPoint.getSignature()); // joinPoint.getSignature() : 메서드의 정보
        return joinPoint.proceed(); // 실제 Target 호출
    }
}
