package annotation.definition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解用在connection上用来获得sql链接
 * @author liangliangzhou3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlConnector {

	String driver() default "";
	
	String url() default "";
	
	String user() default "";
	
	String passwd() default "";
	
}
