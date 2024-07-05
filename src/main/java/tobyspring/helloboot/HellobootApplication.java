package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan // 하위 패키지를 전부 스캔
public class HellobootApplication {
    // 이거는 사용해야하는데 어디서 구현을?????
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
    // DispatcherServlet는 ApplicationContextAware를 구현을 하는데
    // ApplicationContextAware는 컨테이너가 관리하는 빈을 주입받을 수 있다
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    public static void main(String[] args) {
        MySpringApplication.run(HellobootApplication.class, args);
    }
}
