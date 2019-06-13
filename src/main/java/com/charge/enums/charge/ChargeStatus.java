package com.charge.enums.charge;

public enum ChargeStatus {

    CHARGED(0, "未缴费"), NOT_CHARGED(1,"已缴费");

    private Integer code;
    private String value;

    ChargeStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ChargeStatus getEnum(Integer code) {
        for (ChargeStatus sex : ChargeStatus.values()) {
            if (sex.getCode().equals(code)) {
                return sex;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
