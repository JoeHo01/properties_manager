package com.pr_manager.common.session;

import com.pr_manager.common.utils.ParamUtil;
import com.pr_manager.common.utils.encryption.SingleEnUtil;
import net.sf.json.JSONObject;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token 签发验证
 * Created by Jo on 2017/7/31.
 */
public class TokenManager {
    private final static String secret = "T78$z";
    private final static long Failure_Time = 2 * 3600 * 1000;

    private final static String typ = "JWT";
    private final static String alg = "SHA";
    private final static String iss = "vista";
    private final static String sub = "WS";

    public static String getToken(Map<String, String> information) {
        String header = getHeader();
        String payload = getPayload(information);
        String signature = getSignature(header, payload);
        return header + "." + payload + "." + signature;
    }

    public static Map<String, String> getPayload(String token) {
        if (checkToken(token)) {
            String[] part = token.split("\\.");
            String code = new String(Base64Utils.decodeFromString(part[1]));
            return (Map<String, String>) JSONObject.fromObject(code);
        }
        return null;
    }

    public static boolean checkToken(String token) {
        if (token == null) return false;
        String[] part = token.split("\\.");
        if (part[2].equals(SingleEnUtil.HmacSHA256(part[0] + part[1], secret))) {
            String code = new String(Base64Utils.decodeFromString(part[1]));
            Map<String, String> payload = (Map<String, String>) JSONObject.fromObject(code);
            long exp = Long.valueOf(payload.get("exp"));
            if (new Date().getTime() < exp) {
                return true;
            }
        }
        return false;
    }

    private static String getHeader() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("typ", typ);
        param.put("alg", alg);
        String message = ParamUtil.stringify(param);
        return Base64Utils.encodeToString(message.getBytes());
    }

    private static String getPayload(Map<String, String> information) {
        information.put("iss", iss);
        information.put("sub", sub);
        long exp = new Date().getTime() + Failure_Time;
        information.put("exp", String.valueOf(exp));
        String message = ParamUtil.stringify(information);
        return Base64Utils.encodeToString(message.getBytes());
    }

    private static String getSignature(String header, String payload) {
        return SingleEnUtil.HmacSHA256(header + payload, secret);
    }
}