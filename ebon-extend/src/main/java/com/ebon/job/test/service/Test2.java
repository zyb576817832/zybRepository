package com.ebon.job.test.service;

import org.springframework.stereotype.Service;

@Service
public class Test2 implements ITest2 {

	@Override
	public void test() {
		System.out.println("----test2----");
	}

}
