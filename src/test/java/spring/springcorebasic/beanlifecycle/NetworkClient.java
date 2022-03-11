package spring.springcorebasic.beanlifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

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
    @PostConstruct
    public void init() {
        System.out.println("init() 호출");
        connect();
        call("초기화 연결 메시지");
    }

    // 컨테이너가 종료될 때, 해당 Bean이 소멸하기 직전에 호출
    @PreDestroy
    public void close() {
        System.out.println("close() 호출");
        disconnect();
    }
}
