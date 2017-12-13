package com.ebon.platform.service;

import org.apache.commons.logging.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.dao.MyBatisDao;
import com.ebon.platform.extend.DataSourceHandle;
import com.ebon.platform.util.SpringContextUtil;

public abstract class BaseService {
	
	protected static final Log log = LogFactory.getFactory().getInstance(BaseService.class);
	
	@Autowired(required=true)
	protected MyBatisDao myBatisDao;
	
	/**
	 * 修改要使用的DataSource
	 * @param dataSourceName
	 */
	protected void setDataSourceName(String dataSourceName) {
		DataSourceHandle.setDataSourceType(dataSourceName);
	}
	
	public MyBatisDao getMyBatisDao() {
		return myBatisDao;
	}

	public void setMyBatisDao(MyBatisDao myBatisDao) {
		this.myBatisDao = myBatisDao;
	}
	
	public SqlSession getSession() {
		return this.getSession(null);
	}
	
	public SqlSession getSession(String sessionFactoryName) {
		SqlSession session = null;
		try {
			if (null == sessionFactoryName || sessionFactoryName.trim().length() <= 0) {
				sessionFactoryName = "sqlSessionFactory";
			}
			SqlSessionFactory sessionFactory = (SqlSessionFactory)SpringContextUtil.getBean(sessionFactoryName);
			session = sessionFactory.openSession();
		} catch (Exception e) {
			log.error(e);
		}
		return session;
	}
	
	public void closeSession(SqlSession session) {
		if (null != session) {
			try{
				session.close();
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

}
