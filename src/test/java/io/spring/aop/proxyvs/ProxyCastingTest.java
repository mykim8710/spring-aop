package io.spring.aop.proxyvs;

import io.spring.aop.member.MemberService;
import io.spring.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    @DisplayName("jdk동적프록시를 구체클래스로 캐스팅할때 예외가 발생한다.")
    void jdkDynamicProxyCastingTest() throws Exception {
        // 인터페이스도 있고 구체클래스도 있다.
        MemberServiceImpl target = new MemberServiceImpl(); // target class

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    // JDK 동적 프록시를 사용(인터페이스 기반 프록시 생성)

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class = {}", memberServiceProxy.getClass());

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        Assertions.assertThatThrownBy(() ->{
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("cglib프록시를 구체클래스로 캐스팅이 성공")
    void cglibProxyCastingTest() throws Exception {
        // 인터페이스도 있고 구체클래스도 있다.
        MemberServiceImpl target = new MemberServiceImpl(); // target class

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    // CGLIB를 사용해서 구체 클래스 기반 프록시 생성

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class = {}", memberServiceProxy.getClass());

        // CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        log.info("proxy class = {}", castingMemberService.getClass());
    }
}
