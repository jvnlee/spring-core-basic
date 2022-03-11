package spring.springcorebasic.autowired;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AutoAppConfig;
import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBeans() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int fixedDiscount = discountService.discount(member, 10000, "fixedDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixedDiscount).isEqualTo(1000);

        int ratedDiscount = discountService.discount(member, 20000, "ratedDiscountPolicy");
        assertThat(ratedDiscount).isEqualTo(2000);
    }

    // 테스트를 위한 가상의 Bean
    static class DiscountService {
        /*
        Map과 List를 사용하므로, 그 동안 단일 Bean만 의존성으로 갖던 객체와는 달리 제시된 타입에 부합하는 모든 Bean을 주입받을 수 있음
        이러한 방식은 같은 타입의 Bean 여러개를 불러와 그 중 하나를 동적으로 선택해서 사용해야할 때 유용함.
        */
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
        }

        public int discount(Member member, int price, String policyName) {
            // Map에는 Bean명-Bean객체 쌍으로 저장되어 있으므로 이름으로 Bean을 조회해서 꺼내옴
            DiscountPolicy discountPolicy = policyMap.get(policyName);
            return discountPolicy.discount(member, price);
        }
    }
}
