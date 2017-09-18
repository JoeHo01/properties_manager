package com.pr_manager.common.utils.encryption;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jo on 2017/7/27.
 */
public class KeyUtil {
    public static String requestKey(HttpServletRequest request){
        String remoteAddress = request.getRemoteAddr();
        return SingleEnUtil.MD5(remoteAddress);
    }
}
