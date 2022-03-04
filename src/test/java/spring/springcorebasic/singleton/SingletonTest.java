package spring.springcorebasic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;
import spring.springcorebasic.member.MemberService;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 클라이언트1이 서비스를 호출함
        MemberService memberService1 = appConfig.memberService();

        // 클라이언트2가 서비스를 호출함
        MemberService memberService2 = appConfig.memberService();

        /*
        서비스 객체가 요청이 들어올 때마다 새로 생성되므로, 메모리 낭비가 심함
        이를 방지하기 위해 스프링 컨테이너는 싱글톤 (오직 1개의 객체만 생성하고 모두가 공유) 패턴을 사용함
        */
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체가 유일무이한지 검증")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        // isSameAs()는 비교 대상 객체가 같은 참조를 가지는지 검증함
        assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("싱글톤 패턴이 적용된 스프링 컨테이너")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        /*
        AppConfig에는 싱글톤 패턴 구현을 위한 거추장스러운 코드들이 없는데도, 스프링 컨테이너의 내부 로직 상
        기본적으로 모든 Bean을 싱글톤으로 관리해주기 때문에 프로그래머는 싱글톤이 가진 단점은 모두 회피하면서
        싱글톤의 이점만 가지고서 애플리케이션을 만들 수 있게됨

        (스프링에서 빈 등록 방식을 싱글톤으로만 할 수 있는 것은 아님. 요청할 때마다 새로운 객체를 생성해서
        반환하게끔 설정할 수도 있긴함. 그러나 사실상 기본 방식인 싱글톤만 사용...)
        */

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        assertThat(memberService1).isSameAs(memberService2);
    }
}
