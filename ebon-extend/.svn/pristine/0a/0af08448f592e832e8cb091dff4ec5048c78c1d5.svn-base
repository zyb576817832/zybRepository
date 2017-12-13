package com.ebon.platform.action;

import java.util.List;

import com.ebon.platform.dao.pager.Page;

public class JqGridData<T> {
	
	public JqGridData(Page page, List<T> data){
		this.rows = data;
		this.total = page.getRecords();
		this.page = page.getPage();
	}
	
	private int total; // 总记录数
	private int page; // 当前页
	
	private List<T> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
