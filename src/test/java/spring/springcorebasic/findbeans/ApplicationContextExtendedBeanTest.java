package spring.springcorebasic.findbeans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.discount.FixedDiscountPolicy;
import spring.springcorebasic.discount.RatedDiscountPolicy;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextExtendedBeanTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy ratedDiscountPolicy() {
            return new RatedDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixedDiscountPolicy() {
            return new FixedDiscountPolicy();
        }
    }

    @Test
    @DisplayName("부모 타입으로 조회할 때, 자식이 둘 이상 있으면 중복 오류 발생")
    void findBeanByParentType() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("자식이 둘 이상 있는 부모 타입으로 조회할 때 Bean 이름을 지정해서 조회")
    void findBeanByParentTypeAndName() {
        DiscountPolicy ratedDiscountPolicy = ac.getBean("ratedDiscountPolicy", DiscountPolicy.class);
        assertThat(ratedDiscountPolicy).isInstanceOf(RatedDiscountPolicy.class);
    }

    @Test
    @DisplayName("자식이 둘 이상 있는 부모 아래의 Bean을 특정 하위 타입으로 조회")
    void findBeanByConcreteChildType() {
        // 이 방법은 하위 타입 (구체)에 의존하는 코드가 되므로 권장하지 않음. 설명을 위한 용도.
        RatedDiscountPolicy bean = ac.getBean(RatedDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RatedDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        // 학습용이기 때문에 콘솔 출력으로도 검증하는거지만, 실제 테스트 코드 작성 시에는 안하는게 바람직 (테스트 결과는 오로지 테스트 프레임워크의 판단에 맡김)
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }
}
