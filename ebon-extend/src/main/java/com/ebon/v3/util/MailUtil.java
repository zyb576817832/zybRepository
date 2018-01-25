package com.ebon.v3.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.ebon.app.service.user.vo.User;
import com.ebon.v3.dto.VAppUserTaskPlatfrom;


/**
 * 客户环境不需要身份认证
 * @author ebon-11
 *
 */
public class MailUtil {
	

	private static String mailTranProtocol = "smtp";
	//private static String mailSmtpAuth = "true"; 去掉身份认证
	private static String mailSubject = "uPMS Task Centre - ";
	
	
	private static MailUtil mailUtil;
	
	private MailUtil(){};
	
	public static MailUtil getInstance(){
		if(mailUtil == null){
			mailUtil = new MailUtil();
		}
		return mailUtil;
	}
	
	 
	public void sendMail(User toUser,User fromUser, VAppUserTaskPlatfrom task, String mailHost, String mailFrom, String maileEnd, String serveHost) throws Exception{
		//系统属性        
		Properties prop = new Properties();
		//设置SMTP主机 
		prop.setProperty("mail.host", mailHost);
		//传输的协议
		prop.setProperty("mail.transport.protocol", mailTranProtocol);
		//身份认证
		//prop.setProperty("mail.smtp.auth", mailSmtpAuth);
		//创建session
		Session session = Session.getDefaultInstance(prop, null);
		//Session session = Session.getInstance(prop);
		//通过session得到transport对象
		//Transport ts = session.getTransport();
		//使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
		//ts.connect(mailHost, mailFrom, mailFromPassword);
		//创建邮件
		MimeMessage message = new MimeMessage(session);
		//发件人
		message.setFrom(new InternetAddress(mailFrom));
		//收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toUser.getUserName()+maileEnd));
		//邮件标题
		message.setSubject(mailSubject + " Remind by " + fromUser.getActualName());  
		
		if("1".equals(task.getStatus())){
			task.setStatus("New");
		}else if("2".equals(task.getStatus())){
			task.setStatus("Running");
		}else if("3".equals(task.getStatus())){
			task.setStatus("Review");
		}else if("4".equals(task.getStatus())){
			task.setStatus("Finish");
		}else if("5".equals(task.getStatus())){
			task.setStatus("Cancel");
		}
		
		MimeBodyPart text = new MimeBodyPart();
		String url = serveHost + "/uPMS/v3/app/menu/getMainPage?v3Language=en_US&userId="+toUser.getEmployeeId();
		text.setContent("<html lang='zh-CN'><head ><meta charset='utf-8'>"
				+ "</head><body>"
				+ "&nbsp;Mr./Ms.&nbsp;" + toUser.getActualName() + "</br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;你有一条任务You have a task in uPMS</br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;项目号Project code:&nbsp;&nbsp;" + task.getProjCode() + "</br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;任务状态Task status:&nbsp;&nbsp;" + task.getStatus() + "</br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;任务名称Task name:&nbsp;&nbsp;" + task.getName() + "</br>"
				+ "</br>"
				+ "请及时处理Please handle in time."
				//客户的邮件系统会识别不了
				+ "<a href='"+url +"'>点击进入Click here.</a>"
				+ "</body></html>", "text/html;charset=UTF-8");
		// 描述数据关系
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.setSubType("related");
		message.setContent(mm);
		message.saveChanges();
		//发送邮件
		Transport.send(message);
		//关闭
		//ts.close();
	}
}
