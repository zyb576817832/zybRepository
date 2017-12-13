package com.ebon.framework.bo;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupObject<T> extends BusinessObject {

	private static final long serialVersionUID = 8895996898914756233L;
	
	protected String parentId;
	
	protected String path;
	
	protected List<T> children;
	
	// 克隆方法
	@Override
	@SuppressWarnings(value = "unchecked")
	public Object clone() throws CloneNotSupportedException {
		try {
			GroupObject<T> result = (GroupObject<T>)super.clone();
			if (children != null) {
				List<T> list = new ArrayList<T>();
				result.setChildren(list);
				if (children.size() > 0){
					if (children.get(0) instanceof GroupObject) {
						for (T t : children){
							list.add((T)((GroupObject<T>)t).clone());
						}
					} else if (children.get(0) instanceof BusinessObject) {
						for (T t : children){
							list.add((T)((BusinessObject)t).clone());
						}
					} else {
						list.addAll(children);
					}
				}
			}
			return result;
		} catch (CloneNotSupportedException e) {
			throw e;
		}
	}
	
	/**
	 * 父节点
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置父节点
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 树结构路径
	 * @return
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置树结构路径
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 子对象集合
	 * @return
	 */
	public List<T> getChildren() {
		return children;
	}
	/**
	 * 设置子对象集合
	 * @param children
	 */
	public void setChildren(List<T> children) {
		this.children = children;
	}
	
}
