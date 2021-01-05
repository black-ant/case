//package com.gang.protocal.demo;
//
//import com.eatthepath.otp.HmacOneTimePasswordGenerator;
//import org.junit.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.stream.Stream;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
///**
// * @Classname HmacOneTimePasswordGeneratorTest
// * @Description TODO
// * @Date 2020/12/4 11:03
// * @Created by zengzg
// */
//public class HmacOneTimePasswordGeneratorTest {
//
//    private static final Key HOTP_KEY =
//            new SecretKeySpec("12345678901234567890".getBytes(StandardCharsets.US_ASCII), "RAW");
//
//    @Test
//    void testHmacOneTimePasswordGeneratorWithShortPasswordLength() {
//        assertThrows(IllegalArgumentException.class, () -> new HmacOneTimePasswordGenerator(5));
//    }
//
//    @Test
//    void testHmacOneTimePasswordGeneratorWithLongPasswordLength() {
//        assertThrows(IllegalArgumentException.class, () -> new HmacOneTimePasswordGenerator(9));
//    }
//
//    @Test
//    void testHmacOneTimePasswordGeneratorWithBogusAlgorithm() {
//        assertThrows(NoSuchAlgorithmException.class, () ->
//                new HmacOneTimePasswordGenerator(6, "Definitely not a real algorithm"));
//    }
//
//    @Test
//    void testGetPasswordLength() throws NoSuchAlgorithmException {
//        final int passwordLength = 7;
//        assertEquals(passwordLength, new HmacOneTimePasswordGenerator(passwordLength).getPasswordLength());
//    }
//
//    @Test
//    void testGetAlgorithm() throws NoSuchAlgorithmException {
//        final String algorithm = "HmacSHA256";
//        assertEquals(algorithm, new HmacOneTimePasswordGenerator(6, algorithm).getAlgorithm());
//    }
//
//    /**
//     * Tests generation of one-time passwords using the test vectors from
//     * <a href="https://tools.ietf.org/html/rfc4226#appendix-D">RFC&nbsp;4226, Appendix D</a>.
//     */
//    @ParameterizedTest
//    @MethodSource("hotpTestVectorSource")
//    void testGenerateOneTimePassword(final int counter, final int expectedOneTimePassword) throws Exception {
//        assertEquals(expectedOneTimePassword, this.getDefaultGenerator().generateOneTimePassword(HOTP_KEY, counter));
//    }
//
//    static Stream<Arguments> hotpTestVectorSource() {
//        return Stream.of(
//                arguments(0, 755224),
//                arguments(1, 287082),
//                arguments(2, 359152),
//                arguments(3, 969429),
//                arguments(4, 338314),
//                arguments(5, 254676),
//                arguments(6, 287922),
//                arguments(7, 162583),
//                arguments(8, 399871),
//                arguments(9, 520489)
//        );
//    }
//
//    protected HmacOneTimePasswordGenerator getDefaultGenerator() throws NoSuchAlgorithmException {
//        return new HmacOneTimePasswordGenerator();
//    }
//}
