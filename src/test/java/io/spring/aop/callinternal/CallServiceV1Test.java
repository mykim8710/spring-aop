package io.spring.aop.callinternal;

import io.spring.aop.callinternal.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV1Test {
    @Autowired
    CallServiceV1 callService;

    @Test
    @DisplayName("CallServiceV1의 external()을 호출한다.")
    void callExternalTest() throws Exception {
        log.info("callService proxy = {}", callService.getClass());
        callService.external();
    }

    @Test
    @DisplayName("CallServiceV1의 internal()을 호출한다.")
    void callInternalTest() throws Exception {
        log.info("callService proxy = {}", callService.getClass());
        callService.internal();
    }

}