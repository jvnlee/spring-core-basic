package spring.springcorebasic;

import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.discount.FixedDiscountPolicy;
import spring.springcorebasic.member.MemberRepository;
import spring.springcorebasic.member.MemberService;
import spring.springcorebasic.member.MemberServiceImpl;
import spring.springcorebasic.member.MemoryMemberRepository;
import spring.springcorebasic.order.OrderService;
import spring.springcorebasic.order.OrderServiceImpl;

// 스프링 DI 컨테이너를 직접 구현해보기
// 역할과 구현 클래스가 한눈에 들어오게 작성
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private DiscountPolicy discountPolicy() {
        return new FixedDiscountPolicy();
    }
}
