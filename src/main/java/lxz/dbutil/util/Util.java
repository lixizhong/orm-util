package lxz.dbutil.util;

import org.apache.commons.lang3.StringUtils;

public class Util {
	
	// 将_分割的名称转换为驼峰法命名
	public static String camelName(String input) {
		StringBuilder output = new StringBuilder();

		String[] seg = input.split("_");
		for (int i = 0; i < seg.length; i++) {
			if (i == 0) {
				output.append(seg[i]);
			} else {
				output.append(upperFirstLetter(seg[i]));
			}
		}

		return output.toString();
	}

	private static String upperFirstLetter(String input) {
		if(input.length() == 1){
			return input.toUpperCase();
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
	
	//生成pascal命名风格的变量名称
	public static String pascalName(String name){
		return upperFirstLetter(camelName(name));
	}
	
	public static String javaTypeName(Class<?> clazz){
		return StringUtils.removeStart(clazz.getName(), "java.lang.");
	}
	
	public static String setMethodCode(String name, Class<?> clazz){
		
		if(clazz.equals(Boolean.class)){
			name = StringUtils.removeStart(name, "is");
		}
		
		String propName = camelName(name);
		String methodNameSubfix = pascalName(name);
		
		//判断第二个字母是否大写
		if(propName.length() > 1){
			String secondLetter = StringUtils.mid(propName, 1, 1);
			if(StringUtils.equals(secondLetter, secondLetter.toUpperCase())){
				methodNameSubfix = propName;
			}
		}
		
		return "set" + methodNameSubfix
			+ "("+ javaTypeName(clazz) +" " + propName + ")";
	}
	
	public static String getMethodCode(String name, Class<?> clazz){
		String methodNamePrefix = "";
		
		if(clazz.equals(Boolean.class)){
			methodNamePrefix = "is";
		}else{
			methodNamePrefix = "get";
		}
		
		String propName = camelName(name);
		String methodNameSubfix = pascalName(name);
		
		//判断第二个字母是否大写
		if(propName.length() > 1){
			String secondLetter = StringUtils.mid(propName, 1, 1);
			if(StringUtils.equals(secondLetter, secondLetter.toUpperCase())){
				methodNameSubfix = propName;
			}
		}
		
		if(clazz.equals(Boolean.class)){
			name = StringUtils.removeStart(name, "is");
		}
		
		return methodNamePrefix + methodNameSubfix + "()"; 
	}
}
