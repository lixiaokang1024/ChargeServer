package com.charge.enums.student;

public enum Sex {
    MALE(0, "男"), FEMALE(1,"女");

    private Integer code;
    private String value;

    Sex(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Sex getEnum(Integer code) {
        for (Sex sex : Sex.values()) {
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
