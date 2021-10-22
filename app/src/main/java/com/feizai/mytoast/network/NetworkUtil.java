package com.feizai.mytoast.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkUtil {
    /**
     * int类型转ip地址
     *
     * @param paramInt
     * @return ip地址
     */
    public static String int2Ip(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }

    public static String[] resolutionIP(String ip) {
        return ip.split("\\.");
    }

    /**
     * 判断是否正常的ip地址
     *
     * @param ip
     * @return
     */
    public static boolean matchIP(String ip) {
        if (ip == null) {
            return false;
        }
        String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * 通过ip类型子网掩码获取子网掩码前缀长度
     *
     * @param ipAddr
     * @return
     */
    public static int getPrefixLength(String ipAddr) {
        return findNumberOf1(ipToInt(ipAddr));
    }

    /**
     * ip类型转换为二进制
     *
     * @param ipAddr
     * @return
     */
    private static int ipToInt(String ipAddr) {
        String[] ipArr = ipAddr.split("\\.");
        return ((Integer.parseInt(ipArr[0]) & 0xFF) << 24)
                + ((Integer.parseInt(ipArr[1]) & 0xFF) << 16)
                + ((Integer.parseInt(ipArr[2]) & 0xFF) << 8)
                + (Integer.parseInt(ipArr[3]) & 0xFF);
    }

    private static int findNumberOf1(int n) {
        int countOf1 = 0;
        int tag = 1;
        while (tag != 0) {
            if ((tag & n) != 0)
                countOf1++;
            tag = tag << 1;
        }
        return countOf1;
    }
}
