package com.ebon.v2.eai.command.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.v2.eai.base.model.Command;

@Component
public class CommandService extends BaseService{
	
	public Command getCommand(String id) throws DaoException{
		return (Command)this.myBatisDao.getOne("V2Mapper.getCommand", id);
	}

	
	public List getCommands() throws DaoException{
		java.util.HashMap<String,String> params = new java.util.HashMap<String,String>();	
		return this.myBatisDao.getList("V2Mapper.selectCommands");
	}

}
