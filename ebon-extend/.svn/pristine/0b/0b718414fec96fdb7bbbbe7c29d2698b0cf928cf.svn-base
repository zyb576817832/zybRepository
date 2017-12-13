package com.ebon.app.action.dict;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.app.service.dict.IDictionaryService;
import com.ebon.platform.action.BaseAction;

@Controller
@RequestMapping("/dict")
public class DictionaryController extends BaseAction {
	
	@Autowired
	private IDictionaryService	dictionartService;
	
	@RequestMapping("/getListByType")
	@ResponseBody
	public List<Map<String, Object>> getListByType(String type) throws Exception {
		List<Map<String, Object>> listData = dictionartService.getList(type, "1");
		return listData;
	}
	
	public IDictionaryService getDictionartService() {
		return dictionartService;
	}
	
	public void setDictionartService(IDictionaryService dictionartService) {
		this.dictionartService = dictionartService;
	}
	
}
