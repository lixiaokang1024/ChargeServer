package com.charge.proxy.charge;

import com.charge.Exception.BusinessException;
import com.charge.param.charge.ChargeSearchParam;
import com.charge.param.charge.PayProjectIoSearchParam;
import com.charge.param.charge.PayProjectSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.charge.PayProject;
import com.charge.pojo.charge.PayProjectIo;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.service.charge.ChargeService;
import com.charge.service.school.SchoolService;
import com.charge.service.student.StudentChargeInfoService;
import com.charge.service.student.StudentClassInfoService;
import com.charge.util.JsonUtil;
import com.charge.vo.charge.ChargeProjectVo;
import com.charge.vo.charge.PayProjectIoVo;
import com.charge.vo.charge.PayProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChargeProxy {

    private static final Logger logger = LoggerFactory.getLogger(ChargeProxy.class);

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentChargeInfoService studentChargeInfoService;

    @Autowired
    private StudentClassInfoService studentClassInfoService;

    public PageResultDTO<List<ChargeProjectVo>> queryChargeProjectList(ChargeSearchParam chargeSearchParam){
        logger.info("查询收费项目信息搜索参数：{}", JsonUtil.toJson(chargeSearchParam));
        PageResultDTO<List<ChargeProjectVo>> pageResultDTO = new PageResultDTO<List<ChargeProjectVo>>();
        if (chargeSearchParam.getStudentId() != null) {
            StudentClassInfo studentClassInfo = studentClassInfoService.getByStudentId(chargeSearchParam.getStudentId());
            if (studentClassInfo == null) {
                return pageResultDTO;
            }
            ClassInfo classInfo = schoolService.getClassInfoById(studentClassInfo.getClassId());
            if (classInfo == null) {
                return pageResultDTO;
            }
            chargeSearchParam.setGradeId(classInfo.getGradeId());
        }
        try {

            int count = chargeService.countChargeProject(chargeSearchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(chargeSearchParam.getCurrentPage());
            pageResultDTO.setPageSize(chargeSearchParam.getPageSize());
            List<ChargeProject> chargeProjectList = chargeService.queryChargeProjectList(chargeSearchParam);
            if(!CollectionUtils.isEmpty(chargeProjectList)){
                List<ChargeProjectVo> chargeProjectVoList = transferToChargeProjectVo(chargeProjectList);
                pageResultDTO.setData(chargeProjectVoList);
            }
        }catch (Exception e){
            logger.error("查询收费项目信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    public PageResultDTO<List<PayProjectVo>> queryPayProjectList(PayProjectSearchParam searchParam){
        logger.info("查询支出项目信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<PayProjectVo>> pageResultDTO = new PageResultDTO<List<PayProjectVo>>();
        try {

            int count = chargeService.countPayProject(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<PayProject> payProjectList = chargeService.queryPayProjectList(searchParam);
            if(!CollectionUtils.isEmpty(payProjectList)){
                List<PayProjectVo> payProjectVoList = transferToPayProjectVo(payProjectList);
                pageResultDTO.setData(payProjectVoList);
            }
        }catch (Exception e){
            logger.error("查询支出项目信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    public PageResultDTO<List<PayProjectIoVo>> queryPayProjectIoList(PayProjectIoSearchParam searchParam){
        logger.info("查询支出历史信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<PayProjectIoVo>> pageResultDTO = new PageResultDTO<List<PayProjectIoVo>>();
        try {

            int count = chargeService.countPayProjectIo(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<PayProjectIo> payProjectIoList = chargeService.queryPayProjectIoList(searchParam);
            if(!CollectionUtils.isEmpty(payProjectIoList)){
                List<PayProjectIoVo> payProjectIoVoList = transferToPayProjectIoVo(payProjectIoList);
                pageResultDTO.setData(payProjectIoVoList);
            }
        }catch (Exception e){
            logger.error("查询支出历史信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    private List<PayProjectIoVo> transferToPayProjectIoVo(List<PayProjectIo> payProjectIoList) {
        List<PayProjectIoVo> payProjectIoVoList = new ArrayList<PayProjectIoVo>();
        for(PayProjectIo payProjectIo:payProjectIoList){
            PayProjectIoVo vo = new PayProjectIoVo();
            BeanUtils.copyProperties(payProjectIo, vo);
            payProjectIoVoList.add(vo);
        }
        return payProjectIoVoList;
    }

    private List<PayProjectVo> transferToPayProjectVo(List<PayProject> payProjectList) {
        List<PayProjectVo> payProjectVoList = new ArrayList<PayProjectVo>();
        for(PayProject payProject:payProjectList){
            PayProjectVo vo = new PayProjectVo();
            BeanUtils.copyProperties(payProject, vo);
            payProjectVoList.add(vo);
        }
        return payProjectVoList;
    }

    public void saveOrUpdateChargeProject(ChargeProject chargeProject){
        logger.info("收费项目信息保存参数：{}", JsonUtil.toJson(chargeProject));
        if(chargeProject.getId() != null){
            ChargeProject chargeProjectIndb = chargeService.getChargeProjectById(chargeProject.getId());
            if(chargeProjectIndb == null){
                throw new BusinessException("记录不存在！");
            }
            chargeService.updateChargeProject(chargeProject);
        }else{
            if(chargeProject.getGradeId() == -1){
                GradeSearchParam param = new GradeSearchParam();
                param.setPageSize(100);
                List<GradeInfo> gradeInfoList = schoolService.queryGradeList(param);
                for(GradeInfo gradeInfo:gradeInfoList){
                    ChargeProject chargeProjectIndb = chargeService.getChargeProjectByName(chargeProject.getProjectName(), gradeInfo.getId());
                    if(chargeProjectIndb == null){
                        chargeProject.setGradeId(gradeInfo.getId());
                        chargeService.insertSelective(chargeProject);
                    }
                }
            }else{
                chargeService.insertSelective(chargeProject);
            }
        }
    }

    public void saveOrUpdatePayProject(PayProject payProject){
        logger.info("支出项目信息保存参数：{}", JsonUtil.toJson(payProject));
        if(payProject.getId() != null){
            PayProject payProjectIndb = chargeService.getPayProjectById(payProject.getId());
            if(payProjectIndb == null){
                throw new BusinessException("记录不存在！");
            }
            chargeService.updatePayProject(payProject);
        }else{
            chargeService.insertPayProjectSelective(payProject);
        }
    }

    public void saveOrUpdatePayProjectIo(PayProjectIo payProjectIo){
        logger.info("支出项目信息保存参数：{}", JsonUtil.toJson(payProjectIo));
        if(payProjectIo.getId() != null){
            PayProjectIo payProjectIoIndb = chargeService.getPayProjectIoById(payProjectIo.getId());
            if(payProjectIoIndb == null){
                throw new BusinessException("记录不存在！");
            }
            chargeService.updatePayProjectIo(payProjectIo);
        }else{
            chargeService.insertPayProjectIoSelective(payProjectIo);
        }
    }

    private List<ChargeProjectVo> transferToChargeProjectVo(List<ChargeProject> chargeProjectList) {
        List<ChargeProjectVo> chargeProjectVoList = new ArrayList<ChargeProjectVo>();
        if(CollectionUtils.isEmpty(chargeProjectList)){
            return chargeProjectVoList;
        }
        for(ChargeProject chargeProject:chargeProjectList){
            ChargeProjectVo chargeProjectVo = new ChargeProjectVo();
            BeanUtils.copyProperties(chargeProject, chargeProjectVo);
            chargeProjectVoList.add(chargeProjectVo);
        }
        return chargeProjectVoList;
    }

    public void deleteChargeInfo(Integer chargeId) {
        if(chargeService.getChargeProjectById(chargeId) == null){
            throw new BusinessException("缴费项目信息已删除，请勿重复操作。");
        }
        StudentChargeInfoSearchParam studentChargeInfoSearchParam = new StudentChargeInfoSearchParam();
        studentChargeInfoSearchParam.setChargeProjectId(chargeId);
        if(studentChargeInfoService.countStudentChargeInfo(studentChargeInfoSearchParam) > 0){
            throw new BusinessException("存在此项目未缴费的学生，不能删除。");
        }
        chargeService.deleteChargeProjectById(chargeId);
    }

    public void deletePayProjectInfo(Integer payProjectId) {
        if(chargeService.getPayProjectById(payProjectId) == null){
            throw new BusinessException("项目信息已删除，请勿重复操作。");
        }
        PayProjectIoSearchParam payProjectIoSearchParam = new PayProjectIoSearchParam();
        payProjectIoSearchParam.setPayProjectId(payProjectId);
        if(chargeService.countPayProjectIo(payProjectIoSearchParam) > 0){
            throw new BusinessException("已产生此项目的流水，不能删除。");
        }
        chargeService.deletePayProjectById(payProjectId);
    }
}
