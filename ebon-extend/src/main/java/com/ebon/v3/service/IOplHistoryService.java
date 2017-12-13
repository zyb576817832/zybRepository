package com.ebon.v3.service;

import java.util.List;

import com.ebon.framework.vo.Result;
import com.ebon.platform.dao.pager.Page;
import com.ebon.v3.vo.OplHistory;

public interface IOplHistoryService {

	/**
	 * opl历史纪录的添加
	 * @param oplHistory
	 * @return
	 */
	public Result add(OplHistory oplHistory);
	/**
	 * 获取该OPLID下的历史纪录
	 * @return
	 */
	public List<OplHistory> getByOplId(String oplId,Page page);
}
