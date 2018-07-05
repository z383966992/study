package add;

/**
 * 不使用 + - * /
 * 计算+法运算  
 * @author liangliangzhou3
 *
 */
public class Add {

	public int add(int num1, int num2) {
		int sum;
		int carry;
		do{
			sum = num1 ^ num2;
			carry = (num1 & num2) << 1;
			num1 = sum;
			num2 = carry;
		} while(num2!=0);
		return num1;
	}
	
	public static void main(String[] args) {
		System.out.println(new Add().add(13, 23));
	}
}
