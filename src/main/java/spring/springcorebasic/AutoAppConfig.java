package spring.springcorebasic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
/*
@ComponentScan은 @Component 애너테이션이 붙은 클래스들을 찾아 Bean으로 등록해줌
excludeFilters는 기존 설정파일을 그대로 두고 영향을 받지 않기 위해 지정한 것이므로 보통 상황에서는 사용하지 않음
*/
public class AutoAppConfig {
    // 컴포넌트 스캔 방식의 configuration 클래스는 Bean 생성 메서드가 필요 없으므로 비어있음
}
