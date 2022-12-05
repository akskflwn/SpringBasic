package hello.core.beabdefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    /**
     * beanDefinitionName = appConfig beanDefinition = Generic bean: class
     * [hello.core.AppConfig$$EnhancerBySpringCGLIB$$d6ae189e]; scope=singleton; abstract=false;
     * lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
     * factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null
     * beanDefinitionName = memberService beanDefinition = Root bean: class [null]; scope=;
     * abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true;
     * primary=false; factoryBeanName=appConfig; factoryMethodName=memberService;
     * initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
     * beanDefinitionName = memberRepository beanDefinition = Root bean: class [null]; scope=;
     * abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true;
     * primary=false; factoryBeanName=appConfig; factoryMethodName=memberRepository;
     * initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
     * beanDefinitionName = orderService beanDefinition = Root bean: class [null]; scope=;
     * abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true;
     * primary=false; factoryBeanName=appConfig; factoryMethodName=orderService;
     * initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
     * beanDefinitionName = discountPolicy beanDefinition = Root bean: class [null]; scope=;
     * abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true;
     * primary=false; factoryBeanName=appConfig; factoryMethodName=discountPolicy;
     * initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
     *
     * BeanClassName : 생성할 빈의 클래스 명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
     * factoryBeanName : 팩토리 역활의 빈을 사용할 경우 이름 , 예) appConfig
     * factoryMethodName : 빈을 생성할 팩토리 메서드 지정 , 예)memberService
     * Scope : 싱글톤 (기본값)
     * lazyInit : 스프링 컨테이너를 생성할 때 빈을 생성하는 것이 아니라, 실제 빈을 사용할 떄 까지 최대한 생성을 지연처리하는지 여부
     *
     * (lifeCycle)
     * InitMethodName: 빈을 생성하고, 의존 관계를 적용한 뒤에 호출되는 초기화 메서드 명
     * DestroyMethodName: 빈 생명주기가 끝나서 제거하기 직전에 호출되는 메서드명
     * Constructor arguments, Properties : 의존관계 주입에서 사용된다.(자바 설정 처럼 팩토리 역활의 빈을 사용하면 없음)
     */
    @Test
    @DisplayName("빈 설정 메타 정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName);
                System.out.println("beanDefinition = " + beanDefinition);
            }

        }
    }
}