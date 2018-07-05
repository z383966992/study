package annotation.parser;

import java.lang.reflect.Field;

import annotation.definition.CheckNull;
import annotation.definition.NotNull;
import annotation.definition.SqlConnector;
import annotation.parser.parse.NotNullParser;
import annotation.parser.parse.SqlConnectionParser;
import annotation.parser.parse.ChekNullParser;

/**
 * 注解解析分发器
 * @author liangliangzhou3
 *
 */
public class ParserDispatcher {

	public String parser(Object obj) {
		
//		if(entityClass.isAnnotationPresent(NotNull.class)) {
//			NotNull notNull = entityClass.getAnnotation(NotNull.class);
//			String value = notNull.value();
//			System.out.println(value);
//		}
		
		try {
			
			Field []fields = obj.getClass().getDeclaredFields();
			for(Field field : fields) {
				System.out.println(field);
				if(field.isAnnotationPresent(NotNull.class)) {
					new NotNullParser().parse(obj, field);
				} else if(field.isAnnotationPresent(CheckNull.class)) {
					new ChekNullParser().parse(obj, field);
				} else if(field.isAnnotationPresent(SqlConnector.class)) {
					new SqlConnectionParser().parse(obj, field);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
