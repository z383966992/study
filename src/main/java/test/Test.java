package test;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
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
		
		Map<Integer, String> map = new TreeMap<Integer, String>();
		
		map.put(5, "555");
		map.put(2, "222");
		map.put(3, "333");
		map.put(8, "888");
		
//		Iterator<Entry<Integer, String>> entry = map.entrySet().iterator();
//		
//		while(entry.hasNext()) {
//			Entry<Integer, String> node = entry.next();
//			System.out.println(node.getKey() + " " + node.getValue());
//			
//		}
		
		
		Iterator<Integer> iter = map.keySet().iterator();
		while(iter.hasNext()) {
			Integer inte = iter.next();
			if(inte >=3 && inte <=8) {
				System.out.println(inte + "  " + map.get(inte));
			}
		}
		
		
//		HashMap<String, String> map = new HashMap<String, String>();
//		System.out.println(map.put("1123","123"));
//		System.out.println(map.put("1123","1234"));
//		System.out.println(map.put("1123","12345"));
//		ConcurrentHashMap<String, String> cmap = new ConcurrentHashMap<String, String>();
//		cmap.put("", "");
//		System.out.println(new Test().factorial(5));
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
