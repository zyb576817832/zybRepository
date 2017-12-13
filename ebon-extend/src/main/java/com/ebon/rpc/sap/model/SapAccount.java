package com.ebon.rpc.sap.model;

import org.springframework.stereotype.Service;

import com.ebon.platform.util.Config;

/**
 * @author chenlei
 */

@Service
public class SapAccount {
	
	@Config("SAP.client")
	private String client; // SAP client
	@Config("SAP.login_name")
	private String userId; // userName
	@Config("SAP.password")
	private String password; // password
	@Config("SAP.language")
	private String lang; // language
	@Config("SAP.host_name")
	private String ashost; // host name
	@Config("SAP.system_number")
	private String sysnr; // system number

	
	public SapAccount() {}

	/**
	 * 初始化该sapaccount
	 */
	public SapAccount(String userId, String password, String client,
			String language, String host, String sysnum) {
		this.client = client;
		this.userId = userId;
		this.password = password;
		this.lang = language;
		this.ashost = host;
		this.sysnr = sysnum;
	}

	/**
	 * @return Returns the ashost.
	 */
	public String getAshost() {
		return ashost;
	}

	/**
	 * @param ashost
	 *            The ashost to set.
	 */
	public void setAshost(String ashost) {
		this.ashost = ashost;
	}

	/**
	 * @return Returns the client.
	 */
	public String getClient() {
		return client;
	}

	/**
	 * @param client
	 *            The client to set.
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @return Returns the lang.
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang
	 *            The lang to set.
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the sysnr.
	 */
	public String getSysnr() {
		return sysnr;
	}

	/**
	 * @param sysnr
	 *            The sysnr to set.
	 */
	public void setSysnr(String sysnr) {
		this.sysnr = sysnr;
	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
