package com.charge.interceptor;

import com.charge.pojo.common.Page;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class MybatisPageInterceptor implements Interceptor {

	private static String PAGE_MAPPER_ID = "pageMapperId";

	private static String PAGE_DATABASE_TYPE = "pageDatabaseType";

	private String pageMapperId;
	// 数据库类型，不同的数据库有不同的分页方法
	private String pageDatabaseType;

	/**
	 * 拦截后要执行的方法
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
		// 通过反射获取到当前RoutingStatementHandler对象的delegate属性
		StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(statementHandler, "delegate");

		// 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
		if (!"".equals(pageMapperId) && mappedStatement.getId().matches(pageMapperId)) {
			// 获取到当前StatementHandler的
			// boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
			// RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
			BoundSql boundSql = delegate.getBoundSql();
			// 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
			Object parameterObject = boundSql.getParameterObject();
			// 这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。
			Page<?> page = null;
			if (parameterObject instanceof Page<?>) {
				page = (Page<?>) parameterObject;
			} else if (parameterObject instanceof Map<?, ?>) {
				Map<?, ?> parameterMap = (Map<?, ?>) parameterObject;
				for (Map.Entry<?, ?> entry : parameterMap.entrySet()) {
					if (entry.getValue() instanceof Page<?>) {
						page = (Page<?>) entry.getValue();
						break;
					}

				}
			}
			if (page == null) {
				throw new NullPointerException("page parameter is null");
			}

			// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
			String sql = boundSql.getSql();
			// 给当前的page参数对象设置总记录数
			String countSql = getCountSql(sql);
			BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
					boundSql.getParameterMappings(), parameterObject);
			copyAdditionalParameters(countBoundSql, boundSql);
			// 重写sql
			String pageSql = getPageSql(page, sql);
			ReflectUtil.setField(boundSql, "sql", pageSql);
		}
		// 将执行权交给下一个拦截器
		return invocation.proceed();
	}

	private void copyAdditionalParameters(BoundSql target, BoundSql source) {
		List<ParameterMapping> parameterMappings = source.getParameterMappings();
		if (parameterMappings != null) {
			for (ParameterMapping parameter : parameterMappings) {
				if (source.hasAdditionalParameter(parameter.getProperty())) {

					target.setAdditionalParameter(parameter.getProperty(),
							source.getAdditionalParameter(parameter.getProperty()));
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private void setTotalRecord(Page<?> page, MappedStatement mappedStatement, Connection connection, String sql) {

	}

	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return Mysql数据库分页语句
	 */
	private String getMysqlPageSql(Page<?> page, String sql) {
		StringBuilder pageSql = new StringBuilder(100);
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		pageSql.append(sql);
		int offset = (page.getCurrentPage() - 1) * page.getPageSize();
		if (offset < 0) {
			offset = 0;
		}
		pageSql.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return pageSql.toString();
	}

	/**
	 * 获取Oracle数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return Oracle数据库的分页查询语句
	 */
	public String getOraclePageSql(Page<?> page, String sql) {
		StringBuilder pageSql = new StringBuilder(100);
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
		String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());
		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		pageSql.append(sql);
		pageSql.append(" ) temp where rownum <= ").append(endrow);
		pageSql.append(") where row_id > ").append(beginrow);
		return pageSql.toString();
	}

	/**
	 * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
	 * 
	 * @param page
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return
	 */
	private String getPageSql(Page<?> page, String sql) {
		if ("mysql".equalsIgnoreCase(pageDatabaseType)) {
			return getMysqlPageSql(page, sql);
		} else if ("oracle".equalsIgnoreCase(pageDatabaseType)) {
			return getOraclePageSql(page, sql);
		}
		return sql;
	}

	private String getCountSql(String sql) {
		final Matcher matcher = Pattern.compile("((?i)order by)").matcher(sql);
		int lastOrderIndex = -1;
		while (matcher.find()) {
			lastOrderIndex = matcher.start();
		}
		if (lastOrderIndex != -1) {
			sql = sql.substring(0, lastOrderIndex);
		}
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select count(1) from ( ").append(sql).append(" ) cnt");
		return sqlBuilder.toString();
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的
		// 次数
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	/**
	 * 设置注册拦截器时设定的属性
	 */
	public void setProperties(Properties properties) {
		pageDatabaseType = properties.getProperty(PAGE_DATABASE_TYPE);
		pageMapperId = properties.getProperty(PAGE_MAPPER_ID);
		if (pageMapperId == null || "".equals(pageMapperId)) {
			pageMapperId = "";
		}
	}

	private static class ReflectUtil {

		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (Exception e) {
				}
			}
			return field;
		}

		public static void setField(Object obj, String fieldName, Object fieldValue) {
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					if (!Modifier.isPublic(field.getModifiers())) {
						field.setAccessible(true);
					}
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}
		}

		public static Object getFieldValue(Object obj, String fieldName) {
			Object fieldValue = null;
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					if (!Modifier.isPublic(field.getModifiers())) {
						field.setAccessible(true);
					}
					fieldValue = field.get(obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			return fieldValue;
		}
	}

}
