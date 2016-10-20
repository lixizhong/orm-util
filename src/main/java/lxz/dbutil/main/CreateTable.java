package lxz.dbutil.main;

import com.alibaba.fastjson.JSONObject;
import lxz.util.xls.reader.CellReaderSetting;
import lxz.util.xls.reader.RowReader;
import lxz.util.xls.reader.XlsReader;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读取excel，生成建表语句
 */
public class CreateTable {
	public static void main(String[] args) throws Exception{
		
		List<CellReaderSetting> cellList = new ArrayList<CellReaderSetting>();
    	
    	cellList.add(new CellReaderSetting(0, "name"));
    	cellList.add(new CellReaderSetting(1, "text"));
    	cellList.add(new CellReaderSetting(2, "type"));
    	cellList.add(new CellReaderSetting(3, "null"));
    	cellList.add(new CellReaderSetting(4, "default"));
    	cellList.add(new CellReaderSetting(5, "comment"));
    	
    	System.out.println("CREATE TABLE `` (");
		
    	List<JSONObject> dataList = XlsReader.readXls("D:\\项目资料\\母婴三期\\数据字典3.1.4.xlsx", 0, 33, 53, cellList, new RowReader<JSONObject>() {
			public JSONObject getRowValue(Map<String, Object> cells) {
				
				String name = cells.get("name").toString();
				String text = cells.get("text").toString();
				String type = cells.get("type").toString();
				String isNull = cells.get("null").toString();
				String defaultValue = cells.get("default").toString();
				String comment = cells.get("comment").toString();
				
				if("N".equals(isNull)){
					isNull = "NOT NULL";
				}else if("Y".equals(isNull)){
					isNull = "\t";
				}else{
					throw new RuntimeException(name + "是否为空未指定");
				}
				
				if("id".equals(name)){
					defaultValue = "AUTO_INCREMENT";
				}else{
					if(StringUtils.isNotBlank(defaultValue)){
						defaultValue = "DEFAULT '" + defaultValue + "'";
					}else if( ! "NOT NULL".equals(isNull)){
						defaultValue = "DEFAULT NULL";
					}
				}
				
				System.out.println(
					"`" + name + "`" 	+ "\t" + 
					type 				+ "\t" + 
					isNull 				+ "\t" + 
					defaultValue 		+ "\t" + 
					"COMMENT '" + text + ":" + comment + "',");
				
				/*
				 * `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                  `store_code` varchar(20) NOT NULL COMMENT '店铺编码',
                  `category3` varchar(100) DEFAULT NULL COMMENT '三级品类',
                  `brand_code` varchar(100) DEFAULT NULL COMMENT '品牌',
                  `sku` bigint(20) DEFAULT NULL COMMENT 'SKU',
                  `source` tinyint(1) DEFAULT NULL COMMENT '来源 1：采购 2：手工录入',
				 */
				
				JSONObject jsonObject = new JSONObject();
				return jsonObject;
			}
		});
    	
    	System.out.println("PRIMARY KEY (`id`)\r\n" +
    			") ENGINE=InnoDB DEFAULT CHARSET=utf8");
		
	}
}
