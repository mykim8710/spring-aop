package io.spring.aop.exam;

import io.spring.aop.exam.aop.RetryAspect;
import io.spring.aop.exam.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
class ExamTest {
    @Autowired
    private ExamService examService;

    @Test
    @DisplayName("examService에 request 메서드를 5번 실행한다.")
    void callExamServiceRequestMethodTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            log.info("client request i = {}", i);
            examService.request("data_" +i);
        }
    }

}