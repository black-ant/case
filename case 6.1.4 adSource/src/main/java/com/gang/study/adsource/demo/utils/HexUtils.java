package com.gang.study.adsource.demo.utils;

/**
 * @Classname HexUtils
 * @Description TODO
 * @Date 2020/2/20 14:24
 * @Created by zengzg
 */
public class HexUtils {

    /**
     * Gets hex string corresponding to the given byte array from "<tt>from</tt>" position to "<tt>to's</tt>"
     *
     * @param bytes bytes.
     * @param from  from position.
     * @param to    to position.
     * @return hex string.
     */
    public static String get(byte[] bytes, int from, int to) {
        final StringBuilder bld = new StringBuilder();
        for (int i = from; i < to; i++) {
            bld.append(HexUtils.get(bytes[i]));
        }
        return bld.toString();
    }

    /**
     * Gets hex string corresponding to the given bytes.
     *
     * @param bytes bytes.
     * @return hex string.
     */
    public static String get(byte... bytes) {
        final StringBuilder bld = new StringBuilder();
        for (byte b : bytes) {
            bld.append(HexUtils.get(b));
        }
        return bld.toString();
    }

    /**
     * Gets escaped hex string corresponding to the given bytes.
     *
     * @param bytes bytes.
     * @return escaped hex string
     */
    public static String getEscaped(byte... bytes) {
        final StringBuilder bld = new StringBuilder();
        for (byte b : bytes) {
            bld.append("\\").append(HexUtils.get(b));
        }
        return bld.toString();
    }

    /**
     * Gets hex string corresponding to the given byte.
     *
     * @param b byte.
     * @return hex string.
     */
    public static String get(byte b) {
        return String.format("%02X", b);
    }

    /**
     * Reverses bytes.
     *
     * @param bytes bytes.
     * @return reversed byte array.
     */
    public static byte[] reverse(byte... bytes) {
        byte[] res = new byte[bytes.length];
        int j = 0;
        for (int i = bytes.length - 1; i >= 0; i--) {
            res[j] = bytes[i];
            j++;
        }
        return res;
    }
}
