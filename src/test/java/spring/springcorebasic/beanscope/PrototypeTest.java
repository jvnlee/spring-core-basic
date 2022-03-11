package spring.springcorebasic.beanscope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {

    @Test
    void findPrototypeBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        /*
        prototype scope의 경우는 스프링 컨테이너 생성 시점에 Bean이 함께 생성되지 않음
        따라서 getBean()처럼 Bean을 찾으려고 할 때 비로소 Bean이 생성됨
        */
        System.out.println("prototypeBean1 찾기");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean2 찾기");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        // prototype Bean이므로 컨테이너에는 각각 별개로 생성됨 따라서 조회할 때마다 항상 다른 Bean 반환
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        /*
        prototype Bean은 이 Bean을 필요로 할 때에 스프링 컨테이너가 생성하고, 의존성 주입 및 초기(@PostConstruct)까지만 해준 뒤에 더 이상 보관(관리)하지 않음
        따라서 컨테이너 종료를 위해 close()를 호출해도 Bean 소멸 전 호출되어야할 @PreDestroy 메서드가 호출되지 않음
        */
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("init() 호출");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("destroy() 호출");
        }
    }
}
