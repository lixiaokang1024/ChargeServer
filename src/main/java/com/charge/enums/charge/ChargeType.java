package com.charge.enums.charge;

public enum ChargeType {

    CASH(0, "现金"), ADVANCE_CHARGE(1,"预缴费"), OTHER(2, "其他");

    private Integer code;
    private String value;

    ChargeType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ChargeType getEnum(Integer code) {
        for (ChargeType sex : ChargeType.values()) {
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
