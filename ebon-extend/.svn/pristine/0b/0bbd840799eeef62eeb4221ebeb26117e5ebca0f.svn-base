package com.ebon.v2.eai.lims.service;

import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.util.Config;
import com.ebon.v2.eai.base.handler.BaseHandler;
import com.ebon.v2.eai.base.model.BatchInfo;
import com.ebon.v2.eai.base.model.Command;
import com.ebon.v2.eai.base.service.BaseEaiService;
import com.ebon.v2.eai.lims.handler.LimsHandler;
import com.ebon.v2.eai.lims.model.Usage;
import com.ebon.v2.eai.lims.model.UsageItem;
import com.starlims.www.webservices.GenericServices;
import com.starlims.www.webservices.GenericServicesLocator;
import com.starlims.www.webservices.GenericServicesSoap;

@Component
public class LimsUsageService extends BaseEaiService{
	
	@Config("rpt.limsTimesheetWebServiceUrl")
	String limsTimesheetWebServiceUrl;
	
	@Config("rpt.limsTimesheetWebServiceUsername")
	String limsTimesheetWebServiceUsername;
	
	@Config("rpt.limsTimesheetWebServicePwd")
	String limsTimesheetWebServicePwd;

	@Override
	public BaseHandler getHandler() {
		return new LimsHandler();
				
	}
	
	@Override
	public void saveBatchInfo(BatchInfo batchInfo, Command command) throws DaoException {
		Usage usage = (Usage)batchInfo;
		usage.setCommandId(command.getId());	
		//插入同步报工指令
		myBatisDao.save("V2Mapper.insertUsage", usage);
		//插入设备主数据
		if(usage.getEquipments().size()>0){
			myBatisDao.save("V2Mapper.insertEquipments", usage.getEquipments());
		}		
		//插入设备报工数据
		if(usage.getItems().size()>0){
			myBatisDao.save("V2Mapper.insertUsageItems", usage.getItems());
		}		
	}

	@Override
	public void doSave(BatchInfo batchInfo) throws DaoException {
		Usage usage = (Usage)batchInfo;		
		//删除成本归集表中的设备工时，startdate之后的数据，在插入数据
		myBatisDao.delete("V2Mapper.delUsageItemByStartDate", usage.getStartDate());		
		//保存成本归集表中的设备设备工时,保证设备属性为最新的。最近时间的。
		if(usage.getItems().size()>0){
			myBatisDao.save("V2Mapper.insertRptUsageItems", usage.getItems());			//插入设备报工信息
		}		
	}

	@Override
	public String validate(BatchInfo batchInfo) {
		StringBuffer stringBuffer = new StringBuffer();
		Usage usage = (Usage)batchInfo;
		List<UsageItem> items = usage.getItems();
		String dateMsg = "{0}({1})日期格式不符合\"YYYY-MM-DD\";\n";
		
		/**
		 * 		当前只校验日期格式
		 */
		
		if(!this.isDate(batchInfo.getStartDate())){
			String[] args = {"startDate",batchInfo.getStartDate()};
			stringBuffer.append(this.buildErrorMsg(dateMsg,args ));
		}
//		
		//单条数据的验证
		for(UsageItem usageItem:items){
			if(!this.isDate(usageItem.getDate())){
				String[] args = {"workdate",usageItem.getDate()+"--ProjectCode:"+usageItem.getProjectCode()+";EquipmentCode:"+usageItem.getEquipmentCode()};
				stringBuffer.append(this.buildErrorMsg(dateMsg,args ));
			}
		}
		/**
		 * 项目编号所对应的项目信息必须在uPMS中，不能存在有“设备报工”无“项目信息的情况”
		 * date数据日期不能超过当前系统时间，即不能出现未来的时间数据
		 * date报工日期需为startdate之后日期
		 */
		//当前系统时间
//		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//		//1)验证项目编号存在系统中。
//		String isHaveProject = this.isHaveProject(items);
//		if("".equals(isHaveProject)){
//			errorMsg+=isHaveProject+";";
//		}
		return stringBuffer.toString();
	}
	
	/**
	 * 验证项目编号projectcode是否存UPMS系统中
	 * @param items
	 * @return
	 */
	private String isHaveProject(List<UsageItem> items) {
		String result = "";
		
		//数据库的操作......
		
		
		return result;
	}
	

	@Override
	public byte[] getData(String startDate) throws Exception{
		Object[] lst = null;
		byte[] rst = null;
		boolean okay = false;
		GenericServices genericServices = new GenericServicesLocator();
		GenericServicesSoap soap = genericServices.getGenericServicesSoap(new URL(limsTimesheetWebServiceUrl));
		lst = (Object[])soap.runActionDirect("WebServices.GetProjectsWorkTime",
				new Object[]{startDate}, limsTimesheetWebServiceUsername, limsTimesheetWebServicePwd);
		if (lst.length > 0){
			okay = lst[0].toString().equalsIgnoreCase("true");
			if (okay){
				String xml = (String)lst[1];
				// 发现LIMS厂商提供的XML数据不是UTF-8格式，这里做一个转化
				rst = xml.getBytes("UTF-8");
			}
		}
		return rst;
	}
	
	@Override
	public int getSys() {
		return Command.SYS_LIMS;
	}
}
