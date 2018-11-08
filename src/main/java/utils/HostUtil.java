package utils;

import java.net.InetAddress;

/**
 * @program: util
 * @description: 获取机器的ip
 * @author: zhouliangliang
 * @create: 2018-10-31 15:56
 **/
public class HostUtil {

    /**
     * 获取本机的ip
     * 异常情况下获取机器名
     * 都异常的情况下返回""
     */
    public static String getIp() {
        InetAddress address = null;
        try {
            address = address.getLocalHost();
            String ip = address.getHostAddress();
            return ip;
        } catch (Exception e) {
            try{
                return address.getHostName();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public static void main(String [] args) {
        System.out.println(HostUtil.getIp());
    }
}
