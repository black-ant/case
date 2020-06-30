package com.gang.study.sso.ltpa.demo.utils;

import com.gang.study.sso.ltpa.demo.type.Algorithm;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * @Classname CryptUtils
 * @Description TODO
 * @Date 2020/6/30 16:53
 * @Created by zengzg
 */
public class CryptUtils {

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String HmacMD5 = "HmacMD5";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String DES = "DES";
    public static final String DES3 = "3DES";
    public static final String AES = "AES";

    /**
     * 编码格式；默认使用uft-8
     */
    public static String charset = "utf-8";
    /**
     * DES
     */
    public static int keysizeDES = 0;
    /**
     * AES
     */
    public static int keysizeAES = 128;

    public static CryptUtils me;

    private CryptUtils() {
        //单例
    }

    //双重锁
    public static CryptUtils getInstance() {
        if (me == null) {
            synchronized (CryptUtils.class) {
                if (me == null) {
                    me = new CryptUtils();
                }
            }
        }
        return me;
    }

    /**
     * 使用MessageDigest进行单向加密（无密码）
     *
     * @param res       被加密的文本
     * @param algorithm 加密算法名称
     * @return
     */
    private static String messageDigest(String res, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
            return base64(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用KeyGenerator进行单向/双向加密（可设密码）
     *
     * @param res       被加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密使用的秘钥
     * @return
     */
    private static String keyGeneratorMac(String res, String algorithm, String key) {
        try {
            SecretKey sk = null;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            return base64(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     *
     * @param res       加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密的秘钥
     * @param keysize
     * @param isEncode
     * @return
     */
    private static String keyGeneratorES(String res, String algorithm, String key, int keysize, boolean isEncode) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                kg.init(new SecureRandom(keyBytes));
            } else if (key == null) {
                kg.init(keysize);
            } else {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                kg.init(keysize, new SecureRandom(keyBytes));
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String base64(byte[] res) {
        return Base64Utils.encodeToString(res);
    }

    /**
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public static String MD5(String res) {
        return messageDigest(res, MD5);
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String MD5(String res, String key) {
        return keyGeneratorMac(res, HmacMD5, key);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public static String SHA1(String res) {
        return messageDigest(res, SHA1);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String SHA1(String res, String key) {
        return keyGeneratorMac(res, HmacSHA1, key);
    }

    /**
     * 使用DES加密算法进行加密（可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String DESencode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public static String DESdecode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, false);
    }


    /**
     * 使用DES加密算法进行加密（可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String DES3encode(String res, String key) {
        return keyGeneratorES(res, DES3, key, keysizeDES, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public static String DES3decode(String res, String key) {
        return keyGeneratorES(res, DES3, key, keysizeDES, false);
    }

    /**
     * 使用AES加密算法经行加密（可逆）
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return
     */
    public static String AESencode(String res, String key) {
        return keyGeneratorES(res, AES, key, keysizeAES, true);
    }

    /**
     * 对使用AES加密算法的密文进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public static String AESdecode(String res, String key) {
        return keyGeneratorES(res, AES, key, keysizeAES, false);
    }

    /**
     * 使用异或进行加密
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return
     */
    public static String XORencode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     * 使用异或进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public static String XORdecode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     * 直接使用异或（第一调用加密，第二次调用解密）
     *
     * @param res 密文
     * @param key 秘钥
     * @return
     */
    public static int XOR(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     * 使用Base64进行加密
     *
     * @param res 密文
     * @return
     */
    public static String Base64Encode(String res) {
        return Base64Utils.encodeToString(res.getBytes());
    }

    /**
     * 使用Base64进行解密
     *
     * @param res
     * @return
     */
    public static String Base64Decode(String res) {
        return new String(Base64Utils.decodeFromString(res));
    }

    /**
     * 3DES 对密钥进行解密操作
     *
     * @param key
     * @param keyPassword
     * @return
     * @throws Exception
     */
    public static byte[] desDecode(String key, String keyPassword) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(keyPassword.getBytes());
        byte[] hash3DES = new byte[24];
        System.arraycopy(md.digest(), 0, hash3DES, 0, 20);
        Arrays.fill(hash3DES, 20, 24, (byte) 0);
        final Cipher cipher = Cipher.getInstance(Algorithm.DES.getKeyWord());
        final KeySpec keySpec = new DESedeKeySpec(hash3DES);
        final Key secretKey = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] secret = cipher.doFinal(Base64Utils.decodeFromString(key));
        return secret;
    }

    /**
     * DES 加密
     *
     * @param key
     * @param keyPassword
     * @return
     * @throws Exception
     */
    public static byte[] desEncode(String key, String keyPassword) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(keyPassword.getBytes());
        byte[] hash3DES = new byte[24];
        System.arraycopy(md.digest(), 0, hash3DES, 0, 20);
        Arrays.fill(hash3DES, 20, 24, (byte) 0);
        final Cipher cipher = Cipher.getInstance(Algorithm.DES.getKeyWord());
        final KeySpec keySpec = new DESedeKeySpec(hash3DES);
        final Key secretKey = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] secret = cipher.doFinal(Base64Utils.decodeFromString(key));
        return secret;
    }


    public static byte[] crypt(byte[] target, byte[] key, Algorithm algorithm, int mode) throws Exception {
        SecretKey sKey = null;

        if (algorithm.equals(Algorithm.AES)) {
            sKey = new SecretKeySpec(key, 0, 16, "AES");
        } else {
            DESedeKeySpec kSpec = new DESedeKeySpec(key);
            SecretKeyFactory kFact = SecretKeyFactory.getInstance("DESede");
            sKey = kFact.generateSecret(kSpec);
        }
        Cipher cipher = Cipher.getInstance(algorithm.getCode());

        if (algorithm.name().indexOf("ECB") == -1) {
            if (algorithm.name().indexOf("AES") != -1) {
                IvParameterSpec ivs16 = generateIvParameterSpec(key, 16);
                cipher.init(mode, sKey, ivs16);
            } else {
                cipher.init(mode, sKey);
            }
        } else {
            cipher.init(mode, sKey);
        }
        return cipher.doFinal(target);
    }

    private static IvParameterSpec generateIvParameterSpec(byte key[], int size) {
        byte[] row = new byte[size];
        for (int i = 0; i < size; i++) {
            row[i] = key[i];
        }
        return new IvParameterSpec(row);
    }
}
