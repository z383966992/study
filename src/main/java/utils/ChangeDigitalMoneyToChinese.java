package utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

public class ChangeDigitalMoneyToChinese {

	public String toChineseCharacter(String moneyIn) {
		String result = "零";
		if (StringUtils.isBlank(moneyIn))
			return result;
		try {
			double money = Double.valueOf(moneyIn);
			double temp = 0;
			long l = Math.abs((long) money);
			BigDecimal bil = new BigDecimal(l);
			if (bil.toString().length() > 14) {
				// "数字太大，计算精度不够!"
				return result;
			}
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			int i = 0;
			String sign = "", tempStr = "", temp1 = "";
			String[] arr = null;
			sign = money < 0 ? "负" : "";
			temp = Math.abs(money);
			if (l == temp) {
				result = doForEach(new BigDecimal(temp).multiply(new BigDecimal(100)).toString(), sign);
			} else {
				nf.setMaximumFractionDigits(2);
				temp1 = nf.format(temp);
				arr = temp1.split(",");
				while (i < arr.length) {
					tempStr += arr[i];
					i++;
				}
				BigDecimal b = new BigDecimal(tempStr);
				b = b.multiply(new BigDecimal(100));
				tempStr = b.toString();
				if (tempStr.indexOf(".") == tempStr.length() - 3) {
					result = doForEach(tempStr.substring(0, tempStr.length() - 3), sign);
				} else {
					result = doForEach(tempStr.substring(0, tempStr.length() - 3) + "0", sign);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	String doForEach(String result, String sign) {
		String flag = "", b_string = "";
		String[] arr = { "分", "角", "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾" };
		String[] arr1 = { "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		boolean zero = true;
		int len = 0, i = 0, z_count = 0;
		if (result == null) {
			len = 0;
		} else {
			len = result.length();
		}
		while (i < len) {
			flag = result.substring(i, i + 1);
			i++;
			if (flag.equals("0")) {
				if (len - i == 10 || len - i == 6 || len - i == 2 || len == i) {
					if (zero) {
						b_string = b_string.substring(0, (b_string.length()) - 1);
						zero = false;
					}
					if (len - i == 10) {
						b_string = b_string + "亿";
					}
					if (len - i == 6) {
						b_string = b_string + "万";
					}
					if (len - i == 2) {
						b_string = b_string + "圆";
					}
					if (len == i) {
						b_string = b_string + "整";
					}
					z_count = 0;
				} else {
					if (z_count == 0) {
						b_string = b_string + "零";
						zero = true;
					}
					z_count = z_count + 1;
				}
			} else {
				b_string = b_string + arr1[Integer.parseInt(flag) - 1] + arr[len - i];
				z_count = 0;
				zero = false;
			}
		}
		b_string = sign + b_string;
		return b_string;
	}
	
	public static void main(String[] args) {
		System.out.println(new ChangeDigitalMoneyToChinese().toChineseCharacter("1000.05"));
	}
}