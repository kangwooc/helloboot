package tobyspring.helloboot;

@MySpringBootAnnotation
public class HellobootApplication {
    public static void main(String[] args) {
        MySpringApplication.run(HellobootApplication.class, args);
    }
}
