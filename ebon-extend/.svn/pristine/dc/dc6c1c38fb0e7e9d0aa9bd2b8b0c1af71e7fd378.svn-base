package com.ebon.v2.eai.base;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ebon.platform.BaseTest;

@TransactionConfiguration(defaultRollback=false)
public class BaseEaiServiceTest  extends BaseTest {

	@Test
	public void ping(){
		String[] beanDefinitionNames = this.applicationContext.getBeanDefinitionNames();
		for(int i=0;i<beanDefinitionNames.length;i++){
			System.out.println(beanDefinitionNames[i]);
		}
	}
}
