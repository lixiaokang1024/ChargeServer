package com.charge.enums.charge;

public enum ChargeStatus {

    NOT_CHARGED(0, "未缴费"), PART_CHARGED(1,"部分缴费"), CHARGED(2, "已缴费");

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
