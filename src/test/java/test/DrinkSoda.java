package test;
/**
 * @author Administrator
 * 喝汽水问题，开始有n瓶汽水，每7个瓶子可以换1瓶汽水，一共可以喝多少汽水
 */
public class DrinkSoda {
	
	public int drink(int num) {
		int sum = 0;
		int temp = num;
		do {
			sum = sum + num/7;
			num = num/7 + num%7;
		} while (num >= 7);
		return sum + temp;
	}
	
	public static void main(String[] args) {
		System.out.println(new DrinkSoda().drink(20));
	}
}
