package io.spring.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    // 클래스명이 *Service 패턴인 클래스 하부 메서드에 적용
    @Pointcut("execution(* *..*Service.*(..))") //pointcut expression
    private void allService() {}  // pointcut signature

    // io.spring.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* io.spring.aop.order..*(..))") //pointcut expression
    private void allOrder() {}  // pointcut signature

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[LOG] {}", joinPoint.getSignature()); // joinPoint.getSignature() : 메서드의 정보
        return joinPoint.proceed(); // 실제 Target 호출
    }

    // io.spring.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();    // target 호출
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}

