package hello.core.singleton;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {


    /**
     * StatefulService 의 price 필드는 공유되는 필드인데 특정 클라이언트가 값을 변경한다.
     * 사용자A의 주문금액은 10000원이 되어야하는데, 20000이라는 결과가 나왔다.
     * 실무에서는 이런 경우를 종종 보는데 이로인해 정말 해결하기 어려운 큰 문제들이 터진다
     * 진짜 공유필드는 조심해야 한다! 스프링 빈은 항상 무상태로 설계하자.
     *
     */
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class );
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);


        //ThreadA: A 사용자 10000원 주문
        statefulService1.order("userA",10000);

        //ThreadB: B 사용자 20000원 주문
        statefulService2.order("userB",20000);

        //ThreadA: 사용자 A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}