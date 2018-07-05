package annotation.parser;

import java.lang.reflect.Field;

public interface Parser {
	
	public void parse(Object obj, Field field) throws Exception;

}
