package io.spring.aop;

import io.spring.aop.order.OrderRepository;
import io.spring.aop.order.OrderService;
import io.spring.aop.order.aop.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
//@Import(AspectV1.class) // AspectV1 빈 등록
//@Import(AspectV2.class) // AspectV2 빈 등록
//@Import(AspectV3.class) // AspectV3 빈 등록
//@Import(AspectV4Pointcut.class) // AspectV4 빈 등록
//@Import({AspectV5Order.TransactionAspect.class, AspectV5Order.LogAspect.class}) // AspectV5 빈 등록
@Import(AspectV6Advice.class) // AspectV4 빈 등록
public class AopTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("AOP 프록시의 적용여부를 출력한다.")
    void aopInfoPrintTest() throws Exception {
        log.info("isAopProxy, orderService = {}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository = {}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    @DisplayName("OrderService의 orderItem()을 호출하면 정상작동한다.")
    void callOrderServiceSuccessTest() {
        orderService.orderItem("itemA");
    }

    @Test
    @DisplayName("OrderService의 orderItem()을 호출하면 IllegalStateException 예외가 터진다.")
    void callOrderServiceExceptionTest() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }

}
