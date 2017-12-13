package com.ebon.job.test.service;

import org.springframework.stereotype.Service;

import com.ebon.platform.service.BaseService;

@Service
public class Test extends BaseService implements ITest {

	@Override
	public void test() {
		System.out.println("----test----");
	}

}
