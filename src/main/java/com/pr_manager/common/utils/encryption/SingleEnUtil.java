package com.pr_manager.common.utils.encryption;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 单向加密工具
 * Created by Jo on 2017/8/1.
 */
public class SingleEnUtil {

    public static String MD5(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] byteArray = message.getBytes("UTF-8");
            byte[] md5Bytes = messageDigest.digest(byteArray);

            StringBuilder md5Word = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    md5Word.append("0");
                }
                md5Word.append(Integer.toHexString(val));
            }
            return md5Word.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String SHA(String message) {
        try {
            byte[] inputData = message.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(inputData);
            BigInteger shaWord = new BigInteger(messageDigest.digest());
            return shaWord.toString(32);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String HmacSHA256(String message,String key){
        try  {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] shaBytes = mac.doFinal(message.getBytes("UTF-8"));

            StringBuilder shaWord = new StringBuilder();
            for (int n = 0; shaBytes!=null && n < shaBytes.length; n++) {
                String temp = Integer.toHexString(shaBytes[n] & 0XFF);
                if (temp.length() == 1)
                    shaWord.append('0');
                shaWord.append(temp);
            }
            return shaWord.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
