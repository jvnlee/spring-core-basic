package spring.springcorebasic.autowired;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;
import spring.springcorebasic.member.Member;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    /*
    setter 메서드로 의존성 주입을 받는 테스트용 Bean
    이 Bean은 스프링 컨테이너 생성자 new AnnotationConfigApplicationContext()의 파라미터에 바로 주입시켰으므로
    따로 @Bean 어노테이션을 붙이지 않아도 단일 Bean으로서 스프링 컨테이너에 등록됨.
    */
    static class TestBean {

        /*
        의존성 주입을 시도할 Member 객체는 스프링 Bean으로 등록되어있지 않으므로 기본 옵션인 required=true로 돌리면 에러 발생
        required=false 옵션은 우선 파라미터에 있는, 의존성으로 주입해줄 Bean 객체가 존재하는지 확인하고, 없다면 이 setter 메서드 자체를 호출하지 않음
        */
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        /*
        setNoBean1은 주입할 Bean 객체가 존재하지 않으면 아예 호출이 안되었는데, 이 경우에는 객체 파라미터 앞에 @Nullable을 붙여줌으로써
        설령 등록된 해당 타입의 Bean이 없더라도 null을 주입하여 메서드를 호출함
        */
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        /*
        파라미터의 Bean 타입을 Optional로 감싸주면 해당 타입의 Bean이 존재할 시 그 객체를 Optional로 감싸서 주입해줄 것이고,
        존재하지 않는다면 null 대신 Optional.empty를 주입하여 메서드를 호출함.
        */
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
