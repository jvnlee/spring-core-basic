package spring.springcorebasic.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}

/*
@Qualifier("...")를 사용할 때 이름 부분은 그냥 문자열이라 실수가 발생할 여지가 있음
따라서 이렇게 별도의 어노테이션으로 만든 뒤에 이것을 사용함
*/
