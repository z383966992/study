package test;

import java.util.TreeMap;
import java.util.Vector;

public class TreeMapTest {
	
	public static void main(String[] args) {
		TreeMap<Long, String> map = new TreeMap<Long, String>();
		
		map.put(1L, "a");
		map.put(2L, "b");
		map.put(3L, "c");
		
		System.out.println(map.firstKey());
		
		System.out.println(Runtime.getRuntime().maxMemory());
		
	}

}
