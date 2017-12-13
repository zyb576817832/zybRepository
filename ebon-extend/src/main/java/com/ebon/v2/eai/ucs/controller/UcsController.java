package com.ebon.v2.eai.ucs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ebon.v2.eai.base.controller.BaseEaiController;
import com.ebon.v2.eai.base.model.Command;
import com.ebon.v2.eai.ucs.service.TimesheetService;

@Controller
@Scope()
@RequestMapping("/ucsController")
public class UcsController extends BaseEaiController {
	@Autowired
	TimesheetService timesheetService ;
	@RequestMapping("/doCommand")
	public String doManulCommand(Model model,String regStartDateU) throws Exception {
		String startDate = regStartDateU;
		Command command = this.timesheetService.doManulCommand(startDate);
		model.addAttribute(command);
		return "v2/registTimeResult";
	}
}
