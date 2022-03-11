package spring.springcorebasic.beanscope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest {

    @Test
    void singletonWithPrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(SingletonBean.class, PrototypeBean.class);

        SingletonBean sb1 = ac.getBean(SingletonBean.class);
        int count1 = sb1.logic();
        assertThat(count1).isEqualTo(1);

        SingletonBean sb2 = ac.getBean(SingletonBean.class);
        int count2 = sb2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class SingletonBean {
        /*
        만약 생으로 PrototypeBean을 의존성으로 주입받게 되면, 주입 시점에 생성된 PrototypeBean을 가지고 계속 사용하게 됨
        이는 prototype scope의 취지에도 맞지 않는데, 만약 SingletonBean 내부에서 사용할 PrototypeBean을 요청할 때마다 새로 생성시켜
        주입받으려고 한다면 ObjectProvider를 사용하면 됨. (ObjectProvider는 스프링이 제공하는 기본 Bean 중에 하나)
        */
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public SingletonBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            /*
            getObject()가 스프링 컨테이너에 PrototypeBean를 하나 달라고 요청하는 역할 (새로 생성된 Bean을 받을 수 있음)
            이렇게 필요한 의존성을 직접 구해오도록 설정하는 것을 Dependency Lookup 이라고 함.
            */
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.incrementCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void incrementCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean: init() " + this);
        }
    }
}
