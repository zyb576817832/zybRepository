package com.ebon.v2.eai.command.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebon.platform.action.BaseAction;
import com.ebon.v2.eai.base.model.Command;
import com.ebon.v2.eai.command.service.CommandService;

@Controller
@Scope()
@RequestMapping("/commandController")
public class CommandController extends BaseAction {
	@Autowired
	CommandService commandService;

	@RequestMapping("/commandList")
	public String doManulCommand(Model model, String regStartDateL)
			throws Exception {
		List<Command> data = commandService.getCommands();
		model.addAttribute("data",data);
		return "v2/com";
	}

	public CommandService getCommandService() {
		return commandService;
	}

	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}

}
