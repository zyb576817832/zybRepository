/*
 * Created on 2006-10-8
 *
 */
package com.ebon.rpc.sap.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebon.platform.util.Config;
import com.ebon.rpc.sap.IProcessor;
import com.ebon.rpc.sap.exception.SapException;
import com.ebon.rpc.sap.model.SapAccount;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Function;

/**
 *  sap 连接器
 */
@Service
public class SapInvoker {

	@Autowired
	private SapAccount sapaccount;
	
	private JCO.Client mClient;

	private JCO.Repository mRepository;

	@Config("SAP.SID")
	private String SID;

	public SapInvoker() {
		
	}
	
	public SapInvoker(SapAccount sapaccount) {
		this.sapaccount = sapaccount;
	}
	
	/**
	 * 执行验证操作，如果用户的配置参数正确的话sapinvoker自动取得连接，如果不正确的则抛出错误
	 * 
	 * @throws SapException
	 */
	public void validate() throws SapException {
		try {
			this.mClient = JCO.createClient(sapaccount.getClient(), 
					sapaccount.getUserId(), 
					sapaccount.getPassword(), 
					sapaccount.getLang(), 
					sapaccount.getAshost(), 
					sapaccount.getSysnr()); 
			mClient.connect();
			this.mRepository = new JCO.Repository(SID, mClient);
		} catch (Exception e1) {
			System.out.println("连接失败!");
			throw new SapException(e1);
		}
	}

	public Function createFunction(String name) throws SapException {
		Function function = null;
		try {
			IFunctionTemplate ft = mRepository.getFunctionTemplate(name.toUpperCase());
			if (ft != null){
				function = ft.getFunction();
			}
		} catch (Exception ex) {
			throw new SapException("Problem retrieving JCO.Function object.", ex);
		}
		return function;
	}

	/**
	 * 处理业务逻辑,执行结束后自动释放连接
	 * 
	 * @param iprocessor
	 * @throws SapException
	 * @throws Exception
	 */
	public Object execute(IProcessor iprocessor) throws SapException {
		Object rtVal = null;
		try {
			Function function = createFunction(iprocessor.fname());// set the function name

			iprocessor.doFilter(function);// set input parameter
			if (this.mClient.isAlive()) {
				if (!isCommit())
					this.mClient.execute(function);
				else {
					IProcessor t_IProcessor = this.getCommitFunction();
					Function t_commitFunction = createFunction(t_IProcessor.fname());
					t_IProcessor.doFilter(t_commitFunction);
					this.mClient.execute(function);
					this.mClient.execute(t_commitFunction);
				}
			}
			rtVal = iprocessor.doBusiness(function);
		} catch (SapException e) {
			throw new SapException("调用函数时失败");
		} finally {
			this.mClient.disconnect();
		}
		return rtVal;
	}

	/**
	 * @return Returns the mClient.
	 */
	public JCO.Client getMClient() {
		return mClient;
	}
	
	public boolean isAlive() {
		return mClient.isAlive();
	}
	
	/****************** 特殊处理，处理本function调用结束后还要调用bapicommit的操作标志 *****************/
	private boolean commit;

	private IProcessor commitFunction;

	public IProcessor getCommitFunction() {
		return commitFunction;
	}

	public void setCommitFunctionName(IProcessor commitFunction) {
		this.commitFunction = commitFunction;
	}

	public boolean isCommit() {
		return commit;
	}

	public void setCommit(boolean commit) {
		this.commit = commit;
	}

	public SapAccount getSapaccount() {
		return sapaccount;
	}

	public void setSapaccount(SapAccount sapaccount) {
		this.sapaccount = sapaccount;
	}
}