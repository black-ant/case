package com.gang.study.adsource.demo.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @Classname GUID
 * @Description TODO
 * @Date 2020/2/20 14:23
 * @Created by zengzg
 */
public class GUIDUtils {

    /**
     * Gets GUID as string.
     *
     * @param GUID GUID.
     * @return GUID as string.
     */
    public static String getGuidAsString(byte[] GUID) {
        final StringBuilder res = new StringBuilder();

        res.append(AddLeadingZero((int) GUID[3] & 0xFF));
        res.append(AddLeadingZero((int) GUID[2] & 0xFF));
        res.append(AddLeadingZero((int) GUID[1] & 0xFF));
        res.append(AddLeadingZero((int) GUID[0] & 0xFF));
        res.append("-");
        res.append(AddLeadingZero((int) GUID[5] & 0xFF));
        res.append(AddLeadingZero((int) GUID[4] & 0xFF));
        res.append("-");
        res.append(AddLeadingZero((int) GUID[7] & 0xFF));
        res.append(AddLeadingZero((int) GUID[6] & 0xFF));
        res.append("-");
        res.append(AddLeadingZero((int) GUID[8] & 0xFF));
        res.append(AddLeadingZero((int) GUID[9] & 0xFF));
        res.append("-");
        res.append(AddLeadingZero((int) GUID[10] & 0xFF));
        res.append(AddLeadingZero((int) GUID[11] & 0xFF));
        res.append(AddLeadingZero((int) GUID[12] & 0xFF));
        res.append(AddLeadingZero((int) GUID[13] & 0xFF));
        res.append(AddLeadingZero((int) GUID[14] & 0xFF));
        res.append(AddLeadingZero((int) GUID[15] & 0xFF));

        return res.toString();

    }

    /**
     * Gets GUID as byte array.
     *
     * @param GUID GUID.
     * @return GUID as byte array.
     */
    public static byte[] getGuidAsByteArray(final String GUID) {
        final UUID uuid = UUID.fromString(GUID);

        final ByteBuffer buff = ByteBuffer.wrap(new byte[16]);
        buff.putLong(uuid.getMostSignificantBits());
        buff.putLong(uuid.getLeastSignificantBits());

        byte[] res = new byte[]{
                buff.get(3),
                buff.get(2),
                buff.get(1),
                buff.get(0),
                buff.get(5),
                buff.get(4),
                buff.get(7),
                buff.get(6),
                buff.get(8),
                buff.get(9),
                buff.get(10),
                buff.get(11),
                buff.get(12),
                buff.get(13),
                buff.get(14),
                buff.get(15),};

        return res;
    }

    private static String AddLeadingZero(int k) {
        return (k <= 0xF) ? "0" + Integer.toHexString(k) : Integer.toHexString(k);
    }
}
