package spring.springcorebasic.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
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
    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("subDiscountPolicy") DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /*
    DiscountPolicy 타입의 Bean은 Fixed와 Rated 2가지 종류가 존재하므로 스프링 컨테이너에게 이 중 뭘 주입할지 알려줘야함

    Bean이 등록될 때는 Bean의 이름을 객체의 이름과 같게 camel case로 등록하기 때문에 생성자 파라미터명을 원하는 Bean의 이름으로 바꾸면 됨
    예를 들어 Fixed를 원한다면, 파라미터명인 discountPolicy를 fixedDiscountPolicy로 고치면 됨
    이러한 방법은 OCP를 위반한다는 단점이 있으므로 권장되지 않음

    또 다른 방법은 @Qualifier, @Primary 어노테이션을 활용하는 것

    Fixed에는 @Qualifier("subDiscountPolicy")를 붙임
    이 Bean을 지목하려면 생성자의 파라미터 타입 앞에 똑같이 @Qualifier(...)를 붙여줘야함

    Rated에는 @Primary를 붙여, 따로 지목한 Bean이 없다면 이 @Primary 붙은 Bean을 사용하게끔 함
    따라서 @Primary Bean을 사용하고자할 때는 @Qualifier처럼 생성자 파라미터 타입 앞에 따로 어노테이션을 붙이지 않아도 됨

    @Qualifier가 @Primary보다 더 상세한 동작을 요구하므로 더 우선권을 가짐
    따라서 현재의 생성자 파라미터처럼 @Qualifier가 사용된 경우에는 설령 또 다른 Bean 중 @Primary가 존재한다 할지라도 @Qualifier Bean이 적용됨
    */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountAmount = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountAmount);
    }
}
