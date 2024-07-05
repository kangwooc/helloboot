package tobyspring.helloboot;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class MySpringApplication {
    public static void run(Class<?> applicationClass, String... args) {
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
        applicationContext.register(applicationClass);
        applicationContext.refresh();
    }
}
