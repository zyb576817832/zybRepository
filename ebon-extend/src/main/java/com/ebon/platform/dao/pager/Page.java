package com.ebon.platform.dao.pager;

import org.apache.ibatis.session.RowBounds;

public class Page extends RowBounds {

	private int total; // 总页数
	private int records; // 总记录数
	private int currentResult; // 当前记录起始索引
	
	private int page = 1; // 当前页
	
	private int rows = 10; // 每页显示记录数

	private String sort;

	private String order;
	
	public int getTotal() {
		if (records % rows == 0) {
			total = records / rows;
		} else {
			total = records / rows + 1;
		}
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getCurrentResult() {
		currentResult = (getPage() - 1) * getRows();
		if (currentResult < 0) {
			currentResult = 0;
		}
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

}
