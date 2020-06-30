package com.gang.study.sso.ltpa.demo.to;

import com.gang.study.sso.ltpa.demo.utils.LMBCSUtil;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @Classname LtpaToken
 * @Description TODO
 * @Date 2020/6/30 16:08
 * @Created by zengzg
 */
public class LtpaToken {

    private byte[] creation;
    private Date creationDate;
    private byte[] digest;
    private byte[] expires;
    private Date expiresDate;
    private byte[] hash;
    private byte[] header;
    private String tokenStr;
    private byte[] rawToken;
    private byte[] user;

    public LtpaToken() {
        init();
    }

    public LtpaToken(String token) {
        init();
        this.tokenStr = token;
        this.rawToken = Base64Utils.decodeFromString(token);
        this.user = new byte[this.rawToken.length - 60];
        for (int i = 0; i < 4; i++) {
            this.header[i] = this.rawToken[i];
        }
        for (int i = 4; i < 12; i++) {
            this.creation[(i - 4)] = this.rawToken[i];
        }
        for (int i = 12; i < 20; i++) {
            this.expires[(i - 12)] = this.rawToken[i];
        }
        for (int i = 20; i < this.rawToken.length - 40; i++) {
            this.user[(i - 20)] = this.rawToken[i];
        }
        for (int i = this.rawToken.length - 40; i < this.rawToken.length; i++) {
            this.digest[(i - (this.rawToken.length - 40))] = this.rawToken[i];
        }
        this.creationDate = new Date(Long.parseLong(new String(this.creation), 16) * 1000L);
        this.expiresDate = new Date(Long.parseLong(new String(this.expires), 16) * 1000L);
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
        }
        return null;
    }

    public Date getExpiresDate() {
        return this.expiresDate;
    }

    public String getUsername() {
        String userName = null;
        try {
            userName = new String(this.user, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            userName = new String(this.user);
        }
        return userName;
    }

    public String getUsername(String code) {
        String userName = null;
        try {
            userName = new String(this.user, code);
        } catch (UnsupportedEncodingException e) {
            userName = new String(this.user);
        }
        return userName;
    }

    private void init() {
        this.creation = new byte[8];
        this.digest = new byte[40];
        this.expires = new byte[8];
        this.hash = new byte[20];
        this.header = new byte[4];
    }

    public boolean isValid(String secretKey) {
        boolean validDigest = false;
        boolean validDateRange = false;

        byte[] bytes = (byte[]) null;
        Date now = new Date();

        MessageDigest md = getMessageDigest();
        bytes = LMBCSUtil.concatenate(bytes, this.header);
        bytes = LMBCSUtil.concatenate(bytes, this.creation);
        bytes = LMBCSUtil.concatenate(bytes, this.expires);
        bytes = LMBCSUtil.concatenate(bytes, this.user);

        bytes = LMBCSUtil.concatenate(bytes, Base64Utils.decodeFromString(secretKey));

        byte[] newDigest = md.digest(bytes);

        String di = byte2hex(newDigest);
        validDigest = MessageDigest.isEqual(this.digest, di.getBytes());

        validDateRange = now.before(this.expiresDate);
        boolean result = (validDigest) && (validDateRange);
        return result;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public String toString() {
        return encodeToken(this.tokenStr);
    }

    public String toPlainString() {
        return this.tokenStr;
    }

    public byte[] getCreation() {
        return this.creation;
    }

    public void setCreation(byte[] creation) {
        this.creation = creation;
    }

    public byte[] getExpires() {
        return this.expires;
    }

    public void setExpires(byte[] expires) {
        this.expires = expires;
    }

    public byte[] getHeader() {
        return this.header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDigest(byte[] digest) {
        this.digest = digest;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }

    public void setUser(byte[] user) {
        this.user = user;
    }

    public byte[] getUser() {
        return this.user;
    }

    public String getTokenStr() {
        return encodeToken(this.tokenStr);
    }

    public static String encodeToken(String token) {
        try {
            return URLEncoder.encode(URLEncoder.encode(URLEncoder.encode(token, "UTF-8"), "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Token encode error[UnsupportedEncodingException]!");
            e.printStackTrace();
        }

        return token;
    }

    public static String decodeToken(String token) {
        if (token.indexOf("+") == -1) {
            try {
                String decodeToken1 = URLDecoder.decode(token, "UTF-8");
                if (decodeToken1.indexOf("+") == -1) {
                    String decodeToken2 = URLDecoder.decode(decodeToken1, "UTF-8");
                    if (decodeToken2.indexOf("+") == -1) {
                        return URLDecoder.decode(decodeToken2, "UTF-8");
                    }
                    return decodeToken2;
                }

                return decodeToken1;
            } catch (UnsupportedEncodingException e) {
                System.err.println("Token decode error[UnsupportedEncodingException]!");
                e.printStackTrace();
            }
        }

        return token;
    }
}
