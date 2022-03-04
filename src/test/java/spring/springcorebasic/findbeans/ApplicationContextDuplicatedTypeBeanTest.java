package spring.springcorebasic.findbeans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.springcorebasic.member.MemberRepository;
import spring.springcorebasic.member.MemoryMemberRepository;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextDuplicatedTypeBeanTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Configuration
    static class TestConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

    @Test
    @DisplayName("같은 타입의 Bean이 둘 이상 존재할 때 타입으로 조회")
        // NoUniqueBeanDefinitionException이 발생하는지 검증
    void findBeanByDuplicatedType() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("같은 타입의 Bean이 둘 이상 존재할 때 Bean 이름으로 조회")
        // 이름으로 조회하면 중복된 타입의 Bean이 있더라도 특정해서 찾을 수 있으므로 예외가 발생하지 않음
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입의 Bean 모두 조회")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

        /*
        실제로 테스트 코드 작성할 때는 콘솔 출력은 안해야 맞음. 출력물을 보는건 눈으로 보고 결과를 판단한다는 것인데,
        바람직한 테스트 코드는 테스트 결과를 시스템이 결정하게 해야하기 때. 현재는 학습용이라서 남기는 것.
         */
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }
}
