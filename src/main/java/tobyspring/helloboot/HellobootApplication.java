package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

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

        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();

        WebServer webServer = factory.getWebServer(servletContext -> {
            // 서블릿 등록
            // 익명 클래스 등록
            // DispatcherServlet 등록
            // 이 상태에서는 DispatcherServlet이 동작하지 않는다
            // 매핑정보가 들어있지 않기 때문!
            servletContext.addServlet("dispatcherServlet",
                    new DispatcherServlet(applicationContext)
            ).addMapping("/*");
        });
        webServer.start();

        // 서블릿 등록

    }
}
