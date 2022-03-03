package spring.springcorebasic.discount;

import spring.springcorebasic.member.Member;

public interface DiscountPolicy {

    // 할인 금액 반환
    int discount(Member member, int price);

}
