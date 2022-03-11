package spring.springcorebasic.beanscope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Provider;

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
        ObjectProvider는 스프링이 제공하고 있기 때문에, 사용 시 코드가 스프링에 의존적이게 된다는 단점을 가짐.
        반면, javax.inject.Provider는 자바 표준, 즉 순수 자바 기능이므로 스프링 외의 다른 컨테이너서도 사용 가능하고, 단위 테스트나 mock 코드 작성에 유리함
        */
        private Provider<PrototypeBean> prototypeBeanProvider;

        public SingletonBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            // javax.inject.Provider는 ObjectProvider의 getObject()와 같은 기능으로 get()을 제공함 (DL 용도)
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
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
