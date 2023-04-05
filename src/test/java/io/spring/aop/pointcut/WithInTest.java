package io.spring.aop.pointcut;

import io.spring.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class WithInTest {
    // AspectJExpressionPointcut : 포인트컷 표현식을 처리해주는 클래스
    // 여기에 포인트컷 표현식을 지정하면 된다.
    // AspectJExpressionPointcut는 상위에 Pointcut 인터페이스를 가진다.
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    void init() throws NoSuchMethodException {
        // 리플렉션으로 해당 클래스의 특정 메서드(hello) 정보 추출
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("타입과 매칭되는 WithIn 포인트컷 표현식을 설정한다.")
    void set_WithIn_PointcutExpression_Test() throws Exception {
        pointcut.setExpression("within(io.spring.aop.member.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입패턴과 매칭되는 WithIn 포인트컷 표현식을 설정한다.")
    void set_WithIn_PointcutExpression_TypePattern_Test() throws Exception {
        pointcut.setExpression("within(io.spring.aop.member.*ServiceImpl)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("서브패키지 하부 타입과 매칭되는 WithIn 포인트컷 표현식을 설정한다.")
    void set_WithIn_PointcutExpression_SubPackage_Test() throws Exception {
        pointcut.setExpression("within(io.spring.aop..*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입과 매칭되는 WithIn 포인트컷 표현식을 설정한다. - 정확하게 타입이 맞지않으면 안된다. 부모 - 자식(상속관계)")
    void set_WithIn_PointcutExpression_MatchFalse_Test() throws Exception {
        pointcut.setExpression("within(io.spring.aop.member.MemberService)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
