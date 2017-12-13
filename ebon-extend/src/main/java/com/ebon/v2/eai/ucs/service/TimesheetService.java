package com.ebon.v2.eai.ucs.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.springframework.stereotype.Component;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.util.Config;
import com.ebon.v2.eai.base.handler.BaseHandler;
import com.ebon.v2.eai.base.model.BatchInfo;
import com.ebon.v2.eai.base.model.Command;
import com.ebon.v2.eai.base.service.BaseEaiService;
import com.ebon.v2.eai.ucs.handler.UcsHandler;
import com.ebon.v2.eai.ucs.model.Employee;
import com.ebon.v2.eai.ucs.model.Timesheet;
import com.ebon.v2.eai.ucs.model.TimesheetItem;
import com.ekingwin.webservice.UCSTimesheetService_PortType;
import com.ekingwin.webservice.UCSTimesheetService_Service;
import com.ekingwin.webservice.UCSTimesheetService_ServiceLocator;

@Component
public class TimesheetService extends BaseEaiService {

	@Config("rpt.ucsTimesheetWebServiceUrl")
	String ucswsurl;

	@Override
	public BaseHandler getHandler() {
		return new UcsHandler();
	}

	@Override
	public void doSave(BatchInfo batchInfo) throws DaoException {
		Timesheet timesheet = (Timesheet) batchInfo;
		// 删除人员报工表中，startdate之后的数据，在插入数据
		myBatisDao.delete("V2Mapper.delTimesheetByStartDate", timesheet.getStartDate());
		// 保存人员报工信息,保证人员报工为最新的。最近时间的。
		List<TimesheetItem> items = timesheet.getItems();
		for (TimesheetItem timesheetItem: items){
			//插入人员报工数据
			myBatisDao.save("V2Mapper.insertRptTimesheetItem", timesheetItem);
		}
	}

	@Override
	public void saveBatchInfo(BatchInfo batchInfo, Command command)	throws DaoException {

		Timesheet timesheet = (Timesheet) batchInfo;
		timesheet.setCommandId(command.getId());
		//插入同步报工指令
		myBatisDao.save("V2Mapper.insertTimesheet", timesheet);
		List<TimesheetItem> items = timesheet.getItems();
		for (TimesheetItem timesheetItem: items){
			//插入人员报工数据
			myBatisDao.save("V2Mapper.insertTimesheetItem", timesheetItem);
		}
	}
	
	@Override
	public String validate(BatchInfo batchInfo) {

		StringBuffer stringBuffer = new StringBuffer();
		Timesheet timesheet = (Timesheet) batchInfo;
		String dateMsg = "{0}({1})日期格式不符合\"YYYY-MM-DD\";\n";

		// 人员报工信息
		List<TimesheetItem> items = timesheet.getItems();
		for (TimesheetItem timesheetItem : items) {

			if(!this.isDate(timesheetItem.getDate())){
				String[] args = {"workdate",timesheetItem.getDate()+"--ProjectCode:"+timesheetItem.getProjectCode()+";EmployeeId:"+timesheetItem.getEmployeeId()};
				stringBuffer.append(this.buildErrorMsg(dateMsg,args ));
			}
		}

		return stringBuffer.toString();

//		// 当前系统时间
//		String currentDate = new SimpleDateFormat("yyyy-MM-dd")
//				.format(new Date());
//
//		/*
//		 * 1）如果存在“登记人”不在uPMS系统中的数据如何处理？ 处理：本条数据正常写入同步的数据表中。
//		 * 
//		 * 2）如果存在项目编号，不在uPMS系统中的数据如何处理？ 处理：本条数据入“ERROR库”，并通知相关人
//		 * 
//		 * 3) 时间格式，不符合"YYYY-MM-DD" 规范的则不入系统库 处理：本条数据入“ERROR库”，并通知相关人 4） date
//		 * 不在“startDate”之内的则不入系统库 处理：本条数据入“ERROR库”，并通知相关人
//		 * 
//		 * 5）人员报告信息中的“部门”、“成本中心”信息，以实际报工数据为准，主数据为参考
//		 * 
//		 * 6) 通知方式采用“邮件”的方式
//		 */
//
//		// 1)验证所有的登记人是否在系统中存在
//		// String isHaveEmplopyee = this.isHaveEmplopyee(eList);
//		// if("".equals(isHaveEmplopyee)){
//		// errorMsg+=isHaveEmplopyee+";";
//		// }
//
//		// 2)验证项目是否在系统中存在
//		// String isHaveProject = this.isHaveProject(items);
//		// if("".equals(isHaveProject)){
//		// errorMsg+=isHaveEmplopyee+";";
//		// }

	}

	/**
	 * 验证项目编号projectcode是否存UPMS系统中
	 * 
	 * @param items
	 * @return
	 */
	private String isHaveProject(List<TimesheetItem> items) {
		String result = "";

		// 数据库的操作......

		return result;
	}

	/**
	 * 登记人employeeId是否存在UPMS中
	 * 
	 * @param eList
	 * @return
	 */
	public String isHaveEmplopyee(List<Employee> eList) {

		String result = "";

		// 数据库的操作......

		return result;
	}

	@Override
	public byte[] getData( String startDate) throws Exception {
//		return this.getBytes("ucs.xml");
		
		UCSTimesheetService_Service ss = new UCSTimesheetService_ServiceLocator();
		UCSTimesheetService_PortType port;
		byte[] _getTimesheet__return = null;
		port = ss.getUCSTimesheetServiceImplPort(new URL(ucswsurl));
		_getTimesheet__return = port.getTimesheet(startDate);
		return _getTimesheet__return;
	}
	
	@Override
	public int getSys() {
		return Command.SYS_UCS;
	}

}
