package com.pr_manager.common.response;

import java.io.Serializable;

/**
 *
 * Created by Jo on 2017/5/12.
 */
public enum ErrorCodeEnum implements Serializable {

    No_Error("No Error", 1000),
    Error("Unknown error", 1001),
    Exist_Already("已存在", 1002),
    Exist_Null("不存在",1003),
    Password_Wrong("用户名或密码错误", 1004),
    Token_Failure("JWT验证失效", 1005),
    Token_Forbidden("JWT验证权限不足", 1006),
    Column_NotMatch("修改值与字段不匹配",2001),
    ;

    private String label;
    private Integer code;

    ErrorCodeEnum() {
    }

    ErrorCodeEnum(String label, Integer code) {
        this.label = label;
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ErrorCode#"+code.toString()+":"+label;
    }

    public static ErrorCodeEnum parse(int code) {
        for (ErrorCodeEnum theEnum : ErrorCodeEnum.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return No_Error;
    }
}
