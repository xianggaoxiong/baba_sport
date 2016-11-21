package com.bestseller.common;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.common.web.aop.MemCachedUtil;

public class CacheInterceptor {
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	public static final int TIMEOUT=3600;
	
	public int expiry=TIMEOUT;
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
		String key=getCacheKey(pjp);
		System.out.println(key);
		if(memCachedClient.stats().isEmpty()){
			System.out.println("memCached的服务连接异常！！！");
			return pjp.proceed();
		}
		if(null==memCachedClient.get(key)){
			Object proceed = pjp.proceed();
			memCachedClient.set(key, proceed,expiry);
		}
		return memCachedClient.get(key);
	}
	
	//数据库更新，清理缓存
	public void doAfter(JoinPoint jp){
		String backageName=jp.getTarget().getClass().getName();
		
		Map<String, Object> keySet = MemCachedUtil.getKeySet(memCachedClient);
		for(Entry<String,Object> entry:keySet.entrySet()){
			if(entry.getKey().startsWith(backageName)){
				memCachedClient.delete(entry.getKey());
			}
		}
	}
	
	public String getCacheKey(ProceedingJoinPoint pjp){
		StringBuilder sb=new StringBuilder();
		String packageName=pjp.getTarget().getClass().getName();
		sb.append(packageName);
		//方法名
		String methodName=pjp.getSignature().getName();
		sb.append("."+methodName);
		Object[] args = pjp.getArgs();
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		for(Object arg:args){
			StringWriter sw=new StringWriter();
			try {
				om.writeValue(sw, arg);
			}catch (Exception e) {
				e.printStackTrace();
			}
			sb.append(".").append(sw.toString());
		}
		return sb.toString();
	}
	
	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}
}
