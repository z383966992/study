package cglib;

import java.util.Arrays;
import java.util.List;

public class DBQuery {

	public DBQuery() {
	}
	
	public DBQuery(Integer i) {
		System.out.println("DBQuery Constructor");
	}
	
	public String getElement(String id) {
		return id + "_CGLib";
	}
	
	public List<String> getAllElements() {
		return Arrays.asList("Hello_CGLib1", "Hello_CGLib2");
	}
	
	public String methodForNoop() {  
        return "Hello_Noop";  
    }  
  
    public String methodForFixedValue(String param) {  
        return "Hello_" + param;  
    }
  
    public final String sayHello() {
        return "Hello Everyone！";
    }
}
