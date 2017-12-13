package com.ebon.job.cpc.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;

public class CopyOfHumanPlanResourceTest extends BaseTest {
	
	@Autowired
	private IHumanPlanResource humanPlanResource;

	public IHumanPlanResource getHumanPlanResource() {
		return humanPlanResource;
	}

	public void setHumanPlanResource(IHumanPlanResource humanPlanResource) {
		this.humanPlanResource = humanPlanResource;
	}
	
//	@Test
//	public void testSumResourcePlantime() {
//		
//		System.out.println(humanPlanResource);
//		
//		long start = System.currentTimeMillis();
//		humanPlanResource.sumResourcePlantime();
//		System.out.println(System.currentTimeMillis() - start);
//	}
	
	
	@Test
	public void test() {
		
		System.out.println(this.applicationContext);
		
		System.out.println("4545");
	}
	
	
}
