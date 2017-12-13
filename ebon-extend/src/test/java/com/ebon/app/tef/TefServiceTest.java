package com.ebon.app.tef;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.tef.ITefService;
import com.ebon.app.service.tef.vo.TefInfo;
import com.ebon.platform.BaseTest;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.ServiceException;

public class TefServiceTest extends BaseTest {
	
	@Autowired
	private ITefService	tefService;
	
	@Test
	public void testGetPageList() {
		Page page = new Page();
		TefInfo info = new TefInfo();
		List<TefInfo> list = null;
		try {
			list = tefService.getPageList(page, info);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		Assert.assertNotNull(list);
	}
	
	public ITefService getTefService() {
		return tefService;
	}
	
	public void setTefService(ITefService tefService) {
		this.tefService = tefService;
	}
	
}
