package com.charge.proxy.school;

import com.charge.Exception.BusinessException;
import com.charge.param.school.ClassSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.service.school.SchoolService;
import com.charge.service.student.StudentClassInfoService;
import com.charge.util.JsonUtil;
import com.charge.vo.school.ClassInfoVo;
import com.charge.vo.school.GradeInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolProxy {

    private static final Logger logger = LoggerFactory.getLogger(SchoolProxy.class);

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentClassInfoService studentClassInfoService;

    public void saveOrModifyClassInfo(ClassInfo classInfo){
        logger.info("班级信息保存参数：{}", JsonUtil.toJson(classInfo));
        if(classInfo.getId() != null){
            ClassInfo classInfoIndb = schoolService.getClassInfoById(classInfo.getId());
            if(classInfoIndb == null){
                throw new BusinessException("记录不存在！");
            }
            schoolService.updateClassInfo(classInfo);
        }else{
            if(classInfo.getGradeId() == -1){
                GradeSearchParam param = new GradeSearchParam();
                param.setPageSize(100);
                List<GradeInfo> gradeInfoList = schoolService.queryGradeList(param);
                for(GradeInfo gradeInfo:gradeInfoList){
                    ClassInfo classInfoIndb = schoolService.getByClassNameGradeId(classInfo.getName(), gradeInfo.getId());
                    if(classInfoIndb == null){
                        classInfo.setGradeId(gradeInfo.getId());
                        schoolService.insertSelectiveClassInfo(classInfo);
                    }
                }
            }else{
                schoolService.insertSelectiveClassInfo(classInfo);
            }
        }
    }

    public void saveGradeInfo(GradeInfo gradeInfo){
        logger.info("年纪信息保存参数：{}", JsonUtil.toJson(gradeInfo));
        if(gradeInfo.getId() != null){
            GradeInfo gradeInfoIndb = schoolService.getGradeInfoById(gradeInfo.getId());
            if(gradeInfoIndb == null){
                throw new BusinessException("记录不存在！");
            }
            schoolService.updateGradeInfo(gradeInfo);
        }else{
            schoolService.insertSelectiveGradeInfo(gradeInfo);
        }
    }

    public PageResultDTO<List<GradeInfoVo>> queryGradeInfo(GradeSearchParam searchParam){
        logger.info("查询年级信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<GradeInfoVo>> pageResultDTO = new PageResultDTO<List<GradeInfoVo>>();
        try {

            int count = schoolService.countGrade(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<GradeInfo> gradeInfoList = schoolService.queryGradeList(searchParam);
            if(!CollectionUtils.isEmpty(gradeInfoList)){
                List<GradeInfoVo> gradeInfoVoList = transferToGradeInfoVo(gradeInfoList);
                pageResultDTO.setData(gradeInfoVoList);
            }
        }catch (Exception e){
            logger.error("查询年级信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    public PageResultDTO<List<ClassInfoVo>> queryClassInfo(ClassSearchParam searchParam){
        logger.info("查询班级信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<ClassInfoVo>> pageResultDTO = new PageResultDTO<List<ClassInfoVo>>();
        try {

            int count = schoolService.countClass(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<ClassInfo> classInfoList = schoolService.queryClassList(searchParam);
            if(!CollectionUtils.isEmpty(classInfoList)){
                List<ClassInfoVo> classInfoVoList = transferToClassInfoVo(classInfoList);
                pageResultDTO.setData(classInfoVoList);
            }
        }catch (Exception e){
            logger.error("查询年级信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    private List<GradeInfoVo> transferToGradeInfoVo(List<GradeInfo> gradeInfoList) {
        List<GradeInfoVo> gradeInfoVoList = new ArrayList<GradeInfoVo>();
        if(CollectionUtils.isEmpty(gradeInfoList)){
            return gradeInfoVoList;
        }
        for(GradeInfo gradeInfo:gradeInfoList){
            GradeInfoVo gradeInfoVo = new GradeInfoVo();
            BeanUtils.copyProperties(gradeInfo, gradeInfoVo);
            gradeInfoVoList.add(gradeInfoVo);
        }
        return gradeInfoVoList;
    }

    private List<ClassInfoVo> transferToClassInfoVo(List<ClassInfo> classInfoList) {
        List<ClassInfoVo> classInfoVoList = new ArrayList<ClassInfoVo>();
        if(CollectionUtils.isEmpty(classInfoList)){
            return classInfoVoList;
        }
        for(ClassInfo classInfo:classInfoList){
            ClassInfoVo classInfoVo = new ClassInfoVo();
            BeanUtils.copyProperties(classInfo, classInfoVo);
            classInfoVoList.add(classInfoVo);
        }
        return classInfoVoList;
    }

    public void deleteGradeInfo(Integer gradeId) {
        if(schoolService.getGradeInfoById(gradeId) == null){
            throw new BusinessException("年级信息已删除，请勿重复操作。");
        }
        ClassSearchParam classSearchParam = new ClassSearchParam();
        classSearchParam.setGradeId(gradeId);
        if(schoolService.countClass(classSearchParam) > 0){
            throw new BusinessException("有班级属于此年级，请先删除班级信息。");
        }
        schoolService.deleteGradeInfo(gradeId);
    }

    public void deleteClassInfo(Integer classId) {
        if(schoolService.getClassInfoById(classId) == null){
            throw new BusinessException("班级信息已删除，请勿重复操作。");
        }
        StudentClassInfoSearchParam studentClassInfoSearchParam = new StudentClassInfoSearchParam();
        studentClassInfoSearchParam.setClassId(classId);
        if(studentClassInfoService.countStudentClassInfo(studentClassInfoSearchParam) > 0){
            throw new BusinessException("有学生属于此班级，无法删除班级信息。");
        }
        schoolService.deleteClassInfo(classId);
    }
}
