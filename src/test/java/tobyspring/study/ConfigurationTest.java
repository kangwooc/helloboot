package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    void configuration() {
//        // AnnotationConfigApplicationContext에 등록이 안되어있을시에는 두개는 다름
//        MyConfig myConfig = new MyConfig();
//        Bean1 bean1 = myConfig.bean1();
//        Bean2 bean2 = myConfig.bean2();
//        // 두개가 다름
//        Assertions.assertThat(bean1.common).isSameAs(bean2.common);

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);
        // 두개는 같다!!!
        // Configuration은 proxy object가 bean으로 등록이 됨
        // proxyBeanMethods = false인 경우 다른 빈 오브젝트를 사용하는 것을 막음
        // 굳이 비용이 많이 드는 식으로 안해도 괜찮음
        // 예전에는 default = true로 두는 것을 권장을 했으나, 이 자체로 괜찮으면 false로 두어도 됨
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    void proxyCommonMethod() {
        MyConfigProxy myConfigProxy = new MyConfigProxy();
        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();
        // proxy object를 통해서 제어가 가능해짐!!
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class MyConfigProxy extends MyConfig {
        private Common common;

        @Override
        Common common() {
            if (this.common == null) this.common = super.common();

            return super.common();
        }


    }

    @Configuration()
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            // common이라는 object를 주입해야함
            // factory로
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            // common이라는 object를 주입해야함
            // 두번 실행이 되므로 되지는 않는다
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        public Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {

    }

    // bean1 <-- common
    // bean2 <-- common
    // 동일한 오브젝트
}
