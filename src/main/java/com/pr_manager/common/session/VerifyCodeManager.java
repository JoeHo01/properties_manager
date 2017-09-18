package com.pr_manager.common.session;


import java.util.*;

/**
 *
 * Created by Jo on 17/07/19.
 */
public class VerifyCodeManager {

    private static final int Failure_Time = 5 * 60 * 1000;//失效时间 5分钟
    private static HashMap<String, String> codeMap = new HashMap<String, String>();

    public static String newCode(String phone) {
        String code = getCode(phone);
        if (code != null) return code;

        Random random = new Random();
        int a = random.nextInt(1000000);
        code = String.format("%06d", a);

        codeMap.put(phone, code);
        new Timer().schedule(new Task(phone), Failure_Time);
        return code;
    }

    public static String getCode(String phone) {
        return codeMap.get(phone);
    }

    /**
     * 删除某用户的验证码Code
     */
    public static void removeCode(String phone) {
        codeMap.remove(phone);
    }

    private static class Task extends TimerTask {
        private String phone;

        public Task(String phone) {
            this.phone = phone;
        }

        @Override
        public void run() {
            removeCode(phone);
        }
    }
}
