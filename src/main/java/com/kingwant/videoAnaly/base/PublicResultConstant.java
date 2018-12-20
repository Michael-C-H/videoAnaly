package com.kingwant.videoAnaly.base;

/**
 * @author brk
 * @since 2018-05-03
 */
public enum PublicResultConstant {
    /**
     * 成功
     */
    SUCCESS("0000", "success"),
    /**
     * 异常
     */
    FAILED("1001", "系统错误"),
    
    /**
     * 未登录/token过期
     */
    UNAUTHORIZED("1002", "获取登录用户信息失败"),
    /**
     * 失败
     */
    ERROR("1003", "操作失败"),

    /**
     * 用户名或密码错误
     */
    INVALID_USERNAME_PASSWORD("2001", "用户名或密码错误"),
    /**
     * 用户名或密码错误
     */
    PARAM_ERROR("3000","请求接口参数错误"),
    /**
     *
     */
    ;
    
    

    public String result;
    public String msg;

    PublicResultConstant(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
