package com.ebon.rpc.equipment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.BaseRpc;

public interface IRpcInsertNewNE4Info extends BaseRpc {
	/**
	 * 获得FACILITYID, ORDERID,TIME_NUM AS HOURS,startDate,endDate
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>>  selectNE4Info() throws ServiceException;
	
	/**
	 * 把得到的数据插入新表
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	public boolean insertNE4Time(SqlSession session, Map<String,Object> paramMap)throws ServiceException;
	
	/**
	 * 拆分数据，并进行插入操作
	 * @throws ServiceException
	 */
	public void insert()throws ServiceException;

}
