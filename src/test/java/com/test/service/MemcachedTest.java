package com.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.danga.MemCached.MemCachedClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext*.xml"})
public class MemcachedTest {
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	@Test
	public void test01(){
		memCachedClient.set("name", "xianggaoxiong", 0);
		System.out.println(memCachedClient.get("name"));
	}
}
