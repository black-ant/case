/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 samir.araujo@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.gang.study.sso.ltpa.demo.to;

import java.security.PrivateKey;

/**
 * Private Key for LTPA
 */
public class LTPAPrivateKey implements PrivateKey {
	private static final long serialVersionUID = 3706306729746466911L;
	private byte rawKey[][];
	private byte encodedKey[];
	private int eLength = 3;
	private int pLength = 65;
	private int qLength = 65;
	private int privExponentLength;

	/**
	 * Constructor for encodedKey
	 * @param key
	 */
	public LTPAPrivateKey(byte key[] ) {
		this.encodedKey = key;
		this.rawKey = decode( );
	}

	/**
	 * Constructor for rawKey
	 * @param key
	 */
	public LTPAPrivateKey(byte key[][] ) {
		this.rawKey = key;
		this.encodedKey = encode( );
	}

    /**
     * Decode the encoded key into a rawKey
     * @param encodedKey
     * @return
     */
    private byte[][] decode( ) {
        byte decodedRawKey[][] = new byte[8][];

        if(encodedKey.length > eLength + pLength + qLength) {
            byte abyte2[] = new byte[4];
            for(int i = 0; i < 4; i++) {
                abyte2[i] = encodedKey[i];
            } // for
            privExponentLength = toInt(abyte2);
            decodedRawKey[1] = new byte[privExponentLength];
            decodedRawKey[2] = new byte[eLength];
            decodedRawKey[3] = new byte[pLength];
            decodedRawKey[4] = new byte[qLength];
            System.arraycopy(encodedKey, 4, decodedRawKey[1], 0, privExponentLength );
            System.arraycopy(encodedKey, privExponentLength + 4, decodedRawKey[2], 0, eLength);
            System.arraycopy(encodedKey, privExponentLength + eLength + 4, decodedRawKey[3], 0, pLength);
            System.arraycopy(encodedKey, privExponentLength + eLength + pLength + 4, decodedRawKey[4], 0, qLength);
        } else {
            decodedRawKey[2] = new byte[eLength];
            decodedRawKey[3] = new byte[pLength];
            decodedRawKey[4] = new byte[qLength];
            System.arraycopy(encodedKey, 0, decodedRawKey[2], 0, eLength);
            System.arraycopy(encodedKey, eLength, decodedRawKey[3], 0, pLength);
            System.arraycopy(encodedKey, eLength + pLength, decodedRawKey[4], 0, qLength);
        }
        return decodedRawKey;
    }

    /**
     * Encode the rawKey into an array of encoded bytes
     * @return
     */
    private byte[] encode() {
        int number = privExponentLength + eLength + pLength + qLength + 4;
        byte encodedBytes[] = new byte[number];
        byte abyte1[] = toByteArray(privExponentLength);
        System.arraycopy(abyte1, 0, encodedBytes, 0, 4);
        System.arraycopy(rawKey[1], 0, encodedBytes, 4, privExponentLength);
        System.arraycopy(rawKey[2], 0, encodedBytes, privExponentLength + 4, eLength);
        System.arraycopy(rawKey[3], 0, encodedBytes, privExponentLength + eLength + 4, pLength);
        System.arraycopy(rawKey[4], 0, encodedBytes, privExponentLength + pLength + eLength + 4, qLength);
        encodedKey = (byte[])(byte[])encodedBytes.clone();
        return encodedBytes;
    }

    /**
     * Convert a byte array of four bytes into an integer
     * @param byteArray
     * @return
     */
    public static int toInt(byte byteArray[]) {
        int number = byteArray[3] & 0xff;
        number |= byteArray[2] << 8 & 0xff00;
        number |= byteArray[1] << 16 & 0xff0000;
        number |= byteArray[0] << 24 & 0xff000000;
        return number;
    }

    /**
     * Convert an integer into a byte array of four bytes
     * @param number
     * @return
     */
    public static byte[] toByteArray(int number) {
        byte byteArray[] = new byte[4];
        byteArray[0] = (byte)(number >>> 24 & 0xff);
        byteArray[1] = (byte)(number >>> 16 & 0xff);
        byteArray[2] = (byte)(number >>> 8 & 0xff);
        byteArray[3] = (byte)(number >>> 0 & 0xff);
        return byteArray;
    }

	/* (non-Javadoc)
	 * @see java.security.Key#getAlgorithm()
	 */
	public String getAlgorithm() {
		 return "RSA/SHA-1";
	}

	/* (non-Javadoc)
	 * @see java.security.Key#getFormat()
	 */
	public String getFormat() {
		 return "LTPAFormat";
	}

	/* (non-Javadoc)
	 * @see java.security.Key#getEncoded()
	 */
	public byte[] getEncoded() {
		return (byte[])encodedKey.clone();
	}

	/**
	 * @return the rawKey
	 */
	public byte[][] getRawKey() {
		return (byte[][])rawKey.clone();
	}
}
