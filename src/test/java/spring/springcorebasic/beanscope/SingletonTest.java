package spring.springcorebasic.beanscope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    void findSingletonBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);

        // singleton Bean이므로 컨테이너에는 유일무이한 객체로 등록됨. 따라서 조회할 때마다 항상 같은 Bean 반환
        assertThat(singletonBean1).isSameAs(singletonBean2);

        // @PreDestroy 메서드를 호출하기 위해 컨테이너를 종료함
        ac.close();
    }

    @Scope("singleton")
    // singleton scope는 Bean의 기본 scope라서 안써도 되는데 학습용이므로 적었음
    static class SingletonBean {

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
