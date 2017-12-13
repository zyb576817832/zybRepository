package com.ebon.rpc.equipment.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.stereotype.Service;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.extend.DataSourceConstant;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.DateHelper;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.equipment.IEquipmentService;

@Service
public class EquipmentService extends BaseRpcService implements IEquipmentService {
	
	private static final String	DEPT_NE3	= "NE3";
	
	private static final String	DEPT_NE4	= "NE4";
	
	@Override
	public List<Map<String, Object>> getEquipmentInfo(Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		list = getEquipmentInfoNE3(requestMap);
		List<Map<String, Object>> list2 = getEquipmentInfoNE4(requestMap);
		if (StringUtil.isNotNull4List(list)) {
			list.addAll(list2);
		} else {
			list = list2;
		}
		return list;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<Map<String, Object>> getEquipmentInfoNE3(Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> ne3List = null;
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateHelper.getStringToDate((String) requestMap.get("startDate"));
			endDate = DateHelper.getStringToDate((String) requestMap.get("endDate"));
			if (this.compleYear(endDate, startDate) == 0) {
				requestMap.put("endDate_startDate", startDate.getYear() + 1900);
				ne3List = this.getList(requestMap, DEPT_NE3);
			} else if (this.compleYear(endDate, startDate) >= 1) {
				ne3List = new ArrayList<Map<String, Object>>();
				for (int j = 0; j <= this.compleYear(endDate, startDate); j++) {
					if (j == 0) {
						requestMap.put("endDate_startDate", startDate.getYear() + 1900);
						ne3List.addAll(this.getList(requestMap, DEPT_NE3));
					} else if (j > 0 && j < this.compleYear(endDate, startDate)) {
						requestMap.put("endDate_startDate", (startDate.getYear() + j + 1900));
						ne3List.addAll(this.getList(requestMap, DEPT_NE3));
					} else if (j == this.compleYear(endDate, startDate)) {
						requestMap.put("endDate_startDate", endDate.getYear() + 1900);
						ne3List.addAll(this.getList(requestMap, DEPT_NE3));
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return ne3List;
	}
	
	/**
	 * 获取设备信息列表
	 * 
	 * @param requestMap
	 * @param dept
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getList(Map<String, Object> requestMap, String dept) throws ServiceException {
		List<Map<String, Object>> list = null;
		SqlSession session = null;
		try {
			session = super.getSession(DataSourceConstant.PMS_DATASOURCE_NAME);
			list = myBatisDao.getList(session, "equipmentMapper.getEquipmentInfo" + dept, requestMap);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return list;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<Map<String, Object>> getEquipmentInfoNE4(Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> ne4List = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String startDateStr = requestMap.get("startDate") + " 00:00:00";
		String endDateStr = requestMap.get("endDate") + " 23:59:59";
		paramMap.put("startDate", startDateStr);
		paramMap.put("endDate", endDateStr);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateHelper.getStringToLongDate(startDateStr);
			endDate = DateHelper.getStringToLongDate(endDateStr);
			int monthCount = (endDate.getYear() - startDate.getYear()) * 12 + endDate.getMonth() - startDate.getMonth() + 1;
			paramMap.put("monthCount", monthCount);
			if (this.compleYear(endDate, startDate) == 0) {
				paramMap.put("endDate_startDate", startDate.getYear() + 1900);
				ne4List = this.getList(paramMap, DEPT_NE4);
			} else if (this.compleYear(endDate, startDate) >= 1) {
				ne4List = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < this.compleYear(endDate, startDate); i++) {
					if (i == 0) {
						paramMap.put("endDate_startDate", startDate.getYear() + 1900);
						ne4List.addAll(this.getList(paramMap, DEPT_NE4));
					} else if (i > 0 && i < this.compleYear(endDate, startDate)) {
						paramMap.put("endDate_startDate", (startDate.getYear() + i + 1900));
						ne4List.addAll(this.getList(paramMap, DEPT_NE4));
					} else if (i == this.compleYear(endDate, startDate)) {
						paramMap.put("endDate_startDate", endDate.getYear() + 1900);
						ne4List.addAll(this.getList(paramMap, DEPT_NE4));
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return ne4List;
	}
	
	@SuppressWarnings("deprecation")
	private int compleYear(Date endDate, Date startDate) {
		return endDate.getYear() - startDate.getYear();
	}
	
	/**
	 * 从PMS数据库抽取设备信息,写到uPMS库中,给CPC提供支持
	 */
	@Override
	public void putEquipmentDataToTable() throws ServiceException {
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction tx = null;
		Map<String, Object> requestMap = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//从2005年开始抽取数据,直到今年
		try {
			session = super.getSession();
			//tx = transactionFactory.newTransaction(session.getConnection(), false);
			  tx = transactionFactory.newTransaction(session.getConnection());
			for (int year = 2005; year < new Date().getYear() + 1901; year++) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, year);
				for (int month = 1; month <= 12; month++) {
					System.out.println("现在开始抽取" + year + "年" + month + "月份数据");
					calendar.set(Calendar.MONTH, month - 1);
					String startDate = year + "-" + month + "-1";
					String endDate = year + "-" + month + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					try {
						requestMap.put("startDate", startDate);
						requestMap.put("endDate", endDate);
						requestMap.put("currentYear", DateHelper.getStringToDate(startDate).getYear() + 1900);
						List<Map<String, Object>> list = getEquipmentInfoForCPC(session, requestMap);
						if (StringUtil.isNotNull4List(list)) {
							dataList.addAll(list);
						} else {
							System.out.println(year + "年" + month + "月没有数据!!");
						}
					} catch (Exception e) {
						log.error(e);
						throw new ServiceException(e);
					}
				}
			}
			if (StringUtil.isNotNull4List(dataList)) {
				deleteEquipmentInfoForCPC(session);
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, Object> maeMap = dataList.get(i);
					insertMAE(session, maeMap);
				}
				System.out.println("成功将数据更新到数据库中!!");
			} else {
				System.out.println("没有获取到数据,无法执行更新操作!!");
			}
			tx.commit();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (SQLException e1) {
				log.error(e1);
				throw new ServiceException(e1);
			}
			log.error(e);
			throw new ServiceException(e);
		} finally {
			if (null != tx) {
				try {
					tx.close();
				} catch (SQLException e) {
					log.error(e);
					throw new ServiceException();
				}
			}
		}
	}
	
	private boolean insertMAE(SqlSession session, Map<String, Object> paramMap) throws ServiceException {
		boolean flag = false;
		try {
			flag = myBatisDao.save(session, "equipmentMapper.insertMAE", paramMap);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return flag;
	}
	
	private void resetMAEFlag(SqlSession session) throws ServiceException {
		try {
			myBatisDao.update(session, "equipmentMapper.resetMAEFlag");
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	private boolean updateMAEInfo(SqlSession session, Map<String, Object> paramMap) throws ServiceException {
		boolean flag = false;
		try {
			flag = myBatisDao.update(session, "equipmentMapper.updateMAEInfo", paramMap);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return flag;
	}
	
	private List<Map<String, Object>> getEquipmentInfoForCPC(SqlSession session, Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listNE3 = getEquipmentInfoNE3ForCPC(session, requestMap);
		if (StringUtil.isNotNull4List(listNE3)) {
			list.addAll(listNE3);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("currentYear", requestMap.get("currentYear"));
		paramMap.put("startDate", requestMap.get("startDate") + " 00:00:00");
		paramMap.put("endDate", requestMap.get("endDate") + " 23:59:59");
		paramMap.put("monthCount", 1);
		List<Map<String, Object>> listNE4 = getEquipmentInfoNE4ForCPC(session, paramMap);
		if (StringUtil.isNotNull4List(listNE4)) {
			list.addAll(listNE4);
		}
		return list;
	}
	
	private List<Map<String, Object>> getEquipmentInfoNE3ForCPC(SqlSession session, Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList(session, "equipmentMapper.getEquipmentInfoNE3ForCPC", requestMap);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	private List<Map<String, Object>> getEquipmentInfoNE4ForCPC(SqlSession session, Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList(session, "equipmentMapper.getEquipmentInfoNE4ForCPC", requestMap);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	private boolean deleteEquipmentInfoForCPC(SqlSession session) throws ServiceException {
		boolean flag = false;
		try {
			flag = myBatisDao.delete(session, "equipmentMapper.deleteEquipmentInfoForCPC");
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return flag;
	}
}
