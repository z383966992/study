package test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Test{
	
	 public static void main(String args[]) throws Exception {
//		 System.out.println(new BigDecimal(50).divide(new BigDecimal(12)).setScale(10, RoundingMode.HALF_DOWN));
		 Map<String, String> map = new HashMap<String, String>();
//		 map.put(key, value);
		 System.out.println(new BigDecimal(50).divide(new BigDecimal(12), 3, RoundingMode.HALF_DOWN));
	 }
}
