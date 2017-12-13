package com.ebon.v2.eai.command;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;
import com.ebon.platform.dao.DaoException;
import com.ebon.v2.eai.base.model.Command;
import com.ebon.v2.eai.command.service.CommandService;

public class CommandServiceTest extends BaseTest{
	@Autowired
	CommandService commandService;
	@Test
	public void getCommands(){
		try {
			List commands = this.commandService.getCommands();
			
			for(int i=0;i<commands.size();i++){
				Command command = (Command )commands.get(i);
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println(new String(command.getData()));
			}
			
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
