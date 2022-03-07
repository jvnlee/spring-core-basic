package spring.springcorebasic.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.discount.FixedDiscountPolicy;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberRepository;
import spring.springcorebasic.member.MemoryMemberRepository;

@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /*
    생성자에 @Autowired를 붙여주면 스프링 컨테이너가 등록되어 있는 Bean들 중에서 필요한 의존 클래스를 찾아 주입해줌
    이 때 제시된 타입을 기준으로 찾는데, 아래에서는 MemberRepository 타입과 DiscountPolicy 타입에 해당하는 클래스를 각각 찾음
     */

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
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
