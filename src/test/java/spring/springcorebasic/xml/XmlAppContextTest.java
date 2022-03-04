package spring.springcorebasic.xml;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import spring.springcorebasic.member.MemberService;

import static org.assertj.core.api.Assertions.*;

public class XmlAppContextTest {

    @Test
    void xmlAppContext() {
        /*
        Java 코드와 어노테이션 기반의 AppConfig.java가 아닌 XML 양식에 맞는 appConfig.xml로 Application Context를 설정
        이 방법은 레거시에 속하므로, 레거시 코드를 읽을 때를 생각해 알아두는 정도로만.
         */
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
