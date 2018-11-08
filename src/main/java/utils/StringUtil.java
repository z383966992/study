package utils;

/**
 * @program: util
 * @description: 一些字符串处理方法
 * @author: zhouliangliang
 * @create: 2018-10-23 21:47
 **/
public class StringUtil {

    /**
     * 去掉字符串末尾的多个相同的字符
     * @param value
     * @param trimedStr
     * @return
     */
    private String trimEnd(String value, char trimedStr) {
        int len = value.length();
        int st = 0;
        while ((st < len) && value.charAt(len - 1) == trimedStr) {
            len--;
        }
        return value.substring(0, len);
    }

    /**
     * 去掉字符串开头的多个相同字符
     * @param value
     * @param trimedStr
     * @return
     */
    private String trimFirst(String value, char trimedStr) {
        int len = value.length();
        int st = 0;
        int index = 0;
        while ((st < len) && value.charAt(st) == trimedStr) {
            st++;
            index++;
        }
        return value.substring(index);
    }

    public void test() {
        String test = "////spring/test//";
//        System.out.println(new StringUtil().trimFirst(trimEnd(test,'/'), '/'));
        System.out.println(trimEnd(test.replaceFirst("^/*", ""), '/').replaceAll("/", "_"));
    }

    public static void main(String args[]) {
//        System.out.println(new StringUtil().trimEnd("aa/////", '/'));
//        System.out.println(new StringUtil().trimFirst("/////a/a//", '/'));

        new StringUtil().test();

    }
}
