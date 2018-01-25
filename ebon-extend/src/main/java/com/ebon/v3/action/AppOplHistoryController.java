package com.ebon.v3.action;

import java.io.BufferedOutputStream; 
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.vo.CusGridData;
import com.ebon.v3.service.IOplHistoryService;
import com.ebon.v3.util.ExcelUtil;
import com.ebon.v3.vo.OplHistory;

@Controller
@RequestMapping("app/oplHistory")
public class AppOplHistoryController {

	@Autowired
	private IOplHistoryService oplHistoryService;
	
	@RequestMapping("/getListData")
	@ResponseBody
	public CusGridData<OplHistory> getListData(String oplId,Page page){
		
		List<OplHistory> list=oplHistoryService.getByOplId(oplId,page);
		CusGridData<OplHistory> gridData = new CusGridData<OplHistory>(page, list);
		return gridData;
	}
	/**
	 * excel导出
	 * @param page
	 * @param oplId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/export")
	public void export(Page page,String oplId,HttpServletResponse response) throws Exception{
		
		List<OplHistory> data=oplHistoryService.getByOplId(oplId,page);
		CusGridData<OplHistory> gridData = new CusGridData<OplHistory>(page, data);
		List<Map<String,Object>> list = gridData.getRows();
		String fileName = java.net.URLEncoder.encode("opl历史纪录", "UTF-8");
		
		List<String> heads = new ArrayList<String>();
		//excel头信息
		String[] arrayHeads = {"修改时间","修改人","问题描述","严重度","优先级","责任人","备注"};
		for (String string : arrayHeads) {
			heads.add(string);
		}
		//对应值
		List<String> keys = new ArrayList<String>();
		String arrayKeys[] = {"CREATE_TIME","CREATE_USER","OPL_DESC","OPL_DEGREE","OPL_LEVEL","RESP_USER","OPL_DESCRIBE"};
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
