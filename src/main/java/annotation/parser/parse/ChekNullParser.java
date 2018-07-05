package annotation.parser.parse;

import java.lang.reflect.Field;

import annotation.definition.CheckNull;
import annotation.parser.Parser;

/**
 * 验证非空
 * 如果是null，报错
 * @author liangliangzhou3
 */
public class ChekNullParser implements Parser{
	@Override
	public void parse(Object obj, Field field) throws Exception{
		String stringType = "";
		char charType;
		field.setAccessible(true);
		if(field.get(obj) == null) {
			throw new Exception(field.getName() + ": is null");
		}
		
		if(field.get(obj) != null && "".equalsIgnoreCase(field.get(obj).toString())) {
			throw new Exception(field.getName() + ": is \"\"");
		} 
		
		System.out.println("验证通过 " + field.get(obj));
	}

}
