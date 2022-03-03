package spring.springcorebasic.discount;

import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;

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
