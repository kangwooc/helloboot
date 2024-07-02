package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration // 스프링 컨테이너에게 이 클래스가 설정 정보를 포함하고 있음을 알려줌
// 팩토리 메소드가 있는 것을 알려줌
public class HellobootApplication {
    // bean annotation을 붙이면 클래
    @Bean
    public HelloController helloController(HelloService helloService) {
        return new HelloController(helloService);
    }
    @Bean
    public HelloService helloService() {
        return new SimpleHelloService();
    }

    public static void main(String[] args) {
        // Jetty나 undertow 같은 서블릿 컨테이너 사용 가능
        //        new JettyServletWebServerFactory()
        //        new UndertowServletWebServer()
        // GenericWebApplicationContext은 configure를 읽을수 없음
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                // 서블릿 컨테이너 등록
                TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
                // 스프링 컨테이너로의 이전
                WebServer webServer = factory.getWebServer(servletContext -> {
                    // 서블릿 등록
                    // 익명 클래스 등록
                    // DispatcherServlet 등록
                    // 이 상태에서는 DispatcherServlet이 동작하지 않는다
                    // 매핑정보가 들어있지 않기 때문!
                    servletContext.addServlet("dispatcherServlet",
                            new DispatcherServlet(this)
                    ).addMapping("/*");
                });
                webServer.start();
            }

        };
        // bean object 등록
        applicationContext.register(HellobootApplication.class);
        applicationContext.refresh();

        // 서블릿 등록

    }
}
