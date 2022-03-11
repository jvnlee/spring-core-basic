package spring.springcorebasic.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
    /*
    @Scope 어노테이션에 proxyMode = ScopedProxyMode.TARGET_CLASS 옵션을 넣으면 이 클래스의 객체를 의존성으로 갖는 곳에
    이 클래스를 상속받은 가짜(프록시) 객체를 만들어 주입해줌.

    내부 원리:
    스프링 컨테이너는 CGLIB이라는 바이트 코드 조작 라이브러리를 사용해서 프록시 객체를 생성함
    Controller와 Service의 의존성 주입에는 프록시 객체가 사용되지만, 실제 HTTP 요청이 오게 되면 실제 Bean을 요청하는
    위임 로직이 작동해서 프록시 Bean이 실제 MyLogger Bean을 찾고 자신을 실제 Bean으로 대체한 뒤에 동작이 수행됨.
    */

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean created: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean closed: " + this);
    }
}
