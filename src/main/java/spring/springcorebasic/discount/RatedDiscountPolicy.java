package spring.springcorebasic.discount;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import spring.springcorebasic.annotation.MainDiscountPolicy;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;

@Component
@MainDiscountPolicy
public class RatedDiscountPolicy implements DiscountPolicy {

    private int discountRate = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountRate / 100;
        } else {
            return 0;
        }
    }
}
