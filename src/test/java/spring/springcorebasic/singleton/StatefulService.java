package spring.springcorebasic.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드

    public void order(String name, int price) {
        // name은 주문자 이름, price는 주문 금액
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 멤버의 값(상태)을 바꾸는 바로 이 지점이 문제!
    }

    public int getPrice() {
        return price;
    }
}
