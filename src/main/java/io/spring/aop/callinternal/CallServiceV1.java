package io.spring.aop.callinternal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {
    private CallServiceV1 callServiceV1;  // 자기자신(프록시)

    // 생성자방식으로 자기자신 객체를 주입받으려하면 자기자신 객체가 생성이 되었는지 안되었는지 모름으로 setter방식으로 주입
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter={}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 메서드 호출
    }
    public void internal() {
        log.info("call internal");
    }

}
