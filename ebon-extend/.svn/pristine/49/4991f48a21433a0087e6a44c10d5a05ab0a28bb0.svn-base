package com.ebon.app.project.service;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.project.IProjectService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class ProjectServiceTest extends BaseTest {
	
	@Autowired
	private IProjectService	projectService;
	
	@Test
	public void testGetProjectInfo() {
		Map<String, Object> map = null;
		try {
			map = projectService.getProjectInfoById("991/CTSXX.1");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println("map = " + map);
	}
	
	@Test
	public void testGetProjectInfoById() {
		try {
			Map<String, Object> map = projectService.getProjectInfoById("projectNo01");
			System.out.println(map);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public IProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
}
