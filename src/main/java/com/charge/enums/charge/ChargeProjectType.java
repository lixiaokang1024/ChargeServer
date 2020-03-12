package com.charge.enums.charge;

public enum ChargeProjectType {

    PREPAYMENT_AMOUNT(1, "预交费"), DEPOSIT(2, "押金"), OTHER(3, "项目缴费");

    private Integer code;
    private String value;

    ChargeProjectType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ChargeProjectType getEnum(Integer code) {
        for (ChargeProjectType sex : ChargeProjectType.values()) {
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
