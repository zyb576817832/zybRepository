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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.framework.util.StringUtil;
import com.ebon.framework.vo.Result;
import com.ebon.platform.action.BaseAction;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.vo.CusGridData;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.service.IMileStoneService;
import com.ebon.v3.util.ExcelUtil;
import com.ebon.v3.vo.AppProjInfo;
import com.ebon.v3.vo.MileStone;

@Controller
@RequestMapping("/app/mileStone")
public class AppMileStoneController extends BaseAction{
	@Autowired
	private IMileStoneService mileStoneService;
	@Autowired
	private IAppUserTaskPlatfromService appUserTaskPlatfromService;
	
	
	/**
	 * 遍历里程碑数据
	 * @param projId
	 * @param version
	 * @return
	 */
	@RequestMapping("getListData")
	@ResponseBody
	public CusGridData<MileStone> getListData(String projId,String version,String platformFlag,Page page){
		
		Map<String,String> map = new HashMap<String,String>();
		List<MileStone> list = null;
		if(StringUtil.isNotNull(platformFlag)){//个人中心工作平台里程碑
			map.put("projId", projId);
			String v=mileStoneService.maxBaseLine(map);
			map.put("version", v);
			list = mileStoneService.maxBaseLineData(map, page);
		}else{//里程碑tab
			map.put("projId", projId);
			if(StringUtil.isNotNull(version)){
				map.put("version", version);
			}
			list = mileStoneService.maxBaseLineData(map,page);
		}
		CusGridData<MileStone> gridData = new CusGridData<MileStone>(page, list);
		return gridData;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Result mileStoneAdd(MileStone mileStone){
		String projId = mileStone.getProjId();
		AppProjInfo appProjInfo = appUserTaskPlatfromService.getProjById(projId);
		if(StringUtil.isNotNull(appProjInfo.getPrimProjId())){//默认为主项目添加，如果条件成立执行子项目添加
			mileStone.setChildId(projId);
			mileStone.setProjId(appProjInfo.getPrimProjId());
		}
		Result result = mileStoneService.add(mileStone);
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Result mileStoneDelete(String id){
		
		Result result = mileStoneService.deleteById(id);
		return result;
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result saveBaseLine(String projId,String userId,Page page){
		
		Result result = null;
		//获取该项目对应的系统项目Id
		AppProjInfo appProjInfo = appUserTaskPlatfromService.getProjById(projId);
		Map<String,String> map = new HashMap<String, String>();
		List<MileStone> list = null;
		
		if(StringUtil.isNotNull(appProjInfo.getPrimProjId())){//零部件
			map.put("projId", appProjInfo.getPrimProjId());
			map.put("childId", projId);
			list = mileStoneService.maxChildBaseLineData(map,page);
			String currentLine = mileStoneService.maxBaseLine(map);
			if(StringUtil.isNotNull(currentLine)){
				currentLine = String.valueOf(Integer.parseInt(currentLine)+1);
			}else{
				currentLine = "1";//初始值
			}
			for(MileStone mileStone:list){
				mileStone.setUpdateUser(userId);//初始化创建人
				mileStone.setVersion(currentLine);
			}
			result=mileStoneService.saveChildBaseLine(list,appProjInfo.getPrimProjId(),projId );
		}else{//主项目
			map.put("projId", projId);
			list = mileStoneService.maxBaseLineData(map,page);
			String currentLine = mileStoneService.maxBaseLine(map);
			if(StringUtil.isNotNull(currentLine)){
				currentLine = String.valueOf(Integer.parseInt(currentLine)+1);
			}else{
				currentLine = "1";//初始值
			}
			for(MileStone mileStone:list){
				mileStone.setUpdateUser(userId);//初始化创建人
				mileStone.setVersion(currentLine);
			}
			result=mileStoneService.saveBaseLine(list, projId);
		}
		return result;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public Result edit(MileStone mileStone){
		
		Result result=mileStoneService.edit(mileStone);
		return result;
	}
	
	/**
	 * 遍历零部件项目
	 * @param projId
	 * @param version
	 * @return
	 */
	@RequestMapping("getChildListData")
	@ResponseBody
	public CusGridData<MileStone> getChildListData(String projId,String version,String platformFlag,Page page){
		
		
		Map<String,String> map = new HashMap<String, String>();
		List<MileStone> list = null;
		//获取该项目对应的系统项目Id
		AppProjInfo appProjInfo = appUserTaskPlatfromService.getProjById(projId);
		String primProjId = "";
		if(StringUtil.isNotNull(appProjInfo)){
			primProjId = appProjInfo.getPrimProjId();
		}
		if(StringUtil.isNotNull(platformFlag)){//个人中心平台里程碑
			map.put("projId", primProjId);
			map.put("childId", projId);
			String v = mileStoneService.maxBaseLine(map);
			map.put("version", v);
			list = mileStoneService.maxChildBaseLineData(map,page);
		}else{
			map.put("projId", primProjId);//主项目id
			map.put("childId", projId);//零部件项目
			
			if(StringUtil.isNotNull(version)){
				map.put("version", version);
			}
			list = mileStoneService.maxChildBaseLineData(map,page);
		}
		CusGridData<MileStone> gridData = new CusGridData<MileStone>(page, list);
		
		return gridData;
	}
	/**
	 * 初始化零部件项目基线的下拉框
	 * @param projId
	 * @return
	 */
	@RequestMapping("initChildCombox")
	@ResponseBody
	public String initChildCombox(String projId){
		
		AppProjInfo appProjInfo = appUserTaskPlatfromService.getProjById(projId);
		Map<String,String> map = new HashMap<String, String>();
		map.put("projId", appProjInfo.getPrimProjId());
		map.put("childId", projId);
		//现获取最大版本
	    String maxBaseLine = mileStoneService.maxBaseLine(map);
	    
	    String data = "";
	    for(int i = 1 ; i<= Integer.parseInt(maxBaseLine);i++){
	    	if(i == Integer.parseInt(maxBaseLine)){
	    		data = data + "{\"id\":\""+i+"\",\"text\":\""+i+"\",\"selected\":true},";
	    	}else{
	    		data = data + "{\"id\":\""+i+"\",\"text\":\""+i+"\"},";
	    	}
	    }
	    data = data.substring(0,data.lastIndexOf(","));
	    data = "["+ data +"]";//json拼接完成
	    return data;
	}
	@RequestMapping("initCombox")
	@ResponseBody
	public String initCombox(String projId){
		Map<String,String> map = new HashMap<String, String>();
		map.put("projId", projId);
		//现获取最大版本
	    String maxBaseLine = mileStoneService.maxBaseLine(map);
	    String data = "";
	    for(int i = 1 ; i<= Integer.parseInt(maxBaseLine);i++){
	    	if(i == Integer.parseInt(maxBaseLine)){
	    		data = data + "{\"id\":\""+i+"\",\"text\":\""+i+"\",\"selected\":true},";
	    	}else{
	    		data = data + "{\"id\":\""+i+"\",\"text\":\""+i+"\"},";
	    	}
	    }
	    data = data.substring(0,data.lastIndexOf(","));
	    data = "["+ data +"]";//json拼接完成
	    return data;
	}
	
	
	@RequestMapping("/export")
	public void export(String projId,String version,Page page,HttpServletResponse response) throws Exception{
		
		Map<String,String> map = new HashMap<String,String>();
		AppProjInfo appProjInfo = appUserTaskPlatfromService.getProjById(projId);
		List<MileStone> data;
		if(StringUtil.isNotNull(appProjInfo.getPrimProjId())){//零部件
			map.put("projId", appProjInfo.getPrimProjId());//主项目id
			map.put("childId", projId);//零部件项目ID
			
			if(StringUtil.isNotNull(version)){
				map.put("version", version);
			}
			data = mileStoneService.maxChildBaseLineData(map,page);
			
		}else{//主项目
			map.put("projId", projId);
			if(StringUtil.isNotNull(version)){
				map.put("version", version);
			}
			data = mileStoneService.maxBaseLineData(map,page);
		}
		
		CusGridData<MileStone> gridData = new CusGridData<MileStone>(page, data);
		
		List<Map<String,Object>> list = gridData.getRows();
		String fileName = java.net.URLEncoder.encode("里程碑", "UTF-8");
		List<String> heads = new ArrayList<String>();
		//excel头信息
		String[] arrayHeads = {"级别","里程碑名称","计划时间","实际时间","版本"};
		for (String string : arrayHeads) {
			heads.add(string);
		}
		//对应值
		List<String> keys = new ArrayList<String>();
		String arrayKeys[] = {"M_LEVEL","NAME","PLAN_TIME","ACT_TIME","VERSION"};
		for (String string : arrayKeys) {
			keys.add(string);
		}
		
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
	
	
}
