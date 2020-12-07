package com.gang.protocal.demo.service;

import com.eatthepath.otp.HmacOneTimePasswordGenerator;
import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;

/**
 * @Classname GoogleAuthenticationHOTP
 * @Description TODO
 * @Date 2020/12/4 10:50
 * @Created by zengzg
 */
public class GoogleAuthenticationHOTP {

    private static final Key HOTP_KEY =
            new SecretKeySpec("12345678901234567890".getBytes(StandardCharsets.US_ASCII), "RAW");

    public static void main(String[] args) throws Exception {

        final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
        final Instant now = Instant.now();
        final Instant later = now.plus(totp.getTimeStep());

        final Key key;
        {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());

            // Key length should match the length of the HMAC output (160 bits for SHA-1, 256 bits
            // for SHA-256, and 512 bits for SHA-512).
            keyGenerator.init(160);

            key = keyGenerator.generateKey();
        }

//        System.out.format("Current password: %06d\n", totp.generateOneTimePassword(key, now));
//        System.out.format("Future password:  %06d\n", totp.generateOneTimePassword(key, later));

        String keyHOTP = "";
        final HmacOneTimePasswordGenerator hotp = new HmacOneTimePasswordGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.format("HOTP Current password: %06d\n", hotp.generateOneTimePassword(HOTP_KEY, i));
        }
    }
}
