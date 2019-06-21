package com.charge.enums.student;

public enum GraduateStatus {
    UN_GRADUATE(0, "未毕业"), GRADUATE(1,"已毕业");

    private Integer code;
    private String value;

    GraduateStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static GraduateStatus getEnum(Integer code) {
        for (GraduateStatus sex : GraduateStatus.values()) {
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
