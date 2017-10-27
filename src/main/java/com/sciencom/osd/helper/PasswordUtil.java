package com.sciencom.osd.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private static final String SALT = "SJjuDF8990-@ajsdu#90";

    private static String md5(String raw){
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return e.getMessage();
        }
        md.update(raw.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<bytes.length; i++){
            sb.append(Integer.toString(bytes[i]&0xff));
        }
        return sb.toString();
    }

    public static String encode(String raw){
        String temp = raw + SALT;
        return md5(temp);
    }

    public static boolean match(String raw, String encoded){
        if (encoded == null) return false;
        if (raw == null) return false;
        return encode(raw).equals(encoded);
    }
}
