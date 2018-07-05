package annotation.parser.parse;

import java.lang.reflect.Field;
import java.sql.DriverManager;

import annotation.definition.SqlConnector;
import annotation.parser.Parser;

public class SqlConnectionParser implements Parser{

	@Override
	public void parse(Object obj, Field field) throws Exception {
		
		System.out.println(field.getType().getName().toString());
		if (field.getType().isInterface() && field.getType().getName().equals("java.sql.Connection")) {
			field.setAccessible(true);
			SqlConnector sqlConnector = field.getAnnotation(SqlConnector.class);
			String driver = sqlConnector.driver();
			String url = sqlConnector.url();
			String user = sqlConnector.user();
			String passwd = sqlConnector.passwd();
			if( (driver != null && !"".equalsIgnoreCase(driver)) && 
				(url != null && !"".equalsIgnoreCase(url)) &&
				(user != null && !"".equalsIgnoreCase(user)) &&
				(passwd != null)) {
				
				Class.forName(driver);
				field.set(obj, DriverManager.getConnection(url, user, passwd));
			} else {
				throw new Exception("driver :" + driver + " url: " + url + " user: " + user + " passwd: " + passwd);
			}
			return;
		}
		throw new Exception("此注解只能用在java.sql.Connection上，用来获得sql连接");
	}
}
