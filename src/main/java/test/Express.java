package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Express {

	public static void main(String[] args) {
		 Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

		 Matcher matcher = pattern.matcher("a@aa.com");

		 System.out.println(matcher.matches());
	}
}
