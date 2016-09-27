package lxz.dbutil.sql;

import org.apache.commons.lang3.StringUtils;

public class SqlUpdate {
	private String tableName;
	private String logicType;
	
	private StringBuilder columnBuilder = new StringBuilder();
	private StringBuilder whereBuilder = new StringBuilder();

	public SqlUpdate(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 添加需要更新的列
	 * @param column
	 * @return
	 */
	public SqlUpdate addColumn(String column){
		if (columnBuilder.length() > 0) {
			columnBuilder.append(", ");
		}
		
		columnBuilder.append(column);
		
		return this;
	}

	/**
	 * where条件
	 * @param whereClause
	 * @return
	 */
	public SqlUpdate where(String whereClause){
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
	public SqlUpdate and(String clause){
		
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
	public SqlUpdate or(String clause){
		
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
		
		if(columnBuilder.length() == 0){
			throw new IllegalArgumentException("必须指定要更新的列！");
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("update ");
		builder.append(tableName);
		builder.append(" set ");
		builder.append(columnBuilder.toString());
		if (whereBuilder.length() == 0) {
			throw new IllegalArgumentException("必须指定更新条件！");
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
