package lxz.dbutil.util.orm;

import java.util.List;

public class Table{
	private String name;
	private String comment;
	
	private List<Field> fieldList;
	
	public Table(){}
	
	public Table(String name, String comment) {
		super();
		this.name = name;
		this.comment = comment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}
}