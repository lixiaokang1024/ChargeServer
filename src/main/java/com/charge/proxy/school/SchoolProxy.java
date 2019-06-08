package com.charge.proxy.school;

import com.charge.param.school.ClassSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.service.school.SchoolService;
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

    public void saveClassInfo(ClassInfo classInfo){
        logger.info("班级信息保存参数：{}", JsonUtil.toJson(classInfo));
        schoolService.insertSelectiveClassInfo(classInfo);
    }

    public void saveGradeInfo(GradeInfo gradeInfo){
        logger.info("年纪信息保存参数：{}", JsonUtil.toJson(gradeInfo));
        schoolService.insertSelectiveGradeInfo(gradeInfo);
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

}
