package com.ebon.app.action.tef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ebon.app.service.tef.ITefService;
import com.ebon.app.service.tef.vo.TefInfo;
import com.ebon.platform.action.BaseAction;
import com.ebon.platform.action.JqGridData;
import com.ebon.platform.dao.pager.Page;

@Controller
@RequestMapping("/tef")
public class TefController extends BaseAction {
	
	@Autowired
	private ITefService	tefService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() throws Exception {
		return "tef/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JqGridData<TefInfo> list(Page page, TefInfo info) throws Exception {
		List<TefInfo> data = tefService.getPageList(page, info);
		JqGridData<TefInfo> gridData = new JqGridData<TefInfo>(page, data);
		return gridData;
	}
	
	@RequestMapping(value = "/insert")
	@ResponseBody
	public String insert(TefInfo info) throws Exception {
		String rtn = null;
		boolean b = tefService.insert(info);
		if (b) {
			rtn = "1";
		} else {
			rtn = "保存发生错误！";
		}
		return rtn;
	}
	
	@RequestMapping(value = "/getTefById/{tefId}", method = RequestMethod.GET)
	public ModelAndView getTefById(@PathVariable String tefId) throws Exception {
		TefInfo info = tefService.getTefById(tefId);
		Map<String, TefInfo> model = new HashMap<String, TefInfo>(1);
		model.put("tefInfo", info);
		return new ModelAndView("tef/edit", model);
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(TefInfo info) throws Exception {
		String rtn = null;
		boolean b = tefService.update(info);
		if (b) {
			rtn = "1";
		} else {
			rtn = "保存发生错误！";
		}
		return rtn;
	}
	
	@RequestMapping(value = "/delete/{tefId}", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@PathVariable String tefId) throws Exception {
		String rtn = null;
		boolean b = tefService.delete(tefId);
		if (b) {
			rtn = "1";
		} else {
			rtn = "删除发生错误！";
		}
		return rtn;
	}
	
	public ITefService getTefService() {
		return tefService;
	}
	
	public void setTefService(ITefService tefService) {
		this.tefService = tefService;
	}
	
}
