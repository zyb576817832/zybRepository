/**
 * @Company <Jinher Technology Development Company LTD.>
 * @Project ebon-platform
 * @module 
 * @version 
 * @author <string>
 * @Create 2013-4-17
 * @description
 */
package com.ebon.platform.user.vo;

import com.ebon.framework.bo.BusinessObject;

public class SysUser extends BusinessObject{
	
	private static final long serialVersionUID = 7549068137349290794L;
	
	private String employee;//用户工号
	private String gender;//性别 0女 1男 2未知
	private String mobile;//手机
	private String tel;//电话
	private String email;//电子邮箱
	private String passwd;//用户密码
	private String resourceFlag;//是否资源 0不是资源 1是资源
	private String account;//用户帐号
	private String innerFlag;//是否内置
	private String signImage;//个性签名
	private String objectId;
	private String tempRoleId;
	private String templateObsId;
	private String IDCardNo;//身份证号
	private String OUCode;//OU编号
	private String type;//人员类型
	private String typeName;//类型名称
	private String birthday;//出生日期
	private String synId;//对方系统用户ID
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getResourceFlag() {
		return resourceFlag;
	}
	public void setResourceFlag(String resourceFlag) {
		this.resourceFlag = resourceFlag;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getInnerFlag() {
		return innerFlag;
	}
	public void setInnerFlag(String innerFlag) {
		this.innerFlag = innerFlag;
	}
	public String getSignImage() {
		return signImage;
	}
	public void setSignImage(String signImage) {
		this.signImage = signImage;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getTempRoleId() {
		return tempRoleId;
	}
	public void setTempRoleId(String tempRoleId) {
		this.tempRoleId = tempRoleId;
	}
	public String getTemplateObsId() {
		return templateObsId;
	}
	public void setTemplateObsId(String templateObsId) {
		this.templateObsId = templateObsId;
	}
	public String getIDCardNo() {
		return IDCardNo;
	}
	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}
	public String getOUCode() {
		return OUCode;
	}
	public void setOUCode(String oUCode) {
		OUCode = oUCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSynId() {
		return synId;
	}
	public void setSynId(String synId) {
		this.synId = synId;
	}
	@Override
	public String toString() {
		return "SysUser [employee=" + employee + ", gender=" + gender
				+ ", mobile=" + mobile + ", tel=" + tel + ", email=" + email
				+ ", passwd=" + passwd + ", resourceFlag=" + resourceFlag
				+ ", account=" + account + ", innerFlag=" + innerFlag
				+ ", signImage=" + signImage + ", objectId=" + objectId
				+ ", tempRoleId=" + tempRoleId + ", templateObsId="
				+ templateObsId + ", IDCardNo=" + IDCardNo + ", OUCode="
				+ OUCode + ", type=" + type + ", typeName=" + typeName
				+ ", birthday=" + birthday + ", synId=" + synId + "]";
	}
	
	
	
}
