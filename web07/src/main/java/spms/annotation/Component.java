package spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // Annotation 유지정책 = RUNTIME -> Annotation 정보를 언제까지 유지할 것인가
public @interface Component {
	String value() default "";
}
