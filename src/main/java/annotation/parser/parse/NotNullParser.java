package annotation.parser.parse;

import java.lang.reflect.Field;

import annotation.definition.NotNull;
import annotation.parser.Parser;

public class NotNullParser implements Parser{

	@Override
	public void parse(Object obj, Field field) {
		try {
			String stringType = "";
			field.setAccessible(true);
			NotNull notNull = field.getAnnotation(NotNull.class);
			
			//获取对象上标注的属性的类型
			if(field.getType().isInstance(stringType)) {
				//如果属性的类型是stirng
				//获取对象上标注的属性的值
				//如果属性值为空，那么
				//获取注解上标注的值，并设置属性值为注解上标注的值
				if(field.get(obj) != null && !"".equalsIgnoreCase(field.get(obj).toString())) {
					System.out.println("值非空，验证通过");
				}
				field.set(obj, "new_value");
			}
			String type = field.getType().toString();
			System.out.println("type : " + type);
			field.get(obj);
			System.out.println(notNull.value());
			System.out.println("*******************");
			
			System.out.println(field.get(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
