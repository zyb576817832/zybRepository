package com.ebon.platform.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebon.framework.bo.BusinessObject;
import com.ebon.framework.util.MapUtil;
import com.ebon.framework.util.StringUtil;
import com.ebon.platform.dao.pager.Page;

/**
 * 用来封装业务对象列表数据为视图Grid数据
 * @author wanghui
 *
 * @param <T>
 */
public class CusGridData<T extends BusinessObject> {
	
	private final Log log = LogFactory.getFactory().getInstance(this.getClass());
	
	private int page; // 当前页
	
	private List<Map<String, Object>> rows;		// 保存视图Grid数据
	
	private int total; // 总记录数
	
	/**
	 * 构造EasyUI的TreeGrid需要的视图数据
	 * 其中补充了自定义属性
	 * @param data
	 */
	public CusGridData(List<T> data) {
		this.total = 0;
		this.page = 0;
		this.setRows(data , null, null);
	}
	
	public CusGridData(List<T> data1, List<T> data2, String[] keys){
		this.total = 0;
		this.page = 0;
		this.setRows(data1, data2, keys);
	}
	
	/**
	 * 构造EasyUI的DataGrid需要的视图数据
	 * 其中补充自定义属性
	 * @param page
	 * @param data
	 */
	public CusGridData(Page page, List<T> data) {
		this.total = page.getRecords();
		this.page = page.getPage();
		this.setRows(data, null, null);
	}
	
	public CusGridData(Page page, List<T> data1, List<T> data2, String[] keys){
		this.total = page.getRecords();
		this.page = page.getPage();
		this.setRows(data1, data2, keys);
	}
	
	public int getPage() {
		return page;
	}
	
	public List<Map<String, Object>> getRows() {
		return rows;
	}
	
	public int getTotal() {
		return total;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * 根据业务数据，装配自定义字段
	 * @param data
	 */
	private void setRows(List<T> data1, List<T> data2, String[] keys) {
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		if (StringUtil.isNotNull(data1)) {
			try {
				// 枚举业务对象列表data，将其转换为Map并构造List
				for(T t: data1){
					Map<String, Object> dataMap = MapUtil.getMap4Object(t);
					listData.add(dataMap);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		this.rows = listData;
	}

}
