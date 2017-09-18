package com.pr_manager.common.utils.encryption;

import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 *
 * Created by Jo on 2017/7/27.
 */
public class DESUtil {

    /**
     * 加密
     * @param datasource 原始数据
     * @param key 密钥
     * @return 加密数据
     */
    public static String encrypt(String datasource, String key) {
        try {
            //DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成SecretKey
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, random);
            //加密操作
            byte[] bytes = cipher.doFinal(datasource.getBytes());
            //Base64转码
            return Base64Utils.encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @param enData 加密数据
     * @param key 密钥
     * @return 解密数据
     */
    public static String decrypt(String enData, String key) {
        try {
            //Base64转码
            byte[] bytes = Base64Utils.decodeFromString(enData);
            //DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            //创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            //创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //将DESKeySpec对象转换成SecretKey对象
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
            //解密操作
            return new String(cipher.doFinal(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
