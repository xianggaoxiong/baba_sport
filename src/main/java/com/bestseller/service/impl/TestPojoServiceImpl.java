package com.bestseller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestseller.mapper.TestPojoMapper;
import com.bestseller.pojo.TestPojo;
import com.bestseller.service.TestPojoService;
@Service
@Transactional(readOnly=true)
public class TestPojoServiceImpl implements TestPojoService {
	
	@Autowired
	private TestPojoMapper testPojoMapper;
	
	@Override
	@Transactional
	public void savePojo(TestPojo testPojo) {
		testPojoMapper.saveTestPojo(testPojo);
	}

}
