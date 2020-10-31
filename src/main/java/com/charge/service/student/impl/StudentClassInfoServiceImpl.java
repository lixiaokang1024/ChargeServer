package com.charge.service.student.impl;

import com.charge.Exception.BusinessException;
import com.charge.enums.student.GraduateStatus;
import com.charge.mapper.school.ClassInfoMapper;
import com.charge.mapper.school.GradeInfoMapper;
import com.charge.mapper.student.StudentChargeInfoMapper;
import com.charge.mapper.student.StudentClassInfoMapper;
import com.charge.mapper.student.StudentExtInfoMapper;
import com.charge.mapper.student.StudentInfoMapper;
import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.pojo.student.StudentExtInfo;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.student.StudentClassInfoService;
import com.charge.util.DateUtil;
import com.charge.util.RequestParamUtil;
import com.charge.vo.student.StudentClassInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class StudentClassInfoServiceImpl implements StudentClassInfoService {

    @Autowired
    private StudentClassInfoMapper studentClassInfoMapper;

    @Autowired
    private GradeInfoMapper gradeInfoMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private StudentExtInfoMapper studentExtInfoMapper;

    @Autowired
    private StudentChargeInfoMapper studentChargeInfoMapper;

    public void insertSelective(StudentClassInfo studentClassInfo) {
        studentClassInfo.setCreateTime(DateUtil.getCurrentTimespan());
        studentClassInfoMapper.deleteByStudentId(studentClassInfo.getStudentId());
        studentClassInfoMapper.insertSelective(studentClassInfo);
    }

    public int countStudentClassInfo(StudentClassInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentClassInfoMapper.countStudentClassInfo(paramMap);
    }

    public List<StudentClassInfoVo> queryStudentClassInfoList(StudentClassInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentClassInfoMapper.queryStudentClassInfoPageList(paramMap);
    }

    public StudentClassInfo getByStudentId(Integer studentId) {
        return studentClassInfoMapper.getByStudentId(studentId);
    }

    public void upStudentClass(List<StudentClassInfoVo> studentClassInfoVoList) {
        int maxGradeLevel = gradeInfoMapper.getMaxGradeLevel();
        for(StudentClassInfoVo studentClassInfoVo:studentClassInfoVoList){
            if(studentClassInfoVo.getClassId() == null || studentClassInfoVo.getClassId() == 0){
                continue;
            }
            if(studentClassInfoVo.getGradeLevel() == maxGradeLevel){
                upGraduateStudent(studentClassInfoVo);
            }else{
                GradeInfo gradeInfo = gradeInfoMapper.getGradeInfoByLevel(studentClassInfoVo.getGradeLevel() + 1);
                GradeInfo oldGradeInfo = gradeInfoMapper.getGradeInfoByLevel(studentClassInfoVo.getGradeLevel());
                if(gradeInfo == null){
                    throw new BusinessException("未找到【"+oldGradeInfo.getName()+"】对应的年级升级");
                }
                ClassInfo classInfo = classInfoMapper.getByClassNameGradeId(studentClassInfoVo.getClassName(), gradeInfo.getId());
                if(classInfo == null){
                    throw new BusinessException("未找到【"+oldGradeInfo.getName()+studentClassInfoVo.getClassName()+"】对应的班级升级");
                }
                StudentClassInfo studentClassInfo = new StudentClassInfo();
                studentClassInfo.setId(studentClassInfoVo.getId());
                studentClassInfo.setClassId(classInfo.getId());
                studentClassInfoMapper.updateByPrimaryKeySelective(studentClassInfo);
            }
        }
    }

    @Override
    public void updateStudentClassInfo(Integer studentId, Integer classId) {
        StudentClassInfo studentClassInfo = studentClassInfoMapper.getByStudentId(studentId);
        if(studentClassInfo == null){
            studentClassInfo = new StudentClassInfo();
            studentClassInfo.setStudentId(studentId);
            studentClassInfo.setClassId(classId);
            studentClassInfo.setCreateTime(DateUtil.getCurrentTimespan());
            studentClassInfoMapper.insertSelective(studentClassInfo);
        }else{
            studentClassInfo.setClassId(classId);
            studentClassInfoMapper.updateByPrimaryKeySelective(studentClassInfo);
        }
    }

    @Override
    public void leaveSchool(Integer studentId) {
        if(studentChargeInfoMapper.countUnCharged(studentId) > 0){
            updateGraduteStatus(studentId, GraduateStatus.UN_GRADUATE.getCode(), "退学失败，有欠费项目未缴纳。");
        }else{
            updateGraduteStatus(studentId, GraduateStatus.LEAVE.getCode(), "");
        }
    }

    private void updateGraduteStatus(Integer studentId, Integer graduteStatus, String remark){
        StudentClassInfo studentClassInfo = new StudentClassInfo();
        studentClassInfo.setStudentId(studentId);
        studentClassInfo.setGraduate(graduteStatus);
        studentClassInfo.setRemark(remark);
        studentClassInfoMapper.updateByStudentId(studentClassInfo);

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setId(studentId);
        studentInfo.setGraduate(graduteStatus);
        studentInfoMapper.updateByPrimaryKeySelective(studentInfo);

        StudentExtInfo studentExtInfo = new StudentExtInfo();
        studentExtInfo.setStudentId(studentId);
        studentExtInfo.setGraduate(graduteStatus);
        if(graduteStatus == GraduateStatus.GRADUATE.getCode() || graduteStatus == GraduateStatus.LEAVE.getCode()){
            studentExtInfo.setGraduateTime(DateUtil.getCurrentTimespan());
        }
        studentExtInfoMapper.updateByStudentIdSelective(studentExtInfo);
    }

    //升级毕业生
    private void upGraduateStudent(StudentClassInfoVo studentClassInfoVo) {
        StudentExtInfo studentExtInfo = studentExtInfoMapper.getByStudentId(studentClassInfoVo.getStudentId());
        if(studentExtInfo.getDeposit()>0){
            updateGraduteStatus(studentClassInfoVo.getStudentId(), GraduateStatus.UN_GRADUATE.getCode(), "毕业失败，押金未退。");
            return;
        }
        if(studentExtInfo.getPrepaymentAmount()>0){
            updateGraduteStatus(studentClassInfoVo.getStudentId(), GraduateStatus.UN_GRADUATE.getCode(), "毕业失败，预交费未退。");
            return;
        }
        if(studentChargeInfoMapper.countUnCharged(studentClassInfoVo.getStudentId()) > 0){
            updateGraduteStatus(studentClassInfoVo.getStudentId(), GraduateStatus.UN_GRADUATE.getCode(), "毕业失败，有欠费项目未缴纳。");
        }else{
            updateGraduteStatus(studentClassInfoVo.getStudentId(), GraduateStatus.GRADUATE.getCode(), "");
        }
    }
}