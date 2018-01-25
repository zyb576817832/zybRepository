package com.ebon.v3.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.app.service.user.vo.User;
import com.ebon.framework.util.StringUtil;
import com.ebon.framework.vo.Result;
import com.ebon.platform.action.BaseAction;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.util.Config;
import com.ebon.platform.util.Guid;
import com.ebon.platform.vo.CusGridData;
import com.ebon.v3.dto.VAppUserTaskPlatfrom;
import com.ebon.v3.service.IAppUserHoursService;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.util.ExcelUtil;
import com.ebon.v3.util.MailUtil;
import com.ebon.v3.vo.AppUserTaskPlatfrom;

@Controller
@RequestMapping("/app/userTaskPlatfrom")
public class AppUserTaskPlatformController extends BaseAction {
	
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
	
	@Autowired
	IAppUserTaskPlatfromService appUserTaskPlatfromService;
	
	@Autowired
	private IAppUserHoursService appUserHoursService;
	
	@RequestMapping("/getAllByUserId")
	@ResponseBody
	public CusGridData<VAppUserTaskPlatfrom> getAllByUserId(VAppUserTaskPlatfrom query,Page page){
		
		if(StringUtil.isNotNull(query.getDepts())){
			String[] arrayDeptNo = query.getDepts().split(",");
			query.setUserId("");
			query.setArrayDeptNo(arrayDeptNo);
		}
		if(StringUtil.isNotNull(query.getArrayPriority())){//优先级
			String[] array = query.getArrayPriority()[0].split(",");
			query.setArrayPriority(array);
		}
		if(StringUtil.isNotNull(query.getArraySerious())){//严重程度
			String[] array = query.getArraySerious()[0].split(",");
			query.setArraySerious(array);
		}
		if(StringUtil.isNotNull(query.getArraystatus())){//状态
			String[] array = query.getArraystatus()[0].split(",");
			query.setArraystatus(array);
		}
		if(StringUtil.isNotNull(query.getArrayType())){//任务类型
			String[] array = query.getArrayType()[0].split(",");
			query.setArrayType(array);
		}
		if(StringUtil.isNotNull(query.getArrayProjSize())){//项目大小
			String[] array = query.getArrayProjSize()[0].split(",");
			query.setArrayProjSize(array);
		}
		List<VAppUserTaskPlatfrom> list = appUserTaskPlatfromService.getAllByUserId(query, page);
		CusGridData<VAppUserTaskPlatfrom> gridData = new CusGridData<VAppUserTaskPlatfrom>(page, list);
		return gridData;
	}
	
	@RequestMapping("/getAllBySearchName")
	@ResponseBody
	public CusGridData<VAppUserTaskPlatfrom> getAllBySearchName(String userId, String searchName, Page page){
		
		List<VAppUserTaskPlatfrom> list = appUserTaskPlatfromService.getAllBySearchName(userId, searchName, page);
		CusGridData<VAppUserTaskPlatfrom> gridData = new CusGridData<VAppUserTaskPlatfrom>(page, list);
		return gridData;
	}

	@RequestMapping("/getVList")
	@ResponseBody
	public CusGridData<VAppUserTaskPlatfrom> getVList(VAppUserTaskPlatfrom query, Page page){
		if(StringUtil.isNotNull(query.getDepts())){//部门
			String[] arrayDeptNo = query.getDepts().split(",");
			query.setAssginUser("");
			query.setRespUser("");
			query.setArrayDeptNo(arrayDeptNo);
		}
		if(StringUtil.isNotNull(query.getArrayPriority())){//优先级
			String[] array = query.getArrayPriority()[0].split(",");
			query.setArrayPriority(array);
		}
		if(StringUtil.isNotNull(query.getArraySerious())){//严重程度
			String[] array = query.getArraySerious()[0].split(",");
			query.setArraySerious(array);
		}
		if(StringUtil.isNotNull(query.getArraystatus())){//状态
			String[] array = query.getArraystatus()[0].split(",");
			query.setArraystatus(array);
		}
		if(StringUtil.isNotNull(query.getArrayType())){//任务类型
			String[] array = query.getArrayType()[0].split(",");
			query.setArrayType(array);
		}
		List<VAppUserTaskPlatfrom> list = appUserTaskPlatfromService.getVList(query, page);
		CusGridData<VAppUserTaskPlatfrom> gridData = new CusGridData<VAppUserTaskPlatfrom>(page, list);
		return gridData;
	}
	
