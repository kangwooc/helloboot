package tobyspring.helloboot;

import java.util.Objects;

public class HelloController {
    // 멤버 변수
    // 한번 할당 후 재정의는 없다
    private final HelloService simpleHelloService;

    public HelloController(HelloService simpleHelloService) {
        this.simpleHelloService = simpleHelloService;
    }

    public String hello(String name) {
        // null 체크
        return simpleHelloService.sayHello(Objects.requireNonNull(name));
    }
}
