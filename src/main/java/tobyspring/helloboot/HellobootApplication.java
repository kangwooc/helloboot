package tobyspring.helloboot;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;

public class HellobootApplication {

    public static void main(String[] args) {
        // 서블릿 컨테이너 등록
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        // Jetty나 undertow 같은 서블릿 컨테이너 사용 가능
        //        new JettyServletWebServerFactory()
        //        new UndertowServletWebServer()


//        WebServer webServer = factory.getWebServer(servletContext -> {
//            // 서블릿 등록
//            // 익명 클래스 등록
//            servletContext.addServlet("hello", new HttpServlet() {
//                @Override
//                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                    resp.setStatus(HttpStatus.OK.value());
//                    resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
//                    PrintWriter writer = resp.getWriter();
//                    writer.println("hello servlet!!");
//                }
//            }).addMapping("/hello");
//        });

        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();

        WebServer webServer = factory.getWebServer(servletContext -> {
            // 서블릿 등록
            // 익명 클래스 등록
            servletContext.addServlet("frontController", new HttpServlet() {
                // HelloController를 사용
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // 인증, 보안, 다국어 공통기능
                    // 매핑과 바인딩
                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");
                        // 기존 서블릿
                        HelloController bean = applicationContext.getBean(HelloController.class);
                        String response = bean.hello(name);
                        // 기본적으로 200번대 설정
//                        resp.setStatus(HttpStatus.OK.value());
                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        PrintWriter writer = resp.getWriter();
                        writer.println(response);
                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            }).addMapping("/*");
        });
        webServer.start();

        // 서블릿 등록

    }
}
