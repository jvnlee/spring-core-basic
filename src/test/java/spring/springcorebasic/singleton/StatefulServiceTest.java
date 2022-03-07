package spring.springcorebasic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Configuration
    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

    }

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // 유저A가 10000원 어치 주문
        statefulService1.order("userA", 10000);
        // 유저B가 20000원 어치 주문
        statefulService2.order("userB", 20000);

        // 유저A가 본인의 주문 금액을 조회
        int price = statefulService1.getPrice();

        /*
        유저A는 10000원 어치 주문했음에도, 조회하려는 사이에 유저B가 20000원 어치 주문을 함으로써 싱글톤 객체의 가격 필드(상태)를 변경해버렸음
        그래서 유저A가 조회한 본인의 주문 금액이 20000원이 되버리는 불상사 발생.

        이런 불상사 때문에, 싱글톤인 스프링 Bean은 항상 stateless(무상태)로 설계해야함
        특정 클라이언트가 값을 변경할 수 있는 공유 필드가 존재해서는 안됨. (그 대신 지역 변수나 파라미터, ThreadLocal 등을 사용하자)
        */
        Assertions.assertThat(price).isEqualTo(20000);
    }
}