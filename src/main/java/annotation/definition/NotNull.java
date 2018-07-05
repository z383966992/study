package annotation.definition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证值不为空的注解
 * @author liangliangzhou3
 *
 */
@Documented
//局部变量
@Target(ElementType.FIELD)
//运行期执行
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
	//设置默认值
	String value() default "";
}
