package com.ebon.platform.dao.dialect;

import com.ebon.platform.dao.pager.Page;

public abstract class Dialect {

	public static enum Type{
		MYSQL,
		ORACLE
	}
	
	public abstract String getLimitSql(String sql, Page page);
	
	public abstract String getCountSql(String sql);
	
}
