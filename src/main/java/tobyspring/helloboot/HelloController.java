package tobyspring.helloboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
@RequestMapping("/hello")
public class HelloController {
    // 멤버 변수
    // 한번 할당 후 재정의는 없다
    private final HelloService simpleHelloService;

    public HelloController(HelloService simpleHelloService) {
        this.simpleHelloService = simpleHelloService;
    }

    @GetMapping
    // String으로 반환할 경우 View로 인식하기 때문에 ResponseBody 추가
    @ResponseBody
    public String hello(String name) {
        // null 체크
        return simpleHelloService.sayHello(Objects.requireNonNull(name));
    }
}
