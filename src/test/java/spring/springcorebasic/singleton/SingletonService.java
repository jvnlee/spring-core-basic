package spring.springcorebasic.singleton;

public class SingletonService {

    // static 메모리에 객체를 딱 1개만 생성해서 보관
    private static final SingletonService instance = new SingletonService();

    // 생성자를 private으로 막아둠으로써 추가 인스턴스 생성을 원천 봉쇄함
    private SingletonService() {}

    // 1개만 존재하는 인스턴스를 외부에서 필요로 할 때 이 메서드를 사용
    public static SingletonService getInstance() {
        return instance;
        // 항상 이 유일무이한 인스턴스를 반환하게 됨
    }

}
