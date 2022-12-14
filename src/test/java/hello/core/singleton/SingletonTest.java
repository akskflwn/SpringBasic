package hello.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        /**
         * memberService1 = hello.core.member.MemberServiceImpl@66d18979
         * memberService2 = hello.core.member.MemberServiceImpl@bccb269
         * 서로 참조값이 다름 singletonX
         */

        assertThat(memberService1).isNotSameAs(memberService2);
        /**
         * 고객 트래픽이 초당 100초가 나오면 최대 100개 객체가 생성되고 소멸된다 ->메모리 낭비가 심하다.
         * 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계해야한다. -> 싱글톤 패턴
         */
    }

    @Test
    @DisplayName("싱글톤패턴을 적용한 객체 사용")
    void SingletonServiceTest() {
        //private 으로 생성을 막음
//        SingletonTest singletonTest = new SingletonService();

        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        assertThat(singletonService1).isSameAs(singletonService2);
        //same == 비교와 같다 , 객체 인스턴스의 참조 주소를 비교하는 것
        //equal 은 equals 메서드와 같은것.
    }

    /**
     * 싱글톤 패턴의 문제점
     * <p>
     * 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다. 의존관게상 클라이언트가 구체 클래스에 의존한다. -> DIP를 위반 클라이언트가 구체 클래스에 의존해서 OCP
     * 원칙을 위반할 가능성이 높다. 테스트하기 어렵다. 내부 속성을 변경하거나 초기화 하기 어렵다. private 생성자로 자식 클래스를 만들기 어렵다. 결론적으로 유연성이
     * 떨어진다. 안티패턴으로 불리기도 한다.
     */

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        System.out.println("memberService2 = " + memberService2);
        System.out.println("memberService1 = " + memberService1);
        assertThat(memberService1).isSameAs(memberService2);
    }
    /**
     * 싱글톤 방식의 주의점
     * 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하면, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은
     * 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안된다.
     * 무상태로 설계해야한다!
     * 특정 클라이언트에 의존적인 필드가 있으면 안된다.
     * 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
     * 가급적 읽기만 사용해야 한다.
     * 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
     * 스프링 빈의 필드에 공유 값을 설정하면 큰 장애가 발생할 수 있다.
     */

}
