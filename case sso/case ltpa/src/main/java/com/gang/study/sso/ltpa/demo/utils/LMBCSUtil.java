package com.gang.study.sso.ltpa.demo.utils;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Locale;

public final class LMBCSUtil {
    private static final String ULMBCS_GRP_L1 = "0x01";
    private static final String ULMBCS_GRP_GR = "0x02";
    private static final String ULMBCS_GRP_HE = "0x03";
    private static final String ULMBCS_GRP_AR = "0x04";
    private static final String ULMBCS_GRP_RU = "0x05";
    private static final String ULMBCS_GRP_L2 = "0x06";
    private static final String ULMBCS_GRP_TR = "0x08";
    private static final String ULMBCS_GRP_TH = "0x0B";
    private static final String ULMBCS_GRP_JA = "0x10";
    private static final String ULMBCS_GRP_KO = "0x11";
    private static final String ULMBCS_GRP_TW = "0x12";
    private static final String ULMBCS_GRP_CN = "0x13";
    private static final String ULMBCS_GRP_UNICODE = "0x14";
    private static Hashtable groupMap = new Hashtable();

    static {
        groupMap.put(new Locale("ar", ""), "0x04");
        groupMap.put(new Locale("be", ""), "0x05");
        groupMap.put(new Locale("bg", ""), "0x06");
        groupMap.put(new Locale("cs", ""), "0x06");
        groupMap.put(new Locale("el", ""), "0x02");
        groupMap.put(new Locale("he", ""), "0x03");
        groupMap.put(new Locale("hu", ""), "0x06");
        groupMap.put(new Locale("iw", ""), "0x03");
        groupMap.put(new Locale("ja", ""), "0x10");
        groupMap.put(new Locale("ko", ""), "0x11");
        groupMap.put(new Locale("mk", ""), "0x05");
        groupMap.put(new Locale("pl", ""), "0x06");
        groupMap.put(new Locale("ro", ""), "0x06");
        groupMap.put(new Locale("ru", ""), "0x05");
        groupMap.put(new Locale("sh", ""), "0x06");
        groupMap.put(new Locale("sk", ""), "0x06");
        groupMap.put(new Locale("sl", ""), "0x06");
        groupMap.put(new Locale("sq", ""), "0x06");
        groupMap.put(new Locale("sr", ""), "0x05");
        groupMap.put(new Locale("th", ""), "0x0B");
        groupMap.put(new Locale("tr", ""), "0x08");
        groupMap.put(new Locale("uk", ""), "0x05");
        groupMap.put(new Locale("zh", "TW"), "0x12");
        groupMap.put(new Locale("zh", "HK"), "0x12");
        groupMap.put(new Locale("zh", ""), "0x13");
        groupMap.put(new Locale("zh", "CN"), "0x13");
    }

    public static byte getLMBCGroupId(Locale locale) {
        byte groupId = Byte.decode("0x01").byteValue();
        if (groupMap.get(locale) == null) {
            String language = locale.getLanguage();
            Locale l = new Locale(language, "");
            if (groupMap.get(l) != null)
                groupId = Byte.decode(groupMap.get(l).toString()).byteValue();
        } else {
            groupId = Byte.decode(groupMap.get(locale).toString()).byteValue();
        }
        return groupId;
    }

    public static byte getDefaultGroupId() {
        return getLMBCGroupId(Locale.getDefault());
    }

    public static byte[] getLMBCSLocalGroupBytes(String input) {
        byte[] groupId = {getDefaultGroupId()};
        byte[] bytes = input.getBytes();
        byte[] result = (byte[]) null;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Character ch = new Character(c);
            String s = ch.toString();
            if (s.getBytes()[0] < 0) {
                result = concatenate(result, groupId);
            }
            result = concatenate(result, s.getBytes());
        }
        return result;
    }

    public static byte[] getLMBCSUnicodeGroupBytes(String input) {
        byte[] unicodeGroupId = {Byte.decode("0x14")
                .byteValue()};
        String s = null;
        byte[] unicodeBytes = (byte[]) null;
        byte[] LMBCSBytes = (byte[]) null;

        for (int i = 0; i < input.length(); i++) {
            s = new Character(input.charAt(i)).toString();
            try {
                unicodeBytes = s.getBytes("Unicode");
                byte highByte = unicodeBytes[2];
                byte lowByte = unicodeBytes[3];

                if (highByte == 0) {
                    LMBCSBytes = concatenate(LMBCSBytes, new byte[]{lowByte});
                } else {
                    LMBCSBytes = concatenate(LMBCSBytes, unicodeGroupId);
                    LMBCSBytes = concatenate(LMBCSBytes,
                            new byte[]{highByte});
                    LMBCSBytes = concatenate(LMBCSBytes, new byte[]{lowByte});
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return LMBCSBytes;
    }

    public static byte[] concatenate(byte[] a, byte[] b) {
        if (a == null) {
            return b;
        }
        byte[] bytes = new byte[a.length + b.length];

        System.arraycopy(a, 0, bytes, 0, a.length);
        System.arraycopy(b, 0, bytes, a.length, b.length);
        return bytes;
    }
}
