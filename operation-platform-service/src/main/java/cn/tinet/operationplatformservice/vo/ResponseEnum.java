package cn.tinet.operationplatformservice.vo;

/**
 * @Time : 2021/3/3 22:23
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ResultEnum.java
 * @Software: IntelliJ IDEA
 **/
public enum ResponseEnum {
    /**
     * 错误码规则 参考阿里Java开发手册
     */
    SUCCESS("00000","成功"),
    UNAUTHORIZED_ACCESS("A0301", "访问未授权"),
    PASSWORD_ERROR("A0210", "用户密码错误"),
    KUBERNETES_REQUEST_FAILED("C0155", "kubernetes request failed");

    private String code;
    private String description;

    ResponseEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
