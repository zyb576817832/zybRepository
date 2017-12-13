package com.ebon.v2.eai.lims.webservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.dao.DaoException;
import com.ebon.v2.eai.base.BaseEaiServiceTest;

public class MainProjectServiceTest extends BaseEaiServiceTest{
	
	@Autowired(required=true)
	MainProjectService mainProjectService;
	
	@SuppressWarnings("unchecked")
	@Test
	public void getProjects() throws DaoException{
		String[] projectCodes = {"788/4415EN.2-ECU FI_24171","788/4415EN.2-ECU FI_29719"};
		byte[] data = this.mainProjectService.getMainProjectInfoes(projectCodes);
		System.out.println(new String(data));

	}
	

	

	
	

}