	/**
	 * 下拉框的返回结果
	 * 在前台没法对分页区分
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping("/getVListForCombo")
	@ResponseBody
	public List<VAppUserTaskPlatfrom> getVListForCombo(VAppUserTaskPlatfrom query, Page page){
		List<VAppUserTaskPlatfrom> list = appUserTaskPlatfromService.getVList(query, page);
		return list;
	}
	
	@RequestMapping("/getComboListBySearch")
	@ResponseBody
	public List<User> getComboListBySearch(String search){
		List<User> list = null;
		if(StringUtil.isNotNull(search)){
			list = appUserTaskPlatfromService.getComboListBySearch(search);
		}
		return list;
	}
	
	/**
	 * 
	 * @param insert
	 * @param userId 当前用户
	 * @return
	 */
	@RequestMapping("/insertChild")
	@ResponseBody
	public Result insertChild(AppUserTaskPlatfrom insert, String userId){
		AppUserTaskPlatfrom  parentData = appUserTaskPlatfromService.getById(insert.getParentId());
		//页面的属性
		parentData.setParentId(insert.getParentId());
		parentData.setName(insert.getName());
		parentData.setRespUser(insert.getRespUser());
		parentData.setBaseStartDate(insert.getBaseStartDate());
		parentData.setBaseEndDate(insert.getBaseEndDate());
		parentData.setPlanHours(insert.getPlanHours());
		//设置发包人
		parentData.setAssginUser(userId);
		//设置新Id insert语句统一设置ID Useless
		parentData.setId(Guid.getGuid());
		//设置新状态
		parentData.setStatus("1");
		//设置新类型
		parentData.setType("4");
		appUserTaskPlatfromService.add(parentData);
		
		//更新父任务属性
		Result result = appUserTaskPlatfromService.updateChildCount(insert.getParentId());
		return result;
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String userId, String id){
		//更新父任务属性
		Result result = appUserTaskPlatfromService.delete(id, userId);
		return result;
		
	}
	
