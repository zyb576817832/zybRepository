package com.ebon.v3;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ebon.app.service.user.vo.User;
import com.ebon.platform.BaseTest;
import com.ebon.platform.dao.pager.Page;
import com.ebon.v3.dto.VAppUserTaskPlatfrom;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.vo.AppUserTaskPlatfrom;

//设置为不会滚
@TransactionConfiguration(defaultRollback=false)
public class UserTaskPlatform extends BaseTest {
	
	@Autowired
	IAppUserTaskPlatfromService appUserTaskPlatfromService;
	
	
	/**
	 * 如果为子节点需要传入parentID
	 * 如果为项目则设置项目Id如果为OPL则设置oplId
	 * 其他字段必须必填
	 */
	@Test
	public void add(){
		AppUserTaskPlatfrom app = new AppUserTaskPlatfrom();
		app.setName("初始化测试任务004");
		app.setType("1");
		app.setStatus("1");
		
		app.setProjId("ba99d780-866a-4a89-93f9-8bb91fffbfbf");
		//或者OPL
		
		app.setBaseStartDate("2017-10-11");
		app.setBaseEndDate("2017-11-20");
		app.setPlanHours("100");
		
		app.setAssginUser("10A40FD049E74915BD615B6AAFBF1DC7");//jinren.mei01
		app.setRespUser("DCAB10FF4A3E43768270A2DB02402C2A");//lei.xu01
		
		app.setCreateUser("10A40FD049E74915BD615B6AAFBF1DC7");//jinren.mei01
		
		
		appUserTaskPlatfromService.add(app);
		
	}
	
	@Test
	public void update(){
		AppUserTaskPlatfrom update = new AppUserTaskPlatfrom();
		update.setId("6AE38954F03548D9B637788A0791FEEC");
		appUserTaskPlatfromService.update(update);
		
		appUserTaskPlatfromService.changeStatus("6AE38954F03548D9B637788A0791FEEC", "4");
	}
	
	@Test
	public void getList(){
		appUserTaskPlatfromService.getById("6AE38954F03548D9B637788A0791FEEC");
		
		VAppUserTaskPlatfrom query = new VAppUserTaskPlatfrom();
		
		appUserTaskPlatfromService.getVList(query, new Page());
		
		query.setUserId("AC48BC7041464DB892FB4DBFB2652ED3");
		appUserTaskPlatfromService.getAllByUserId(query, new Page());
		
		
		
		
		List<User> users = appUserTaskPlatfromService.getComboListBySearch("");
		System.out.println(users.size());
		
	}
	
	@Test
	public void getProj(){
		appUserTaskPlatfromService.getProjById("ba0a6ec7-eea7-46cb-9a11-805425d5b42a");
	}

}
