package io.spring.aop.proxyvs;

import io.spring.aop.member.MemberService;
import io.spring.aop.member.MemberServiceImpl;
import io.spring.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})  // JDK 동적 프록시, DI 예외 발생
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // CGLIB 프록시, 성공
@SpringBootTest // CGLIB 프록시, default
@Import(ProxyDIAspect.class)
public class ProxyDITest {
    @Autowired
    MemberService memberService; // JDK 동적 프록시 OK, CGLIB OK
    @Autowired
    MemberServiceImpl memberServiceImpl; // JDK 동적 프록시 X, CGLIB OK

    @Test
    @DisplayName("JDK 동적프록시, CGLIB를 번갈아가면서 프록시 빈 주입을 한다.")
    void proxyBeanDITest() throws Exception {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
