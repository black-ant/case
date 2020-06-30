package com.gang.study.sso.ltpa.demo.utils;

import java.math.BigInteger;

/**
 * @Classname RSAUtils
 * @Description TODO
 * @Date 2020/6/30 16:46
 * @Created by zengzg
 */
public class RSAUtils {

    /**
     * RSA Builder
     *
     * @param rawKey
     * @param data
     * @return
     */
    public static byte[] rsa(byte[][] rawKey, byte[] data) {
        byte encoded[] = null;
        int rawKeyLength = rawKey.length;
        BigInteger abiginteger[] = new BigInteger[rawKeyLength];
        int l;
        int k1;
        if (rawKeyLength == 8) {
            l = 3;
            k1 = rawKeyLength;
            abiginteger[0] = new BigInteger(rawKey[0]);
        } else {
            l = 0;
            k1 = 2;
        } // else

        do {
            abiginteger[l] = new BigInteger(rawKey[l]);
        } while (++l < k1);

        int l2 = l != 2
                ? abiginteger[3].bitLength() + abiginteger[4].bitLength()
                : abiginteger[0].bitLength();
        int j2 = (l2 + 7) / 8;
        BigInteger biginteger;
        byte abyte4[];
        if ((abyte4 = padISO9796(data, l2)) == null) {
            return null;
        } // if
        biginteger = new BigInteger(1, abyte4);

        if (rawKeyLength > 3) {
            BigInteger biginteger1 = biginteger.remainder(abiginteger[3]).modPow(abiginteger[5], abiginteger[3]);
            BigInteger biginteger2 = biginteger.remainder(abiginteger[4]).modPow(abiginteger[6], abiginteger[4]);
            biginteger =
                    biginteger1.add(abiginteger[3]).subtract(biginteger2).multiply(abiginteger[7]).remainder(abiginteger[3]).multiply(abiginteger[4]).add(biginteger2);
        } else {
            biginteger = biginteger.modPow(abiginteger[1], abiginteger[0]);
        } // else

        if (biginteger.multiply(BigInteger.valueOf(2L)).compareTo(abiginteger[0]) == 1) {
            biginteger = abiginteger[0].subtract(biginteger);
        } // if

        byte abyte5[] = biginteger.toByteArray();
        rawKeyLength = 0;
        l = abyte5.length;
        k1 = j2;
        if ((l -= k1) == 0) {
            return abyte5;
        } // if
        if (l < 0) {
            rawKeyLength = -l;
            l = 0;
        } // if
        encoded = new byte[k1];
        System.arraycopy(abyte5, l, encoded, rawKeyLength, k1 - rawKeyLength);
        return encoded;
    }


    private static byte[] padISO9796(byte data[], int k) {
        byte padded[] = null;
        k--;
        if (data.length * 16 > k + 3) {
            return null;
        } // if
        padded = new byte[(k + 7) / 8];
        for (int l = 0; l < padded.length / 2; l++) {
            padded[padded.length - 1 - 2 * l] = data[data.length - 1 - l % data.length];
        } // for
        if ((padded.length & 1) != 0) {
            padded[0] = data[data.length - 1 - (padded.length / 2) % data.length];
        } // if
        long l1 = 0x1ca76bd0f249853eL;
        for (int i1 = 0; i1 < padded.length / 2; i1++) {
            int k1 = padded.length - 1 - 2 * i1;
            padded[k1 - 1] =
                    (byte) (int) ((l1 >> (padded[k1] >>> 2 & 0x3c) & 15L) << 4 | l1 >> ((padded[k1] & 0xf) << 2) & 15L);
        } // for
        padded[padded.length - 2 * data.length] ^= 1;
        int j1 = k % 8;
        padded[0] &= (byte) ((1 << j1) - 1);
        padded[0] |= 1 << ((j1 - 1) + 8) % 8;
        padded[padded.length - 1] = (byte) (padded[padded.length - 1] << 4 | 6);
        return padded;
    }
}