	/**
	 * 用于更新计划工时
	 * @param update
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(AppUserTaskPlatfrom update){
		Result result = appUserTaskPlatfromService.update(update);
		return result;
		
	}
	
	
	@RequestMapping("/remindTask")
	@ResponseBody
	public Result remindTask(String id, String type, String userId) throws Exception{
		VAppUserTaskPlatfrom task = appUserTaskPlatfromService.getVById(id);
		String toUserId = "";
		if("1".equals(type)){
			toUserId = task.getRespUser();
		}else{
			toUserId = task.getAssginUser();
		}
		User toUser = appUserTaskPlatfromService.getUserById(toUserId);
		User fromUser = appUserTaskPlatfromService.getUserById(userId);
		MailUtil utile = MailUtil.getInstance();
		utile.sendMail(toUser,fromUser, task, mailHost, mailFrom, maileEnd, serveHost);
		return new Result();
		
	}
	
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Result changeStatus(String id, String status, String userId){
		Result result = appUserTaskPlatfromService.changeStatus(id,  status, userId);
		return result;
	}
	@RequestMapping("/export")
	public void export(Page page,VAppUserTaskPlatfrom query,HttpServletResponse response) throws Exception{
		
		if(StringUtil.isNotNull(query.getDepts())){
			String[] arrayDeptNo = query.getDepts().split(",");
			query.setUserId("");
			query.setAssginUser("");
			query.setRespUser("");
			query.setArrayDeptNo(arrayDeptNo);
		}
		if(StringUtil.isNotNull(query.getArrayPriority()[0])){//优先级
			String[] array = query.getArrayPriority()[0].split(",");
			query.setArrayPriority(array);
		}else{
			query.setArrayPriority(null);
		}
		
		if(StringUtil.isNotNull(query.getArraySerious()[0])){//严重程度
			String[] array = query.getArraySerious()[0].split(",");
			query.setArraySerious(array);
		}else{
			query.setArraySerious(null);
		}
		
		if(StringUtil.isNotNull(query.getArraystatus()[0])){//状态
			String[] array = query.getArraystatus()[0].split(",");
			query.setArraystatus(array);
		}else{
			query.setArraystatus(null);
		}
		if(StringUtil.isNotNull(query.getArrayType()[0])){//任务类型
			String[] array = query.getArrayType()[0].split(",");
			query.setArrayType(array);
		}else{
			query.setArrayType(null);
		}
		if(StringUtil.isNotNull(query.getArrayProjSize()[0])){//项目大小
			String[] array = query.getArrayProjSize()[0].split(",");
			query.setArrayProjSize(array);
		}else{
			query.setArrayProjSize(null);
		}
		
		List<VAppUserTaskPlatfrom> data = appUserTaskPlatfromService.getAllByUserIdExport(query);
		
		for(VAppUserTaskPlatfrom vAppUserTaskPlatfrom:data){
			
			String value = vAppUserTaskPlatfrom.getStatus();
			if(StringUtil.isNotNull(value)){
				if(value.equals("1")){
					vAppUserTaskPlatfrom.setStatus("未开始");
				}else if(value.equals("2")){
					vAppUserTaskPlatfrom.setStatus("已开始");
				}else if(value.equals("3")){
					vAppUserTaskPlatfrom.setStatus("审批中");
				}else if(value.equals("4")){
					vAppUserTaskPlatfrom.setStatus("已完成");
				}else if(value.equals("5")){
					vAppUserTaskPlatfrom.setStatus("已取消");
				}
			}
			String type = vAppUserTaskPlatfrom.getType();
			if(StringUtil.isNotNull(type)){
				if(type.equals("1")){
					vAppUserTaskPlatfrom.setType("计划任务");
				}else if(type.equals("2")){
					vAppUserTaskPlatfrom.setType("风险任务");
				}else if(type.equals("3")){
					vAppUserTaskPlatfrom.setType("OPL任务");
				}else if(type.equals("4")){
					vAppUserTaskPlatfrom.setType("分解任务");
				}
			}
		}
		CusGridData<VAppUserTaskPlatfrom> gridData = new CusGridData<VAppUserTaskPlatfrom>(data);
		
		List<Map<String,Object>> list = gridData.getRows();
		String fileName = java.net.URLEncoder.encode("全部任务", "UTF-8");
		
		List<String> heads = new ArrayList<String>();
		//excel头信息
		String[] arrayHeads = {"来源","任务名称","责任人","责任部门","要求开始时间","要求结束时间","实际开始时间","实际结束时间","计划工时","实际工时","类型","状态","发包人","发包人所属","创建人","创建时间","修改人","修改时间","子任务完成个数","子任务总个数","项目编号","PH1","PH2","0 Number","B Number","SOP","项目大小","车型","发动机","客户零件号"};
		for (String string : arrayHeads) {
			heads.add(string);
		}
		List<String> keys = new ArrayList<String>();
		//对应值
		String arrayKeys[] = {"SOURCE","NAME","RESP_USER_NAME","RESP_DEPT","BASE_START_DATE","BASE_END_DATE","ACT_START_DATE","ACT_END_DATE","PLAN_HOURS","ACT_HOURS","TYPE","STATUS","ASSIGN_USER_NAME","ASSIGN_DEPT","ASSIGN_USER_NAME","CREATE_TIME","UPDATE_USER","UPDATE_TIME","CHILD_UNDO","CHILD_SUM","PROJ_CODE","APP_PROJ_INFO_PH1","APP_PROJ_INFO_PH2","APP_PROJ_INFO_PROD_OCODE","APP_PROJ_INFO_PROD_BCODE","APP_PROJ_INFO_SOP","APP_PROJ_INFO_PROJ_SIZE","APP_PROJ_INFO_VEHICLE","APP_PROJ_INFO_ENGINE","APP_PROJ_INFO_CUSTOMER_NUMBER"};
		for (String string : arrayKeys) {
			keys.add(string);
		}
		//创建excel
		XSSFWorkbook wb = ExcelUtil.createXSSFWorkbook(list, "list", heads, keys);
		
		ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        wb.write(baos);

		response.setContentType("application/x-excel");  
        response.setCharacterEncoding("UTF-8");  
        response.setHeader("Content-Disposition", "attachment; filename="+ fileName +".xlsx");  
        response.setHeader("Content-Length",String.valueOf(baos.size()));  
    
        OutputStream os = new BufferedOutputStream(response.getOutputStream()); 
        os.write( baos.toByteArray()); 
        os.flush(); 
        os.close();
	}
	
	//每个部门领导看到的部门结构
	private static Map<String, List<VAppUserTaskPlatfrom>> deptTreeCombox = new HashMap<String, List<VAppUserTaskPlatfrom>>();
	/**
	 * 初始化部门检索的下拉框
	 * @param userId
	 * @return
	 */
	@RequestMapping("deptComboxTree")
	@ResponseBody
	public List<VAppUserTaskPlatfrom>  deptComboxTree(String userId){
		
		List<VAppUserTaskPlatfrom> combox = new ArrayList<VAppUserTaskPlatfrom>();
		if(userId.equals("95A62E63-07EA-BCF1-00A2-0982C0A80115")){//管理员
			//管理员获取为全部部门
			if(StringUtil.isNotNull(deptTreeCombox.get("admin"))){
				combox = deptTreeCombox.get("admin");
			}else{
				combox=appUserTaskPlatfromService.getManagerDept();
				deptTreeCombox.put("admin", combox);
			}
			
		}else{
			VAppUserTaskPlatfrom v=appUserTaskPlatfromService.getVByUserId(userId);
			if(StringUtil.isNotNull(v)&&v.getIsLeader().equals("1")){//领导
				//领导人根据自己所在部门获取自身及下属部门
				if(StringUtil.isNotNull(deptTreeCombox.get(v.getDeptNo()))){
					combox = deptTreeCombox.get(v.getDeptNo());
				}else{
					combox = appUserTaskPlatfromService.getLeaderDept(v.getDeptNo());
					deptTreeCombox.put(v.getDeptNo(), combox);
				}
				
			}else{//普通员工暂时先不做操作
				
			}
		}
		return combox;
	}
	
	public static void main(String[] args) {
		/*List<String> list = new ArrayList<String>();
		list.add("1.02");
		list.add("1.91");
		list.add("1.0622");
		list.add("1.04");
		BigDecimal bs = new BigDecimal(list.get(0));
		String totalHours = "";
		for (int i = 1; i < list.size(); i++) {
			bs = bs.add(new BigDecimal(list.get(i)));
		}
		
		BigDecimal bsTest = new BigDecimal("1.02");
		bsTest.add(new BigDecimal("1.01"));
		System.out.println("相加完原来的对象不变"+bsTest.toString());//1.02
		System.out.println("相加完返回的新对象值会变"+ bsTest.add(new BigDecimal("1.01")).toString());//2.03
		
		totalHours = bs.toString();
		System.out.println(totalHours);*///5.0322
		
		
	}
	
	

}
