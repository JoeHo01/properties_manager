package com.pr_manager.common.utils;

import java.util.regex.Pattern;

/**
 *
 * Created by Jo on 2017/7/31.
 */
public enum ValidatorEnum {
    PHONE("^1\\d{10}$"),//以1开头, 11位数字
    PASSWORD("^(?![0-9]+$)(?![a-zA-Z]+$)(?![^ 0-9a-zA-Z]+$)[^ \\\\s.]{6,16}$"),//不可为纯数字, 不可为纯英文字母, 不可为纯符号, 不包含\和., 6-16位
    EMAIL("^[0-9a-zA-Z_]+@[0-9a-zA-Z.]+(.com|.net)$"),//登录名@主机名.域名 域名支持.com或.net
    ;

    private String code;

    ValidatorEnum(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
