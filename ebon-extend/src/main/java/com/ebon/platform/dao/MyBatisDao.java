package com.ebon.platform.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ebon.platform.dao.pager.Page;

public class MyBatisDao extends BaseDao {
	
	public boolean save(String key) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = super.getSqlSession().insert(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean save(SqlSession session, String key) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = session.insert(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean save(String key, Object object) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = super.getSqlSession().insert(key, object);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean save(SqlSession session, String key, Object object) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = session.insert(key, object);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean update(String key) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = super.getSqlSession().update(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean update(SqlSession session, String key) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = session.update(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean update(String key, Object object) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = super.getSqlSession().update(key, object);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean update(SqlSession session, String key, Object object) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = session.update(key, object);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean delete(String key) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = super.getSqlSession().delete(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean delete(SqlSession session, String key) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = session.delete(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean delete(String key, Serializable id) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = super.getSqlSession().delete(key, id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean delete(SqlSession session, String key, Serializable id) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = session.delete(key, id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean delete(String key, Object object) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = super.getSqlSession().delete(key, object);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	public boolean delete(SqlSession session, String key, Object object) throws DaoException {
		boolean b = false;
		int rtn = 0;
		try {
			rtn = session.delete(key, object);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		if (rtn > 0) {
			b = true;
		}
		return b;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOne(String key) throws DaoException {
		T t = null;
		try {
			t = (T) super.getSqlSession().selectOne(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOne(SqlSession session, String key) throws DaoException {
		T t = null;
		try {
			t = (T) session.selectOne(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOne(String key, String params) throws DaoException {
		T t = null;
		try {
			t = (T) super.getSqlSession().selectOne(key, params);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOne(SqlSession session, String key, String params) throws DaoException {
		T t = null;
		try {
			t = (T) session.selectOne(key, params);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOne(String key, Object params) throws DaoException {
		T t = null;
		try {
			t = (T) super.getSqlSession().selectOne(key, params);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOne(SqlSession session, String key, Object params) throws DaoException {
		T t = null;
		try {
			t = (T) session.selectOne(key, params);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) throws DaoException {
		List<T> t = null;
		try {
			t = super.getSqlSession().selectList(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(SqlSession session, String key) throws DaoException {
		List<T> t = null;
		try {
			t = session.selectList(key);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key, Object params) throws DaoException {
		List<T> t = null;
		try {
			t = super.getSqlSession().selectList(key, params);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(SqlSession session, String key, Object params) throws DaoException {
		List<T> t = null;
		try {
			t = session.selectList(key, params);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key, Page page) throws DaoException {
		List<T> t = null;
		try {
			t = super.getSqlSession().selectList(key, page);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(SqlSession session, String key, Page page) throws DaoException {
		List<T> t = null;
		try {
			t = session.selectList(key, page);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key, Object params, Page page) throws DaoException {
		List<T> t = null;
		try {
			t = super.getSqlSession().selectList(key, params, page);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(SqlSession session, String key, Object params, Page page) throws DaoException {
		List<T> t = null;
		try {
			t = session.selectList(key, params, page);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return t;
	}

}
