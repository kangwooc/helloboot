package tobyspring.helloboot;

@MyComponent
// @Component // @Service에 포함됨
public class SimpleHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
