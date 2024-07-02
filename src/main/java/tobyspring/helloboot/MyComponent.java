package tobyspring.helloboot;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

// meta annotation - annotation 위에 annotation을 붙이는 것
@Retention(RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface MyComponent {
}
