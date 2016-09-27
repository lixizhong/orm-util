package lxz.dbutil.util.orm;

public class Field{
	private String name;
	private Class<?> javaType;
	private String comment;
	private String defaultValue;
	private boolean primaryKey;
	private boolean generated;
	private boolean autoIncrement;
	
	public Field(String name, Class<?> javaType, 
			String comment, String defaultValue, 
			boolean primaryKey, boolean generated,
			boolean autoIncrement) {
		super();
		this.name = name;
		this.javaType = javaType;
		this.comment = comment;
		this.defaultValue = defaultValue;
		this.primaryKey = primaryKey;
		this.generated = generated;
		this.autoIncrement = autoIncrement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isGenerated() {
		return generated;
	}

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
}