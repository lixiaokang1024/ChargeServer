package com.charge.vo.student;

import com.charge.enums.student.GraduateStatus;
import com.charge.enums.student.Sex;

import java.io.Serializable;
import java.util.Date;

public class StudentInfoVo implements Serializable {
    /**
     * student_info.id 
     */
    private Integer id;

    /**
     * student_info.name 
     */
    private String name;

    /**
     * student_info.sex 
     */
    private Integer sex;
    private String sexStr;

    /**
     * student_info.year 
     */
    private Integer year;

    /**
     * student_info.month 
     */
    private Integer month;

    /**
     * student_info.day 
     */
    private Integer day;
    private String bornDate;

    /**
     * student_info.age 
     */
    private Integer age;

    /**
     * student_info.parent_name 
     */
    private String parentName;

    private String parentIdCardType;

    private String parentIdCardNumber;

    /**
     * student_info.relation 
     */
    private String relation;

    /**
     * student_info.mobile 
     */
    private String mobile;

    /**
     * student_info.address 
     */
    private String address;

    /**
     * student_info.is_graduate 
     */
    private Integer graduate;
    private String graduateStr;

    private Double deposit;
    private Double prepaymentAmount;

    private String idCardType;

    private String idCardNumber;

    private String country;

    private String nation;

    private String oversea;

    private String bornPlace;

    private String nativePlace;

    private String accountCharacter;

    private String nonAgriculturalAccountType;

    private String registeredResidence;

    /**
     * student_info.creat_time 
     */
    private Integer creatTime;

    /**
     * student_info.modify_time 
     */
    private Date modifyTime;

    /**
     * student_info.is_deleted 
     */
    private Boolean deleted;

    /**
     * @return student_info.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for student_info.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return student_info.name 
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the value for student_info.name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getPrepaymentAmount() {
        return prepaymentAmount;
    }

    public void setPrepaymentAmount(Double prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
    }

    /**
     * @return student_info.sex 
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex the value for student_info.sex 
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return student_info.year 
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the value for student_info.year 
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return student_info.month 
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month the value for student_info.month 
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return student_info.day 
     */
    public Integer getDay() {
        return day;
    }

    /**
     * @param day the value for student_info.day 
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * @return student_info.age 
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the value for student_info.age 
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return student_info.parent_name 
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName the value for student_info.parent_name 
     */
    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    public String getParentIdCardType() {
        return parentIdCardType;
    }

    public void setParentIdCardType(String parentIdCardType) {
        this.parentIdCardType = parentIdCardType;
    }

    public String getParentIdCardNumber() {
        return parentIdCardNumber;
    }

    public void setParentIdCardNumber(String parentIdCardNumber) {
        this.parentIdCardNumber = parentIdCardNumber;
    }

    /**
     * @return student_info.relation 
     */
    public String getRelation() {
        return relation;
    }

    /**
     * @param relation the value for student_info.relation 
     */
    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
    }

    /**
     * @return student_info.mobile 
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the value for student_info.mobile 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return student_info.address 
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the value for student_info.address 
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getGraduate() {
        return graduate;
    }

    public void setGraduate(Integer graduate) {
        this.graduate = graduate;
    }

    /**
     * @return student_info.creat_time 
     */
    public Integer getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime the value for student_info.creat_time 
     */
    public void setCreatTime(Integer creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return student_info.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for student_info.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return student_info.is_deleted 
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for student_info.is_deleted 
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getSexStr() {
        return Sex.getEnum(sex).getValue();
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getBornDate() {
        return year+"-"+month+"-"+day;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getGraduateStr() {
        return GraduateStatus.getEnum(graduate).getValue();
    }

    public void setGraduateStr(String graduateStr) {
        this.graduateStr = graduateStr;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getOversea() {
        return oversea;
    }

    public void setOversea(String oversea) {
        this.oversea = oversea;
    }

    public String getBornPlace() {
        return bornPlace;
    }

    public void setBornPlace(String bornPlace) {
        this.bornPlace = bornPlace;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getAccountCharacter() {
        return accountCharacter;
    }

    public void setAccountCharacter(String accountCharacter) {
        this.accountCharacter = accountCharacter;
    }

    public String getNonAgriculturalAccountType() {
        return nonAgriculturalAccountType;
    }

    public void setNonAgriculturalAccountType(String nonAgriculturalAccountType) {
        this.nonAgriculturalAccountType = nonAgriculturalAccountType;
    }

    public String getRegisteredResidence() {
        return registeredResidence;
    }

    public void setRegisteredResidence(String registeredResidence) {
        this.registeredResidence = registeredResidence;
    }
}