package tobyspring.helloboot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HelloControllerTest {
    @Test
    void helloController() {
        // 수동 DI
        HelloController helloController = new HelloController(name -> name);

        String ret = helloController.hello("test");

        assertThat(ret).isEqualTo("test");
    }

    @Test
    void failHelloController() {
        HelloController helloController = new HelloController(name -> name);

        assertThatThrownBy(() -> {
            String ret = helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            String ret = helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}