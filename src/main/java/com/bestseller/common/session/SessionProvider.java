package com.bestseller.common.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SessionProvider {
	
	public void setAttribute(HttpServletResponse response,HttpServletRequest request,String name,Serializable value);
	
	public Serializable getAttribute(HttpServletResponse response,HttpServletRequest request,String name);
	
	public String getSessionId(HttpServletResponse response,HttpServletRequest request);
	
	public void logout(HttpServletResponse response,HttpServletRequest request);
}
