package io.spring.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // io.spring.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* io.spring.aop.order..*(..))") //pointcut expression
    private void allOrder() {}  // pointcut signature

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[LOG] {}", joinPoint.getSignature()); // joinPoint.getSignature() : 메서드의 정보
        return joinPoint.proceed(); // 실제 Target 호출
    }
}
