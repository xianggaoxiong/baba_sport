package com.bestseller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bestseller.common.Constant;
import com.bestseller.common.session.SessionProvider;
import com.bestseller.pojo.Buyer;

public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	private SessionProvider sessionProvider;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Buyer buyer=(Buyer) sessionProvider.getAttribute(response,request, Constant.SESSION_USER_NAME);
		boolean flag=false;
		if(buyer!=null){
			flag=true;
		}
		request.setAttribute("isLogin", flag);
		
		String requestURI = request.getRequestURI();
		if(requestURI.startsWith("/buyer")){
			if(buyer==null){
				response.sendRedirect("/user/userCheck/login.shtml?returnUrl="+request.getParameter("returnUrl"));
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
