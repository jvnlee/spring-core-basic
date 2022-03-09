package spring.springcorebasic.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;

@Component
@Qualifier("subDiscountPolicy")
public class FixedDiscountPolicy implements DiscountPolicy {

    private int fixedDiscountAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return fixedDiscountAmount;
        } else {
            return 0;
        }
    }
}
