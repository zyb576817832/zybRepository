package com.ebon.app.action.costcenter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.app.service.costcenter.ICostCenterService;
import com.ebon.platform.action.BaseAction;

@Controller
@RequestMapping("/costCenter")
public class costCenterController extends BaseAction {
	
	@Autowired
	private ICostCenterService	costCenterService;
	
	@RequestMapping("/getListByIsTEF")
	@ResponseBody
	public List<Map<String, Object>> getListByFlag(String istef) throws Exception {
		List<Map<String, Object>> listData = costCenterService.getListByIsTEF(istef);
		return listData;
	}
	
	public ICostCenterService getCostCenterService() {
		return costCenterService;
	}
	
	public void setCostCenterService(ICostCenterService costCenterService) {
		this.costCenterService = costCenterService;
	}
	
}
