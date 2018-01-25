package com.ebon.v3;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.user.vo.User;
import com.ebon.platform.BaseTest;
import com.ebon.platform.util.Config;
import com.ebon.v3.dto.VAppUserTaskPlatfrom;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.util.MailUtil;

public class SendMail extends BaseTest {
	
	@Autowired
	IAppUserTaskPlatfromService appUserTaskPlatfromService;
	
	@Config("v3.mailHost")
	String mailHost;
	@Config("v3.mailFrom")
	String mailFrom;
	@Config("v3.mailFromPassword")
	String mailFromPassword;
	@Config("v3.maileEnd")
	String maileEnd;
	@Config("v3.serveHost")
	String serveHost;
	
	@Test
	public void sendEamin() throws Exception{
		User toUser = appUserTaskPlatfromService.getUserById("3B6B6ED7CB524A1CA84F02E53470FB62");
		VAppUserTaskPlatfrom task = appUserTaskPlatfromService.getVById("F65D1D8F474A4750B831B4ED272610AC");
		MailUtil utile = MailUtil.getInstance();
		utile.sendMail(toUser,toUser, task, mailHost, mailFrom, maileEnd, serveHost);
	}

}
