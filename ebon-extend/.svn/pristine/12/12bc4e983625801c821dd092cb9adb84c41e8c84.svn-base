package com.ebon.v2.eai.lims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebon.v2.eai.base.controller.BaseEaiController;
import com.ebon.v2.eai.base.model.Command;
import com.ebon.v2.eai.lims.model.ProjectInfoCommand;
import com.ebon.v2.eai.lims.service.LimsUsageService;
import com.ebon.v2.eai.lims.service.MainProjectInfoService;

@Controller
@Scope()
@RequestMapping("/limsController")
public class LimsController extends BaseEaiController {
	@Autowired
	LimsUsageService limsUsageService;

	@Autowired
	MainProjectInfoService mainProjectInfoService;

	@RequestMapping("/doCommand")
	public String doManulCommand(Model model,String regStartDateL) throws Exception {
		Command command = this.limsUsageService.doManulCommand(regStartDateL);
		model.addAttribute(command);
		return "v2/registTimeResult";
	}
	
	@RequestMapping("/pushAllProjectInfo")
	public String pushAllProjectInfo(Model model ) throws Exception {
		ProjectInfoCommand projectInfoCommand = this.mainProjectInfoService.pushAllProjectInfo();
		model.addAttribute(projectInfoCommand);
		return "v2/pushResult";
	}
	
	@RequestMapping("/pushProjectInfo")
	public String pushProjectInfo(Model model ) throws Exception {
		ProjectInfoCommand projectInfoCommand = this.mainProjectInfoService.pushProjectInfo();
		model.addAttribute(projectInfoCommand);
		return "v2/pushResult";
	}

}
