package spring.springcorebasic.findbeans;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;
import spring.springcorebasic.member.MemberService;
import spring.springcorebasic.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("Bean 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체화된 타입으로 조회")
    void findBeanByConcreteType() {
        // 추상이 아닌 구체에 의존한다는 점에서 좋은 코드는 아니지만 이런게 가능하다는 것을 보여주는 용도임
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("Bean 이름으로 조회 - 실패 케이스")
    void failToFindBeanByName() {
        // 주어진 이름으로 등록된 Bean이 없으면 NoSuchBeanDefinitionException 예외가 발생한다는 것을 검증
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("nonExistingBean", MemberService.class));
    }
}
