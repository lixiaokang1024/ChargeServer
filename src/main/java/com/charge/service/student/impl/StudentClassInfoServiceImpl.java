package com.charge.service.student.impl;

import com.charge.Exception.BusinessException;
import com.charge.enums.student.GraduateStatus;
import com.charge.mapper.school.ClassInfoMapper;
import com.charge.mapper.school.GradeInfoMapper;
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

    public void insertSelective(StudentClassInfo studentClassInfo) {
        studentClassInfo.setCreateTime(DateUtil.getCurrentTimespan());
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
            if(studentClassInfoVo.getGradeLevel() == maxGradeLevel){
                upGraduateStudent(studentClassInfoVo);
            }else{
                GradeInfo gradeInfo = gradeInfoMapper.getGradeInfoByLevel(studentClassInfoVo.getGradeLevel() + 1);
                if(gradeInfo == null){
                    throw new BusinessException("未找到合适的年级");
                }
                ClassInfo classInfo = classInfoMapper.getByClassNameGradeId(studentClassInfoVo.getClassName(), gradeInfo.getId());
                if(classInfo == null){
                    throw new BusinessException("未找到合适的班级");
                }
                StudentClassInfo studentClassInfo = new StudentClassInfo();
                studentClassInfo.setId(studentClassInfoVo.getId());
                studentClassInfo.setClassId(classInfo.getId());
                studentClassInfoMapper.updateByPrimaryKeySelective(studentClassInfo);
            }
        }
    }

    //升级毕业生
    private void upGraduateStudent(StudentClassInfoVo studentClassInfoVo) {
        StudentClassInfo studentClassInfo = new StudentClassInfo();
        studentClassInfo.setId(studentClassInfoVo.getId());
        studentClassInfo.setGraduate(GraduateStatus.UN_GRADUATE.getCode());
        studentClassInfoMapper.updateByPrimaryKeySelective(studentClassInfo);

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setId(studentClassInfoVo.getStudentId());
        studentInfo.setGraduate(true);
        studentInfoMapper.updateByPrimaryKeySelective(studentInfo);

        StudentExtInfo studentExtInfo = new StudentExtInfo();
        studentExtInfo.setStudentId(studentClassInfoVo.getStudentId());
        studentExtInfo.setGraduate(1);
        studentExtInfo.setGraduateTime(DateUtil.getCurrentTimespan());
        studentExtInfoMapper.updateByStudentIdSelective(studentExtInfo);
    }
}