package com.charge.pojo.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * 功能概述：返回结果数据传输对象
 * <br/>
 * 创建时间：2015年10月22日下午3:57:16
 * <br/>
 * 修改记录：
 *
 * @author lixiaoliang
 */
public class ResultDTO<T> implements Serializable {

    /**
     * 字段说明
     */
    private static final long serialVersionUID = 8508537142240594075L;

    /**
     * errcode 错误编码，默认 0 = 成功   1=失败
     */
    private Integer errcode = 0;

    /**
     * errmsg 错误信息
     */
    private String errmsg;

    /**
     * 异常信息
     */
    private Exception sompException;


    /**
     * 返回结果
     */
    private T data;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Exception getSompException() {
        return sompException;
    }

    public void setSompException(Exception sompException) {
        this.sompException = sompException;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
