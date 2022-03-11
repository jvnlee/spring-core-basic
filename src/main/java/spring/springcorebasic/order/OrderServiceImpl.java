package spring.springcorebasic.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.springcorebasic.annotation.MainDiscountPolicy;
import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.discount.FixedDiscountPolicy;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberRepository;
import spring.springcorebasic.member.MemoryMemberRepository;

@Component
/*
@RequiredArgsConstructor
lombok 어노테이션으로 의존성을 주입하는 생성자 작성을 생략함 (컴파일 시점에 같은 내용으로 작성해서 포함시켜줌)
*/
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountAmount = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountAmount);
    }
}
