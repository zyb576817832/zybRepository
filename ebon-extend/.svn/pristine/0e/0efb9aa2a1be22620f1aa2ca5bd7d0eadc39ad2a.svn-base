package com.ebon.v2.eai.base.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.util.Config;
import com.ebon.v2.eai.base.handler.BaseHandler;
import com.ebon.v2.eai.base.model.BatchInfo;
import com.ebon.v2.eai.base.model.Command;
import com.ebon.v2.eai.helper.MailBean;
import com.ebon.v2.eai.helper.MailHelper;

/**
 * 继承com.ebon.platform.service.BaseService，注入mybatisDao
 * 
 * @author G
 */
public abstract class BaseEaiService extends
		com.ebon.platform.service.BaseService {
	/**
	 * 下面的方法具有子类差异性。
	 * 
	 * @return
	 */
	public abstract BaseHandler getHandler();

	public abstract void doSave(BatchInfo batchInfo) throws DaoException;

	public abstract void saveBatchInfo(BatchInfo batchInfo, Command command)
			throws DaoException;

	public abstract String validate(BatchInfo batchInfo);

	String getExceptionStackTrace(Exception e) {
		e.printStackTrace();
		return e.getMessage();

	}

	/**
	 * 传入命令,获取数据,并保存
	 * 
	 * @param command
	 * @throws DaoException
	 */
	public String doCommand(Command command) throws DaoException {
		byte[] data = null;
		try {
			data = this.invokeWebService(command);
			command.setData(data);
			command.setStatus(1);
		} catch (Exception e) {
			String comments = "请求Web Service时报错";
			String description = this.getExceptionStackTrace(e);
			this.fillCommand(command, comments, description);
		}
		// 保存命令
		saveCommand(command);
		// 验证数据
		if (command.getData() != null) {
			BatchInfo batchInfo = null;
			try {		
				batchInfo = this.parse(data);
			} catch (Exception e) {
				e.printStackTrace();
				this.updateCommand(command,"解析XML时报错",this.getExceptionStackTrace(e));
				notifyAdmin(command);
			}
			if (batchInfo != null) {
				String errorMsg = this.validate(batchInfo);
				if ("".equals(errorMsg)) {
					try {
						saveBatchInfo(batchInfo, command);
						doSave(batchInfo);
					} catch (DaoException e) {
						e.printStackTrace();
						throw e;
					}
				}else{
					this.updateCommand(command,"校验数据未通过",errorMsg);
					notifyAdmin(command);
				}
			}
		} else {
			notifyAdmin(command);
		}
		return command.getId();
	}
	
	private void updateCommand(Command command,String comments,String description ) throws DaoException{
		this.fillCommand(command, comments, description);
		this.updateCommand(command);
	}
	
	private void fillCommand(Command command,String comments,String description ){
		command.setComments(comments);
		command.setDescription(description);
	}

	/**
	 * 通知管理员本次数据请求异常~
	 * 
	 * @param command
	 */
	private void notifyAdmin(Command command) {
		if(notifiable){
			this.doNotifyAdmin(command);
		}
	}
	
	private void doNotifyAdmin(Command command) {
		MailBean mailBean = this.buildMailBean(command);
		try {
			mailHelper.send(mailBean);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		System.out.println("数据请求异常：" + command.getDescription());
	}

	private MailBean buildMailBean(Command command) {
		MailBean mailBean = new MailBean();
		mailBean.setSubject(buildSubject(command));
		String[] toEmails = {mail};
		mailBean.setToEmails(toEmails );
		mailBean.setContent(this.buildContent(command));
		return mailBean;
	}
	
	String buildSubject(Command command){
		List args = new ArrayList();
		args.add(command.getSendedTime());
		args.add(command.getSys() == Command.SYS_LIMS ? "LIMS":"UCS" );
		args.add(command.TYPE_AUTO == Command.TYPE_AUTO ? "AUTO":"MANUL" );
		
		String msg = " Date:{0};Sys:{1};Type:{2} Failed";
		MessageFormat form = new MessageFormat(msg );
		return form.format(args.toArray());
	}
	
	String buildContent(Command command){
		List args = new ArrayList();
		args.add(command.getSendedTime());
		args.add(command.getSys() == Command.SYS_LIMS ? "LIMS":"UCS" );
		args.add(command.TYPE_AUTO == Command.TYPE_AUTO ? "AUTO":"MANUL" );
		args.add(command.getComments());
		args.add(command.getDescription());
		String msg = " Date:{0};Sys:{1};Type:{2} Failed ,Error Message as follow: \n {3} \n ({4})";
		MessageFormat form = new MessageFormat(msg );
		return form.format(args.toArray());
	}


	private void updateCommand(Command command) throws DaoException {
		myBatisDao.save("V2Mapper.updateCommand", command);// 报错点
	}

	/**
	 * 保存本次数据请求命令。保存的信息 包括：
	 * id,type,sys,data,startDate,status,endedTime
	 * 
	 * @param command
	 * @throws DaoException
	 */
	private void saveCommand(Command command) throws DaoException {
		// 在此保存的过程中应该把command的id获取，并且保存到command对象中！
		myBatisDao.save("V2Mapper.insertCommand", command);// 报错点
	}

	/**
	 * 将xml文件解析成数据对象
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	BatchInfo parse(byte[] data) throws IOException, SAXException,
			ParserConfigurationException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		// 获取子类的handler
		BaseHandler handler = this.getHandler();
		// 实例化myhandler里面的BatchInfo对象。
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(bais, handler);
		// 返回batchinfo对象。
		return handler.getBatchInfo();
	}

	/**
	 * 连接模拟的LIMS提供的接口,获取数据
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private byte[] invokeWebService(Command command) throws Exception {
		return this.getData(command.getStartDate());
	}

	public abstract byte[] getData(String startDate) throws Exception;

	public boolean isDate(String s) {
		boolean dateable = false;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.parse(s);
			dateable = true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
		}
		return dateable;
	}
	
	public String buildErrorMsg(String msg,String[] args){
		MessageFormat form = new MessageFormat(msg);
		return form.format(args);
	}
	
	public Command doAutoCommand() throws DaoException {
		return this.doCommand(Command.TYPE_AUTO,null);
	}

	public Command doManulCommand(String startDate) throws DaoException {
		return this.doCommand(Command.TYPE_MANUL,startDate);
	}
	
	/**
	 * 执行命令并返回命令结果
	 * @param type
	 * @param startDate
	 * @return
	 * @throws DaoException
	 */
	private Command doCommand(int type,String startDate) throws DaoException {
		Command command = new Command();
		command.setType(type);
		command.setSys(this.getSys());
		if(type==1){
			command.setStartDate(startDate);
		}else{
			command.setStartDate(this.getStratDate());
		}
		this.doCommand(command);
		return command;
	}
	
	public abstract int getSys();
	
	
	public String getStratDate() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.add(java.util.Calendar.MONTH, -1);
		calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	@Autowired
	MailHelper mailHelper;
	
	@Config("admin.mail")
	String mail;
	

	@Config("admin.notifiable")
	boolean notifiable;
	
	public byte[] getBytes(String filename) {
		byte[] bytes = null;
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
			bytes = new byte[fis.available()];
			fis.read(bytes);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	
}
