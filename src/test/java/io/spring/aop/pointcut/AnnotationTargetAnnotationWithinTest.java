package io.spring.aop.pointcut;

import io.spring.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({AnnotationTargetAnnotationWithinTest.Config.class})
@SpringBootTest
public class AnnotationTargetAnnotationWithinTest {

    @Autowired
    Child child;

    @Test
    void annotationTargetAnnotationWithinTest() throws Exception {
        log.info("child Proxy = {}", child.getClass());
        child.parentMethod();
        child.childMethod();
    }


    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Child child() {
            return new Child();
        }

        @Bean
        public AnnotationTargetAnnotationWithinAspect annotationTargetAnnotationWithinAspect() {
            return new AnnotationTargetAnnotationWithinAspect();
        }
    }


    static class Parent {
        public void parentMethod(){

        }
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod(){

        }
    }

    @Slf4j
    @Aspect
    static class AnnotationTargetAnnotationWithinAspect {
        // @target: 인스턴스 기준으로 모든 메서드의 조인 포인트를 선정, 부모 타입의 메서드도 적용
        @Around("execution(* io.spring.aop..*(..)) && @target(io.spring.aop.member.annotation.ClassAop)")
        public Object annotationTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // @within: 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정, 부모 타입의 메서드는 적용되지 않음
        @Around("execution(* io.spring.aop..*(..)) && @within(io.spring.aop.member.annotation.ClassAop)")
        public Object annotationWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
