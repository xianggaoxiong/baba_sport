package com.bestseller.common.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;

public class CacheSessionProvider implements SessionProvider {
	@Autowired
	private MemCachedClient memCachedClient;
	
	private static final String  JSESSIONID = "JSESSIONID";
	
	private int expire=30;
	@Override
	public void setAttribute(HttpServletResponse response,HttpServletRequest request, String name, Serializable value) {
		Map<String,Object> session=new HashMap<>();
		session.put(name, value);
		memCachedClient.set(getSessionId(response,request), session,expire*60);
	}

	@Override
	public Serializable getAttribute(HttpServletResponse response,HttpServletRequest request, String name) {
		Map<String,Serializable> map = (Map<String, Serializable>) memCachedClient.get(getSessionId(response,request));
		if(map!=null){
			return map.get(name);
		}
		return null;
	}

	@Override
	public String getSessionId(HttpServletResponse response,HttpServletRequest request) {
		Cookie[] cookies=request.getCookies();
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(JSESSIONID)){
				return cookie.getValue();
			}
		}
		String sessionId=UUID.randomUUID().toString().replace("-", "");
		Cookie cookie=new Cookie(JSESSIONID, sessionId);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		response.addCookie(cookie);
		return sessionId;
	}

	@Override
	public void logout(HttpServletResponse response,HttpServletRequest request) {
		if(memCachedClient.keyExists(getSessionId(response,request))){
			memCachedClient.delete(getSessionId(response,request));
		}
	}
	
	public void setExpire(int expire) {
		this.expire = expire;
	}
}
