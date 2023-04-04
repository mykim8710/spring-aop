package io.spring.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    // io.spring.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* io.spring.aop.order..*(..))") //pointcut expression
    public void allOrder() {}  // pointcut signature

    // 클래스명이 *Service 패턴인 클래스 하부 메서드에 적용
    @Pointcut("execution(* *..*Service.*(..))") //pointcut expression
    public void allService() {}  // pointcut signature

    //allOrder && allService
    @Pointcut("allOrder() && allService()") //pointcut expression
    public void allOrderAndService() {}  // pointcut signature
}
