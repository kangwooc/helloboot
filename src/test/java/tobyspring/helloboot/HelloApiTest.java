package tobyspring.helloboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloApiTest {
    @Test
    void helloApi() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();

        ResponseEntity<String> res =
                testRestTemplate.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        // status 200
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        // header 중 content-type: text/plain;charset=UTF-8
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
        // body "Hello Spring"
        assertThat(res.getBody()).isEqualTo("Hello Spring");
    }

    @Test
    void failHelloApi() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();

        ResponseEntity<String> res =
                testRestTemplate.getForEntity("http://localhost:8080/hello?name={name}", String.class, "");

        // status 500
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
