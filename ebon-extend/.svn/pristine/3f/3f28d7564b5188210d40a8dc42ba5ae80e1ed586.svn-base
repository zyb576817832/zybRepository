package com.ebon.rpc.sap.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.DateHelper;
import com.ebon.rpc.sap.impl.ThreeFeeProcessor;
import com.ebon.rpc.sap.service.IThreeFeeService;
import com.ebon.rpc.sap.util.SapInvoker;
import com.sap.mw.jco.JCO.Table;

@Service
public class ThreeFeeService extends BaseService implements IThreeFeeService {
	
	@Autowired
	private SapInvoker	sapInvoker;
	
	private Date		date	= new Date();
	
	@Override
	@SuppressWarnings("deprecation")
	public void getThreeFee() {
		List<Map<String, Object>> dataList = null;
		try {
			ThreeFeeProcessor processor = new ThreeFeeProcessor(2007, date);
			sapInvoker.validate();
			Table table = (Table) sapInvoker.execute(processor);
			dataList = new ArrayList<Map<String, Object>>();
			do {
				dataList.add(this.setTableToMap(table));
			} while (table.nextRow());
			
		} catch (Exception e) {
			log.error(e);
		}
		if (null != dataList && dataList.size() > 0) {
			SqlSession session = super.getSession();
			try {
				this.delete(session);
				for (Map<String, Object> map : dataList) {
					super.myBatisDao.save(session, "sapCostMapper.insert", map);
				}
			} catch (Exception e) {
				log.error(e);
			} finally {
				super.closeSession(session);
			}
		}
	}
	
	@Override
	@SuppressWarnings("deprecation")
	@Transactional
	public void getThreeFeeAllData() throws ServiceException {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction tx = null;
		try {
			session = super.getSession();
			tx = transactionFactory.newTransaction(session.getConnection());
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			for (int year = 2007; year <= date.getYear() + 1900; year++) {
				System.out.println("现在正在抽取" + year + "年的SAP数据");
				ThreeFeeProcessor processor = new ThreeFeeProcessor(year, dataFormat.parse(year + "1231"));
				sapInvoker.validate();
				Table table = (Table) sapInvoker.execute(processor);
				do {
					dataList.add(this.setTableToMap(table));
				} while (table.nextRow());
			}
			if (null != dataList && dataList.size() > 0) {
				this.deleteAllSapData(session);
				for (Map<String, Object> map : dataList) {
					super.myBatisDao.save(session, "sapCostMapper.insert", map);
				}
			}
			tx.commit();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (SQLException e1) {
				log.error(e1);
				throw new ServiceException();
			}
			log.error(e);
			throw new ServiceException();
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
	
	private void delete(SqlSession session) throws Exception {
		String fristDate = DateHelper.getYearFristDay(date);
		String lastDate = DateHelper.getYearLastDay(date);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fristDate", fristDate);
		map.put("lastDate", lastDate);
		super.myBatisDao.delete(session, "sapCostMapper.delete", map);
	}
	
	public void deleteAllSapData(SqlSession session) throws Exception {
		String fristDate = "2007-1-1";
		String lastDate = DateHelper.getYearLastDay(date);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fristDate", fristDate);
		map.put("lastDate", lastDate);
		super.myBatisDao.delete(session, "sapCostMapper.delete", map);
	}
	
	private Map<String, Object> setTableToMap(Table table) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		String flag = table.getField("SHKZG").getString(); //借贷标示
		String postingDate = table.getField("BUDAT").getString(); //过账日期
		String glAccount = table.getField("KSTAR").getString(); //费用名称（成本要素）
		String costCenter = table.getField("KOSTL").getString().substring(4); //成本中心
		String internalOrderNO = table.getField("AUFNR").getString(); //Order 号码
		String orderDesc = table.getField("KTEXT").getString(); //凭证行项目短文本(项目编号)
		String docHeadText = table.getField("BKTXT").getString(); //凭证头短文本
		
		String docType = table.getField("BLART").getString(); //凭证类型
		String transactionCurrency = table.getField("TWAER").getString(); //币种
		String transactionValue = table.getField("WTGBTRT").getString().replaceAll(",", "").trim(); //文本
		String coValue = table.getField("WKGBTR").getString(); //凭证金额
		
		dataMap.put("flag", flag);
		dataMap.put("postingDate", postingDate);
		dataMap.put("glAccount", glAccount);
		dataMap.put("costCenter", costCenter);
		dataMap.put("internalOrderNO", internalOrderNO);
		dataMap.put("projectNo", orderDesc);
		dataMap.put("docHeadText", docHeadText);
		dataMap.put("docType", docType);
		dataMap.put("transactionCurrency", transactionCurrency);
		dataMap.put("transactionValue", transactionValue);
		dataMap.put("coValue", coValue);
		
		return dataMap;
	}
	
	@Override
	public List<Map<String, Object>> getSAPInfo(Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("sapCostMapper.getSAPInfo", requestMap);
			log.info("在执行com.ebon.rpc.sap.service.impl.ThreeFeeService时,查出的记录为" + list.size() + "条");
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getSAPInfoForNonProject(Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("sapCostMapper.getSAPInfoForNonProject", requestMap);
			log.info("在执行com.ebon.rpc.sap.service.impl.ThreeFeeService时,查出的记录为" + list.size() + "条");
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	public SapInvoker getSapInvoker() {
		return sapInvoker;
	}
	
	public void setSapInvoker(SapInvoker sapInvoker) {
		this.sapInvoker = sapInvoker;
	}
	
}
