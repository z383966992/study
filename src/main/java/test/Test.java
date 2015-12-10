package test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
public class Test {
	
	//递归
	public int factorial(int num) {
		if (num ==1 || num == 0) {
			return 1;
		} else {
			return num*factorial(num-1);
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(new Test().factorial(5));
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = new Date(1433779200000l);
//		try {
//			System.out.println(format.format(date));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
//		map.putIfAbsent("str", "str");
//		
//		Collections.synchronizedList(new LinkedList<String>());
//		
//		Hashtable<String, String> as = new Hashtable<String, String>();
//		as.put("str", "str");
		
	}
}
//2015-06-07 11:42:31
//2015-07-07 11:42:31
//2015-06-08 00:00:00
//2015-06-09 00:00:00