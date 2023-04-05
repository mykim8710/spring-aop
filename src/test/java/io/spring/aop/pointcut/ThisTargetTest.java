package io.spring.aop.pointcut;

import io.spring.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({ThisTargetTest.ThisTargetAspect.class})
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK 동적 프록시(interface)
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB(Implement)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void thisTargetTest() throws Exception {
        log.info("memberService proxy = {}", memberService.getClass());
        memberService.hello("hi");
    }


    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        // 부모 타입 허용
        @Around("this(io.spring.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모 타입 허용
        @Around("this(io.spring.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }


        // this : 스프링 AOP 프록시 객체 대상
        // JDK 동적 프록시는 인터페이스를 기반으로 생성되므로 구현 클래스를 알 수 없음
        // CGLIB 프록시는 구현 클래스를 기반으로 생성되므로 구현 클래스를 알 수 있음
        @Around("this(io.spring.aop.member.MemberServiceImpl)")
        public Object doThisImplement(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-implement] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //target: 실제 target 객체 대상
        @Around("this(io.spring.aop.member.MemberService)")
        public Object doTargetImplement(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-implement] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}
