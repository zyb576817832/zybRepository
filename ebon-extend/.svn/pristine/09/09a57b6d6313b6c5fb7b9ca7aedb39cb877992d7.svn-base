package com.ebon.platform.dao.pager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebon.platform.dao.dialect.Dialect;
import com.ebon.platform.dao.dialect.OracleDialect;
import com.ebon.platform.util.ReflectHelper;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

	protected static Logger log = LoggerFactory
			.getLogger(PaginationInterceptor.class);

	private String dialect = "oracle"; // 数据库方言

	@Override
	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			BoundSql boundSql = delegate.getBoundSql();
			Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
			if (parameterObject != null) {
				Page page = null;
				Connection connection = (Connection) ivk.getArgs()[0];
				if (parameterObject instanceof Page) { // 第二个参数就是Page实体
					page = (Page) parameterObject;
					this.setLimitSqlToBoundSql(connection, boundSql, mappedStatement, parameterObject, page);
				} else { // 第三个参数为Page实体
					MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
					try{
					page = (Page) metaStatementHandler.getValue("delegate.rowBounds");
						this.setLimitSqlToBoundSql(connection, boundSql, mappedStatement, parameterObject, page);
					} catch (Exception e) {
						log.warn(e.getMessage());
					}
				}
			}
		}
		return ivk.proceed();
	}
	
	
	private void setLimitSqlToBoundSql(Connection connection, BoundSql boundSql, MappedStatement mappedStatement, Object parameterObject, Page page) throws Throwable {
		String pageSql = null;
		if(dialect.equals("oracle")) {
			Dialect odialect = new OracleDialect();
			String sql = boundSql.getSql();
			String countSql = odialect.getCountSql(sql); // 记录统计
			int count = this.getCount(connection, boundSql, mappedStatement, parameterObject, countSql);
			page.setRecords(count);
			pageSql = odialect.getLimitSql(sql, page);
		}
		ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
	}
	
	private int getCount(Connection connection, BoundSql boundSql, MappedStatement mappedStatement, Object parameterObject, String countSql) throws Throwable {
		PreparedStatement countStmt = connection.prepareStatement(countSql);
		BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
										boundSql.getParameterMappings(), parameterObject);
		setParameters(countStmt, mappedStatement, countBS, parameterObject);
		ResultSet rs = countStmt.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		rs.close();
		countStmt.close();
		return count;
	}

	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.
	 * DefaultParameterHandler
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	@Override
	public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	@Override
	public void setProperties(Properties p) {
	}

}
