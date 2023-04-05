package io.spring.aop.member;

import io.spring.aop.member.annotation.ClassAop;
import io.spring.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        log.info("[MemberService.hello] param = {}", param);
        return "OK";
    }

    public String internal(String param) {
        log.info("[internal] param = {}", param);
        return "ok";
    }
}
