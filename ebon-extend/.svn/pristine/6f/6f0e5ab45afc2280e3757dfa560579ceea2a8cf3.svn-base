package com.ebon.v3;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ebon.platform.BaseTest;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.util.Guid;
import com.ebon.v3.service.IAppTaskDocService;
import com.ebon.v3.vo.AppTaskDoc;

//设置为不会滚
@TransactionConfiguration(defaultRollback=false)
public class TaskDoc extends BaseTest {
	
	@Autowired
	IAppTaskDocService appTaskDocServcie;
	
	
	@Test
	public void add(){
		AppTaskDoc doc = new AppTaskDoc();
		doc.setId(Guid.getGuid());
		doc.setName("CamelCaseUtils1.java");
		doc.setCreateUser("A76B43602E1344CB8A1CCBFDC41846D2");
		doc.setFileSize("10245");
		doc.setTaskId("CDF2DEF64E644BE6ABE2C5749FCA1954");
		appTaskDocServcie.add(doc);
	}
	
	@Test
	public void getList(){
		AppTaskDoc query = new AppTaskDoc();
		query.setTaskId("CDF2DEF64E644BE6ABE2C5749FCA1954");
		appTaskDocServcie.getVlist(query, new Page());
	}
	
	@Test
	public void delete(){
		appTaskDocServcie.delete("C92DA782B2C044F2BBBDA94CDB40C0C9".split(","), "Test");
	}

}
