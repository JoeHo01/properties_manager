package com.pr_manager.common.session;

/**
 *
 * Created by Jo on 2017/7/31.
 */
public enum LimitationEnum {
    Admin("admin","1"),
    User("user","2"),
    Customer("customer","3"),
    ;
    private String label;
    private String code;

    LimitationEnum(String label, String code) {
        this.label = label;
        this.code = code;
    }

    public String label() {
        return label;
    }

    public String code() {
        return code;
    }
}
