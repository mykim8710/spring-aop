package io.spring.aop.pointcut;

import io.spring.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {
    Method helloMethod;

    @BeforeEach
    void init() throws NoSuchMethodException {
        // 리플렉션으로 해당 클래스의 특정 메서드(hello) 정보 추출
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private static AspectJExpressionPointcut createPointcut(String pointcutExpression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(pointcutExpression);
        return pointcut;
    }

    @Test
    @DisplayName("Args 포인트컷 표현식을 적용한다.")
    void set_args_pointcutExpression_test() throws Exception {
        //hello(String)과 매칭

        // String 타입의 파라미터만 매칭
        String pointcutExpression = "args(String)";
        assertThat(createPointcut(pointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // Object 타입(Java모든타입)의 파라미터 매칭
        pointcutExpression = "args(Object)";    // 부모객체의 타입도 허용
        assertThat(createPointcut(pointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // 파라미터가 없는 매칭
        pointcutExpression = "args()";
        assertThat(createPointcut(pointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();

        // 개수와 상관없이 모든타입의 파라미터 매칭
        pointcutExpression = "args(..)";
        assertThat(createPointcut(pointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // 정확히 하나이며 모든타입의 파라미터 매칭
        pointcutExpression = "args(*)";
        assertThat(createPointcut(pointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // String타입으로 시작하며 그 뒤로는 모든타입의 파라미터 매칭
        pointcutExpression = "args(String, ..)";
        assertThat(createPointcut(pointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)
     * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)
     */
    @Test
    @DisplayName("Args 포인트컷 표현식과 Execution 포인트컷 표현식을 비교 적용한다.")
    void compare_args_pointcutExpression_with_execution_pointcutExpression_test() throws Exception {
        // Args
        String argsPointcutExpression = "args(String)";
        assertThat(createPointcut(argsPointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // String은 Serializable(객체를 직렬화)를 구현
        argsPointcutExpression = "args(java.io.Serializable)";
        assertThat(createPointcut(argsPointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        argsPointcutExpression = "args(Object)";
        assertThat(createPointcut(argsPointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();


        // Execution
        String executionPointcutExpression = "execution(* *(String))";
        assertThat(createPointcut(executionPointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // 매칭실패 - 부모타입 허용안함
        executionPointcutExpression = "execution(* *(java.io.Serializable))";
        assertThat(createPointcut(executionPointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();

        // 매칭실패
        executionPointcutExpression = "execution(* *(Object))";
        assertThat(createPointcut(executionPointcutExpression)
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

}
