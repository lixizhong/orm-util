package lxz.dbutil.mybatis.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拦截select查询，检查sql where子句中设置的查询条件与给定的参数是否匹配。
 * 2种情况视为错误：
 * 1、where中出现的查询条件，对应的参数相应的属性为null；
 * 2、没有where子句（即没有设置过滤条件），但是参数不为null；
 * @author lixizhong
 *
 */

@Intercepts({
	@Signature(
			  type= StatementHandler.class,
			  method = "query",
			  args = {Statement.class, ResultHandler.class})
	})
public class QueryParamCheckInterceptor implements Interceptor {
	
	private static Logger logger = LoggerFactory.getLogger(QueryParamCheckInterceptor.class);

	@SuppressWarnings("rawtypes")
	public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
		
		RoutingStatementHandler handdler = (RoutingStatementHandler) invocation.getTarget();
		
		BoundSql boundSql = handdler.getBoundSql();
		
		Object po = boundSql.getParameterObject();
		
		MapperMethod.ParamMap paramMap = null;
		
		if(po instanceof MapperMethod.ParamMap){
			paramMap = (MapperMethod.ParamMap) po;
		}else{
			return invocation.proceed();
		}
		
		List<ParameterMapping> pmList = boundSql.getParameterMappings();//解析后的参数映射列表，保存了所有的查询条件
		
		for (ParameterMapping pm : pmList) {
			
			String name = pm.getProperty();
			
			Object value = getBeanPropertyCascade(paramMap, name);
			
			logger.debug("查询参数：{}: {}", name, value);
			
			if(value == null){
				throw new IllegalArgumentException("参数" + name + "不能为null！");
			}		
		}
		
		return invocation.proceed();
	}
	
	//获取bean的属性，包括#{user.userName}的情况
	private Object getBeanPropertyCascade(Object bean, String name) {
		try {
			if(-1 == StringUtils.indexOf(name, ".")){
				return PropertyUtils.getProperty(bean, name);
			}else{
				String name1 = StringUtils.substringBefore(name, ".");
				Object obj = PropertyUtils.getProperty(bean, name1);
				
				if(obj == null){
					return null;
				}
				
				return getBeanPropertyCascade(obj, StringUtils.removeStart(name, name1+"."));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object plugin(Object target) {
		return (target instanceof StatementHandler) ? Plugin.wrap(target, this) : target;
	}

	public void setProperties(Properties properties) {
	}

}
