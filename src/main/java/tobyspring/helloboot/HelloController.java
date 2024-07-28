package tobyspring.helloboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

//@MyComponent
// @Component // @Controller에 포함됨
@RestController
public class HelloController {
    // 멤버 변수
    // 한번 할당 후 재정의는 없다
    private final HelloService simpleHelloService;
    // final로 저장은 안되는것이 setter로 주입이 되기 때문에
    private ApplicationContext applicationContext;

    public HelloController(HelloService simpleHelloService) {
        this.simpleHelloService = simpleHelloService;
        this.applicationContext = applicationContext;
    }

    @GetMapping("/hello")
    // String으로 반환할 경우 View로 인식하기 때문에 ResponseBody 추가
    @ResponseBody
    public String hello(String name) {
        // null 체크
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return simpleHelloService.sayHello(name);
    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        // 스프링 컨테이너가 띄워지면 올라옴
//        System.out.println(applicationContext);
//        this.applicationContext = applicationContext;
//    }
}
