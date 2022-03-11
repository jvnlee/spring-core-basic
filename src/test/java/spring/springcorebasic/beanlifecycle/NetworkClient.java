package spring.springcorebasic.beanlifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/*
초기화 인터페이스(InitializingBean)와 소멸 인터페이스(DisposableBean)을 사용해서 Bean의 life cycle 단계에 맞춰 필요한 기능을 실행할 수 있게함
스프링 초창기의 방식이라 지금은 잘 사용하지 않음
*/
public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String msg) {
        System.out.println("call: " + url + " / message: " + msg);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("disconnect: " + url);
    }

    // 의존 관계 주입이 끝난 후 호출
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet() 호출");
        connect();
        call("초기화 연결 메시지");
    }

    // 컨테이너가 종료될 때, 해당 Bean이 소멸하기 직전에 호출
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy() 호출");
        disconnect();
    }
}
