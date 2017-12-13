package com.ebon.app.action.dept;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.app.service.dept.IDeptService;
import com.ebon.platform.action.BaseAction;

@Controller
@RequestMapping("/dept")
public class deptController extends BaseAction {
	
	@Autowired
	private IDeptService	deptService;
	
	@RequestMapping("/getListByFlag")
	@ResponseBody
	public List<Map<String, Object>> getListByFlag(String flag) throws Exception {
		List<Map<String, Object>> listData = deptService.getListByFlag(flag);
		return listData;
	}
	
	public IDeptService getDeptService() {
		return deptService;
	}
	
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}
	
}
