package com.charge.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class PermissionTag extends TagSupport {
	
	//此为传入参数
	private String url;
	private PageContext pageContext;
	
	@Override
	public int doStartTag() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession();
		List<String> menuList = (List<String>) session.getAttribute("resource");
		if (!menuList.contains(url)) {
			//TagSupport.SKIP_BODY返回则表示不显示标签体内的内容
			return TagSupport.SKIP_BODY;
		}
		//TagSupport.EVAL_BODY_INCLUDE返回则表示需要显示标签体内的内容
		return TagSupport.EVAL_BODY_INCLUDE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
}