package com.ebon.job.cpc.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;

public class HumanPlanResourceTest extends BaseTest {
	
	@Autowired
	private IHumanPlanResource humanPlanResource;

	public IHumanPlanResource getHumanPlanResource() {
		return humanPlanResource;
	}

	public void setHumanPlanResource(IHumanPlanResource humanPlanResource) {
		this.humanPlanResource = humanPlanResource;
	}
	
	@Test
	public void testSumResourcePlantime() {
		long start = System.currentTimeMillis();
		humanPlanResource.sumResourcePlantime();
		System.out.println(System.currentTimeMillis() - start);
	}
	

}
