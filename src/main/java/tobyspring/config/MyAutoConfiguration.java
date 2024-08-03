package tobyspring.config;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// proxyBeanMethods를 false로 만든다는 것??
//
@Configuration(proxyBeanMethods = false)
public @interface MyAutoConfiguration {
}
