package com.ebon.app.action.user;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.app.service.user.IUserService;
import com.ebon.app.service.user.vo.User;
import com.ebon.platform.action.BaseAction;
import com.ebon.platform.action.JqGridData;
import com.ebon.platform.dao.pager.Page;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction {
	
	@Resource
	private IUserService	userService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JqGridData<Object> test(Page page, User user, String search) throws Exception {
		System.out.println(user.toString());
		System.out.println(search);
		System.out.println(page.toString());
		List<Object> data = userService.getUser(page, user);
		JqGridData<Object> gridData = new JqGridData<Object>(page, data);
		return gridData;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public String updateUser(Map<String, String> paramData) {
		Set<String> keySet = paramData.keySet();
		for (String key : keySet) {
			System.out.println(key + "-----" + paramData.get(key));
		}
		return "../index";
	}
	
	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
}
