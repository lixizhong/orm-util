package lxz.dbutil.sql;

import org.apache.commons.lang3.StringUtils;

public class SqlDelete {
	private String tableName;
	private String logicType;
	
	private StringBuilder whereBuilder = new StringBuilder();

	public SqlDelete(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * where条件
	 * @param whereClause
	 * @return
	 */
	public SqlDelete where(String whereClause){
		whereBuilder.setLength(0);
		whereBuilder.append("where ");
		whereBuilder.append(whereClause);
		return this;
	}
	
	/**
	 * and A = ?
	 * @param clause
	 * @return
	 */
	public SqlDelete and(String clause){
		
		if(whereBuilder.length() == 0){
			throw new RuntimeException("调用and之前必须先调用where方法！");
		}
		
		if(StringUtils.equalsIgnoreCase(logicType, "or")){
			throw new RuntimeException("同一个sql子句不能混用and和or！");
		}
		
		logicType = "and";
		whereBuilder.append(" and ");
		whereBuilder.append(clause);
		
		return this;
	}

	/**
	 * or A = ?
	 * @param clause
	 * @return
	 */
	public SqlDelete or(String clause){
		
		if(whereBuilder.length() == 0){
			throw new RuntimeException("调用or之前必须先调用where方法！");
		}
		
		if(StringUtils.equalsIgnoreCase(logicType, "and")){
			throw new RuntimeException("同一个sql子句不能混用and和or！");
		}
		
		logicType = "or";
		
		whereBuilder.append(" or ");
		whereBuilder.append(clause);
		
		return this;
	}
	
	public String getSql() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("delete from ");
		builder.append(tableName);
		if (whereBuilder.length() == 0) {
			throw new IllegalArgumentException("必须指定删除条件！");
		}
		builder.append(" ");
		builder.append(whereBuilder.toString());
		
		return builder.toString();
	}
	
	@Override
	public String toString(){
		return getSql();
	}
}
