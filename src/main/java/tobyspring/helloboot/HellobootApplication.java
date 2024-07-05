package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan // 하위 패키지를 전부 스캔
public class HellobootApplication {
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
        // Jetty나 undertow 같은 서블릿 컨테이너 사용 가능
        //        new JettyServletWebServerFactory()
        //        new UndertowServletWebServer()
        // GenericWebApplicationContext은 configure를 읽을수 없음
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                // 서블릿 컨테이너 등록
                ServletWebServerFactory factory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                // 지울 경우에도 동작을 함
//                dispatcherServlet.setApplicationContext(this);
                // 스프링 컨테이너로의 이전
                WebServer webServer = factory.getWebServer(servletContext -> {
                    // 서블릿 등록
                    // 익명 클래스 등록
                    // DispatcherServlet 등록
                    // 이 상태에서는 DispatcherServlet이 동작하지 않는다
                    // 매핑정보가 들어있지 않기 때문!
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*");
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
