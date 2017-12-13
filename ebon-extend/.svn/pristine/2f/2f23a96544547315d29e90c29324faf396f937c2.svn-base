package com.ebon.framework.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebon.framework.bo.GroupObject;

public class MapUtil {
	/**
	 * 将VO的List转化为指定属性值作为key键，VO本身作为value的Map
	 * @param fieldName
	 * @param list
	 * @return Map<Object,T>
	 * @throws Exception
	 */
	public static <T> Map<Object, T> getMap4List(String fieldName, List<T> list) throws Exception{
		Map<Object, T> map = new HashMap<Object, T>();
		Method method = null;
		String functionStr = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		for (T t: list){
			if (t != null){
				if (method == null){
					method = t.getClass().getMethod(functionStr);
				}
				Object fieldValue = method.invoke(t);
				map.put(fieldValue, t);
			}
		}
		return map;
	}
	
	/**
	 * 将VO对象转化为一个Map对象
	 * @param data
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public static <T> Map<String,Object> getMap4Object(T data ) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		containObject2Map(data, dataMap, "");
		
		return dataMap;
	}
	
	/**
	 * 将GroupObject清单转换为父子Map结构
	 * @param list
	 * @return Map<String, List<T>>	用父Id标识的子集合
	 * @throws Exception
	 */
	public static<T extends GroupObject> Map<String, List<T>> getMapList4GroupList(List<T> list){
		Map<String, List<T>> childMap = new HashMap<String, List<T>>();
		for (T t : list) {												//对list进行以parentId为key的Map组装
			String parentId = t.getParentId();
			if (!StringUtil.isNotNull(parentId)){
				parentId = null;
			}
			List<T> tList = childMap.get(parentId);
			if (!StringUtil.isNotNull(tList)) {
				tList = new ArrayList<T>();
				childMap.put(parentId, tList);
			}
			tList.add(t);
		}
		return childMap;
	}
	
	/**
	 * 将VO对象属性追加进Map
	 * @param t	VO对象
	 * @param outputMap	输出的Map对象
	 * @param pClassType	VO对象的上级对象类型
	 * @throws Exception
	 * @Author wanghui
	 * @createDate 2017年6月1日 下午10:39:32
	 */
	private static void containObject2Map(Object voObject, Map<String, Object> outputMap, String pClassType) throws Exception {
		Method[] methods = voObject.getClass().getMethods();
		for (Method method : methods){
			String methodName = method.getName();
			if (methodName.startsWith("get") && !methodName.equals("getClass")){
				Class<?> fieldType = method.getReturnType();
				//获取属性类型
				String fieldTypeName = fieldType.getSimpleName();
				//如果是基本类型
				if (isBasicType(fieldTypeName)) {
					/**
					 * 将此属性纪录到Map中
					 */
					String fieldName = methodName.substring(3);
					String mapKey = "";
					if (StringUtil.isNotNull(pClassType)) {
						mapKey = CamelCaseUtils.camelToUnderlineToUpperCase(pClassType) + "_";
					}
					if(! "ParentId".equals(fieldName)) {
						mapKey += CamelCaseUtils.camelToUnderlineToUpperCase(fieldName);
					} else {
						mapKey += "parentId";
					}
					//获取对象的特定属性
					Object value = method.invoke(voObject, new Object[]{});
					outputMap.put(mapKey, value);
				}
				//如果是集合类型则忽略，最后剩下对象类型
				else if (!isCollectionType(fieldTypeName)) {
					//获取对象属性
					Object value = method.invoke(voObject, new Object[]{});
					//将对象属性的属性追加进Map
					if(StringUtil.isNotNull(value)) {
						/**
						 * 嵌套调用：将vo对象追加进Map
						 */
						containObject2Map(value, outputMap, fieldTypeName);
					}
				}
			}
		}
	}
	
	private static boolean isBasicType(String typeName) {
		List<String> listName = Arrays.asList("int", "Integer", "String", "long", "Long"
				, "short", "Short", "boolean", "Boolean", "byte", "Byte", "float", "Float"
				, "double", "Double", "char", "Character");
		
		if (listName.indexOf(typeName) > -1){
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean isCollectionType(String typeName) {
		List<String> listName = Arrays.asList("List", "ArrayList", "Map", "HashMap");
		if (listName.indexOf(typeName) > -1){
			return true;
		}else{
			return false;
		}
	}
}
