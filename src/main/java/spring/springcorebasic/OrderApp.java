package spring.springcorebasic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberService;
import spring.springcorebasic.order.Order;
import spring.springcorebasic.order.OrderService;

public class OrderApp {

    public static void main(String[] args) {
        /*
         ApplicationContext가 스프링 DI 컨테이너.
         AnnotationConfigApplicationContext는 AppConfig에서 @Configuration과 @Bean 어노테이션을 활용해 스프링 DI 컨테이너에
         객체들을 등록했기 때문에 사용하는 것.
         */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        /*
        getBean() 메서드는 스프링 DI 컨테이너에 등록된 bean을 주어진 이름과 클래스 타입을 가지고서 찾은 다음, 정보에 부합하는 객체를 반환함
        두번째 인자로 클래스 타입을 꼭 넘겨주는 이유는, 반환 받을 객체의 타입을 미리 지정해서 반환 이후 특정 타입으로 캐스팅하는 불편함을 덜기 위함
         */
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(member.getId(), "itemA", 30000);

        System.out.println("order: " + order);
    }
}
