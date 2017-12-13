package com.ebon.platform.dao.dialect;

import com.ebon.platform.dao.pager.Page;


public class MySql5Dialect extends Dialect {
	
	protected static final String SQL_END_DELIMITER = ";";
	
	public String getLimitSql(String sql, boolean hasOffset) {
		return MySql5PageHepler.getLimitString(sql,-1,-1);
	}

	public String getLimitSql(String sql, int offset, int limit) {
		return MySql5PageHepler.getLimitString(sql, offset, limit);
	}

	public boolean supportsLimit() {
		return true;
	}

	@Override
	public String getLimitSql(String sql, Page page) {
		return null;
	}

	@Override
	public String getCountSql(String sql) {
		return null;
	}

}
