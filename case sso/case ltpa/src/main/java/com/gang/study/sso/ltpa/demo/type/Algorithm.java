package com.gang.study.sso.ltpa.demo.type;

/**
 * @Classname Algorithm
 * @Description TODO
 * @Date 2020/6/30 16:53
 * @Created by zengzg
 */
public enum Algorithm {

    AES("AES", "AES/CBC/PKCS5Padding"), DES("DES", "DESede/ECB/PKCS5Padding");

    private String code;
    private String keyWord;

    Algorithm(String code, String keyWord) {
        this.code = code;
        this.keyWord = keyWord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Boolean equals(String key) {
        return this.getCode().equals(key);
    }
}
