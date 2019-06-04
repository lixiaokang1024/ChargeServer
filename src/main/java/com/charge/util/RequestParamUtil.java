/**
 * Copyright (C), 2018-2018, 顺丰优选
 * FileName: RequestParamUtil
 * Author:   776364
 * Date:     2018/8/30 17:30
 * Description: 请求入参转换类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.charge.util;

import com.charge.pojo.common.Page;

import java.util.HashMap;
import java.util.Map;

public class RequestParamUtil {

    public static Map<String, Object> getRequestParamMap(Integer currentPage, Integer pageSize, Object object) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);

        paramMap.put("page", page);
        paramMap.put("param", object);
        return paramMap;
    }

}
