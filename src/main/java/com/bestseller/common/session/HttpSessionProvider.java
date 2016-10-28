package com.bestseller.common.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionProvider implements SessionProvider {

	@Override
	public void setAttribute(HttpServletRequest request, String name, Serializable value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

	@Override
	public Serializable getAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if(null!=session){
			return (Serializable) session.getAttribute(name);
		}
		return null;
	}

	@Override
	public String getSessionId(HttpServletRequest request) {
		return request.getSession().getId();
	}

	@Override
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.invalidate();
		}
	}

}
