package spring.springcorebasic.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RatedDiscountPolicyTest {

    RatedDiscountPolicy ratedDiscountPolicy = new RatedDiscountPolicy();

    // 성공 케이스 테스트
    @Test
    @DisplayName("VIP 고객은 10% 할인이 적용되어야 함")
    void vipDiscount() {
        // given
        Member member = new Member(1L, "vipMember", Grade.VIP);

        // when
        int discount = ratedDiscountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    // 실패 케이스 테스트
    @Test
    @DisplayName("VIP가 아닌 고객은 할인이 적용되지 않아야 함")
    void noDiscount() {
        // given
        Member member = new Member(2L, "vipMember", Grade.BASIC);

        // when
        int discount = ratedDiscountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(1000);
    }
}