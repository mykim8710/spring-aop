package io.spring.aop.pointcut;

import io.spring.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest {
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
    @DisplayName("리플렉션으로 가져온 메서드의 정보를 콘솔에 찍는다.")
    void print_MethodInfo_By_Reflection_Test() throws Exception {
        // execution 포인트컷 표현식 : 메서드 정보를 매칭해서 포인트컷 대상을 찾아냄
        // public java.lang.String io.spring.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("hello method = {}", helloMethod);
    }

    @Test
    @DisplayName("MemberServiceImpl.hello(String)메서드에 가장 정확하게 모든 내용이 매칭되는 표현식을 설정한다.")
    void set_Execute_ExactPointcutExpression_ExactMatch_To_Method_Test() throws Exception {
        // 메서드 정보 : public java.lang.String io.spring.aop.member.MemberServiceImpl.hello(java.lang.String)

        // 메서드 정보를 토대로 포인트컷 표현식을 적용
        pointcut.setExpression("execution(public String io.spring.aop.member.MemberServiceImpl.hello(java.lang.String))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("가장 많이 생략한 표현식을 설정한다.")
    void set_Execute_SkipPointcutExpression_Test() throws Exception {
        // 메서드 정보 : public java.lang.String io.spring.aop.member.MemberServiceImpl.hello(java.lang.String)

        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* *(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 메서드 이름 매칭 관련 포인트컷 표현식
     */
    @Test
    @DisplayName("메서드 명과 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_MethodName_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* hello(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 명 패턴으로 매칭되는 포인트컷 표현식을 설정한다.1")
    void set_Execute_PointcutExpression_Match_To_MethodNamePattern1_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* hel*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 명 패턴으로 매칭되는 포인트컷 표현식을 설정한다.2")
    void set_Execute_PointcutExpression_Match_To_MethodNamePattern2_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* *el*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 명과 매칭에 실패하는 포인트컷 표현식을 설정한다")
    void set_Execute_PointcutExpression_MatchFalse_To_MethodName_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* nono*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    /**
     * 패키지 매칭 관련 포인트컷 표현식
     */
    @Test
    @DisplayName("패키지 명과 가장 정확하게 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_ExactMatch_To_PackageName_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* io.spring.aop.member.MemberServiceImpl.hello(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 명과 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_PackageName_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* io.spring.aop.member.*.*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 명과 매칭에 실패되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_MatchFalse_To_PackageName_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* io.spring.aop.*.*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("하위 패키지까지 매칭되는 포인트컷 표현식을 설정한다. 1")
    void set_Execute_PointcutExpression_Match_To_SubPackageName1_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* io.spring.aop.member..*.*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("하위 패키지까지 매칭되는 포인트컷 표현식을 설정한다. 2")
    void set_Execute_PointcutExpression_Match_To_SubPackageName2_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* io.spring.aop..*.*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 타입 매칭 - 부모 타입 허용
     */
    @Test
    @DisplayName("클래스타입과 정확히 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_ExactMatch_To_ClassType_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* io.spring.aop.member.MemberServiceImpl.*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모 클래스타입과 매칭되는 포인트컷 표현식을 설정한다. - 자식클래스 타입도 매칭된다.")
    void set_Execute_PointcutExpression_Match_To_SuperClassType_Test() throws Exception {
        // 포인트컷 표현식을 적용
        pointcut.setExpression("execution(* io.spring.aop.member.MemberService.*(..))");

        // 지정한 포인트컷 표현식의 매칭여부 확인
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 타입 매칭 - 부모 타입에 있는 메서드만 허용
     */
    @Test
    @DisplayName("자식클래스에 있는 메서드만 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_MethodInThisClassType_Test() throws Exception {
        pointcut.setExpression("execution(* io.spring.aop.member.MemberServiceImpl.*(..))");

        // 자식클래스에만 있는 메서드 : 리플렉션으로 해당 클래스의 특정 메서드(internal) 정보 추출
        Method internal = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internal, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모 클래스타입에 있는 메서드만 매칭되는 포인트컷 표현식을 설정한다. - 자식클래스에 있는 메서드는 매칭되지않는다.")
    void set_Execute_PointcutExpression_MatchFalse_To_MethodInSuperClassType_Test() throws Exception {
        pointcut.setExpression("execution(* io.spring.aop.member.MemberService.*(..))");

        // 자식클래스에만 있는 메서드 : 리플렉션으로 해당 클래스의 특정 메서드(internal) 정보 추출
        Method internal = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internal, MemberServiceImpl.class)).isFalse();
    }


    /**
     * 파라미터 매칭
     */

    // String 타입의 파라미터 허용
    // (String)
    @Test
    @DisplayName("String 타입의 파라미터만 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_ArgumentStringType_Test() throws Exception {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터가 없어야 함
    // ()
    @Test
    @DisplayName("파라미터가 없는 경우 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_NoArgument_Test() throws Exception {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 정확히 하나의 파라미터 허용, 모든 타입 허용
    // (Xxx)
    @Test
    @DisplayName("정확히 하나의 파라미터이며 모든 타입이 허용 경우 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_OnlyOneArgumentAndAllType_Test() throws Exception {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터 수와 상관없이, 모든 타입 허용
    // 파라미터가 없어도 됨
    @Test
    @DisplayName("파라미터 수와 상관없이, 모든 타입 허용되는 경우 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_MultiArgumentAndAllType_Test() throws Exception {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // String 타입으로 시작, 파라미터 수와 상관없이, 모든 타입 허용
    // (String), (String, Xxx), (String, Xxx, Xxx) 허용
    @Test
    @DisplayName("String 타입으로 시작, 파라미터 수와 상관없이, 모든 타입 허용되는 경우 매칭되는 포인트컷 표현식을 설정한다.")
    void set_Execute_PointcutExpression_Match_To_MultiArgumentAndStartSpringTypeAndAllType_Test() throws Exception {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
