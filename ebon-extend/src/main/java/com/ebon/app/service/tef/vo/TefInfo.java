package com.ebon.app.service.tef.vo;

import java.io.Serializable;

import com.ebon.platform.util.StringUtil;

public class TefInfo implements Serializable {
	
	private static final long	serialVersionUID	= -8135218604516014359L;
	
	private String				tefId;
	
	private String				deptName;
	
	private String				hnte;
	
	private String				deptId;
	
	private String				costCenterId;
	
	private String				costCenterName;
	
	private String				hnteId;
	
	private String				regDate;
	
	private String				workHour;
	
	private String				createDate;
	
	private String				updateDate;
	
	private String				regStartDate;
	
	private String				regEndDate;
	
	public String getRegStartDate() {
		if (!StringUtil.isNotNull(regStartDate)) {
			regStartDate = null;
		}
		return regStartDate;
	}
	
	public void setRegStartDate(String regStartDate) {
		this.regStartDate = regStartDate;
	}
	
	public String getRegEndDate() {
		if (!StringUtil.isNotNull(regEndDate)) {
			regEndDate = null;
		}
		return regEndDate;
	}
	
	public void setRegEndDate(String regEndDate) {
		this.regEndDate = regEndDate;
	}
	
	public String getDeptId() {
		if (!StringUtil.isNotNull(deptId)) {
			deptId = null;
		}
		return deptId;
	}
	
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getCostCenterId() {
		return costCenterId;
	}
	
	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}
	
	public String getCostCenterName() {
		return costCenterName;
	}
	
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	
	public String getHnteId() {
		if (!StringUtil.isNotNull(hnteId)) {
			hnteId = null;
		}
		return hnteId;
	}
	
	public void setHnteId(String hnteId) {
		this.hnteId = hnteId;
	}
	
	public String getTefId() {
		return tefId;
	}
	
	public void setTefId(String tefId) {
		this.tefId = tefId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getHnte() {
		return hnte;
	}
	
	public void setHnte(String hnte) {
		this.hnte = hnte;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public String getWorkHour() {
		return workHour;
	}
	
	public void setWorkHour(String workHour) {
		this.workHour = workHour;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	@Override
	public String toString() {
		return "TefInfo [tefId=" + tefId + ", deptName=" + deptName + ", costCenterName=" + costCenterName + ", hnte=" + hnte + ", deptId=" + deptId + ", costCenterId=" + costCenterId + ", hnteId=" + hnteId + ", regDate=" + regDate + ", workHour=" + workHour + ", createDate=" + createDate + ", updateDate=" + updateDate + ", regStartDate=" + regStartDate + ", regEndDate=" + regEndDate + "]";
	}
	
}
