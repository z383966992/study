package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 此类用来生成mybatis需要的xml，model，dao
 * @author liangliangzhou
 * 
 * 从数据库当中读取数据并生成
 */
public class IbatisGenerationUtil {
	//dao，model等生成之后存放的package的名称
	private String packages = "com.sankuai.zebramultidatasource.zebra";
	//数据库用户名
	private String username="root";
	//数据库密码
	private String password="123456";
	//目标数据库名
	private String database="tdb";
	//数据库url
	private String url="jdbc:mysql://localhost:3306/" + database + "?characterEncoding=utf8";
	//数据库驱动
	private String driverName="com.mysql.cj.jdbc.Driver";
	//数据库连接
	private Connection conn = null;
	//生成的model的原始名称
	private String initModelName = "";
	//生成的model的名称
	private String modelName = "";
	//生成的mapper的名称
	private String mapperName = "";
	//生成的Dao的名称
	private String daoName = "";
	//项目的地址
	String basePath = "";
	//生成的model的存放地址
	private String sourcePath = "src.main.java";
	//生成的mapper的存放地址
	private String mapperPath = "src.main.resources.mapping";
	//获得数据库连接
	private void init() throws Exception{
		//获得数据库连接
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, username, password);
		//计算生成文件存放地址
		basePath = System.getProperty("user.dir");
		String [] sourceDirs = sourcePath.split("\\.");
		int sourceSize = sourceDirs.length;
		sourcePath = basePath;
		for(int i=0; i < sourceSize; i++) {
			sourcePath = sourcePath + File.separator + sourceDirs[i];
		}
		
		String [] mapperDirs = mapperPath.split("\\.");
		int mapperSize = mapperDirs.length;
		mapperPath = basePath;
		for(int i=0; i < mapperSize; i++) {
			mapperPath = mapperPath + File.separator + mapperDirs[i];
		}
		
		File files = new File(sourcePath);
		if(!files.exists()) {
			files.mkdirs();
		}
		
		File filem = new File(mapperPath);
		if(!filem.exists()) {
			filem.mkdirs();
		}
	}
	
	//获得数据库表
	private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString(1);
            tables.add(tableName);
        }
        return tables;
    }
	
	/**
	 * 生成model和mapper的名称（类名）
	 * model的类名和mapper文件的名称
	 * @param tableName
	 */
	private void genModelAndDaoName(String tableName) {
        StringBuffer sb = new StringBuffer(tableName.length());
        String tableNew = tableName.toLowerCase();
        String[] tables = tableNew.split("_");
        for(int i=0; i<tables.length; i++) {
        	sb.append(changeFirstLetterToUpperCase(tables[i]));
        }
        initModelName = sb.toString().trim();
        modelName = initModelName + "Model";
        mapperName = initModelName + "Mapper";
        daoName = initModelName + "Dao";
System.out.println(initModelName);
System.out.println(modelName);
System.out.println(mapperName);
System.out.println(daoName);
    }
	
	/**
	 * 此方法用来获得table和table的comment，用来生成model的注释信息
	 * @return
	 */
	private Map<String, String> getTableComment() throws Exception{
		Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
	}
	
	public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
	
	
	/**
	 * 此方法用来生成model
	 * @param columnName 
	 *        数据库字段的名字，用来生成model的属性
	 * @param columnType
	 *        数据库字段的类型，用来生成model属性类型
	 * @param columnComment
	 *        数据库字段的说明，用来生成model属性的注释信息
	 * @param tableComment
	 *        数据库表字段的说明，用来生成model类的注解
	 */
	private void genModel(List<String> columnName, List<String> columnType, List<String> columnComment, String tableComment) throws Exception{
		String modelPath = packages + ".model";
		String [] dirs = modelPath.split("\\.");
		int paths = dirs.length;
		modelPath = sourcePath;
		for(int i=0; i<paths; i++) {
			modelPath = modelPath + File.separator + dirs[i];
		}
		
		File folder = new File(modelPath);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
        File modelFile = new File(modelPath, modelName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(modelFile)));
        //生成package
        bw.write("package " + packages + ".model" + ";");
        bw.newLine();
        //生成类上的说明
        bw = genClassNote(bw, tableComment);
        //生成类
        bw.write("public class " + modelName + " {");
        bw.newLine();
        //生成类属性
        int size = columnName.size();
        for(int index=0; index < size; index++){
        	String attributeName = columnName.get(index);
        	String type = columnType.get(index);
        	String note = columnComment.get(index);
        	if(!isEmpty(note)){
        		bw.write("\t" + "//" + note);
        		bw.newLine();
        	}
        	bw.write("\t" + "private " + genAttributeType(type) + " " + genAttributeName(attributeName) + ";");
        	bw.newLine();
        }
        bw.newLine();
        //生成属性的getter和setter方法
        for(int index=0; index < size; index++) {
        	String attributeName = columnName.get(index);
        	String type = columnType.get(index);
        	//生成get方法
        	bw.write("\tpublic " + genAttributeType(type) + " " + "get" + genMethodName(attributeName) + "() {" );
        	bw.newLine();
        	bw.write("\t\t" + "return " + genAttributeName(attributeName) + ";");
        	bw.newLine();
        	bw.write("\t}");
        	bw.newLine();
        	//生成set方法
        	bw.write("\tpublic " + "void " + "set" + genMethodName(attributeName) + "(" + genAttributeType(type) + " " + genAttributeName(attributeName) + ") {");
        	bw.newLine();
        	bw.write("\t\t" + "this." + genAttributeName(attributeName) + " = " + genAttributeName(attributeName) + ";");
        	bw.newLine();
        	bw.write("\t}");
        	bw.newLine();
        }
        bw.write("}");
        bw.flush();
        bw.close();
	}
	
	/**
	 * 此类用来生成mapper
	 * @param primaryKeyMap
	 *        存放主键的相关信息
	 *        primaryKeyMap.put("attributeName", field); 主键的名字
	 *        primaryKeyMap.put("attributeType", type); 主键的类型
	 * @param columnType
	 * @param columnComment
	 * @param tableComment
	 * @throws Exception
	 */
	private void genDao(Map<String,String> primaryKeyMap) throws Exception{
		
		String daoPath = packages + ".dao";
		String [] dirs = daoPath.split("\\.");
		int paths = dirs.length;
		daoPath = sourcePath;
		for(int i=0; i<paths; i++) {
			daoPath = daoPath + File.separator + dirs[i];
		}
		
		File folder = new File(daoPath);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
		
        File modelFile = new File(daoPath, daoName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(modelFile)));
        //生成package
        bw.write("package " + packages + ".dao" + ";");
        bw.newLine();
        //生成import
        bw.write("import " + packages + ".model.*;");
        bw.newLine();
        bw.write("import org.apache.ibatis.annotations.Param;");
        bw.newLine();
        //生成类上的说明
        bw = genClassNote(bw, "");
        //生成接口
        bw.write("public interface " + daoName  + " {");
        bw.newLine();
        //生成insert方法
        bw.write("\tvoid " + " insert" + initModelName + "(" + modelName + " " + changeFirstLetterToLowerCase(modelName) + ");");
        bw.newLine();
        //生成update方法
        bw.write("\tboolean" + " update" + initModelName + "(" + modelName + " " + changeFirstLetterToLowerCase(modelName) + ");");
        bw.newLine();
        //生成select方法
        bw.write("\t" + modelName + " selectByPrimaryKey" + "(" + "@Param(\"" + genAttributeName(primaryKeyMap.get("attributeName")) + "\") " + genAttributeType(primaryKeyMap.get("attributeType")) + " " + genAttributeName(primaryKeyMap.get("attributeName")) + ");");
        bw.newLine();
        //生成delete方法
        bw.write("\tboolean"  + " deleteByPrimaryKey" + "(" + "@Param(\"" + genAttributeName(primaryKeyMap.get("attributeName")) + "\") " + genAttributeType(primaryKeyMap.get("attributeType")) + " " + genAttributeName(primaryKeyMap.get("attributeName")) + ");");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
	}
	
	/**
	 * 生成xml文件
	 * @param columnName
	 * @param columnType
	 * @param genId
	 *        判断生成插入语句的时候是否插入id
	 */
	private void genMapperXml(List<String> columnName, List<String> columnType, String tableName, Map<String, String> primaryKeyMap, boolean genId) throws Exception{

		String [] dirs = mapperPath.split("\\.");
		int paths = dirs.length;
		mapperPath = "";
		for(int i=0; i<paths; i++) {
			mapperPath = mapperPath + File.separator + dirs[i];
		}
		
		File folder = new File(mapperPath);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
 
        File mapperFile = new File(mapperPath, daoName + "Mapper.xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        //生成xml的固定信息
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper");
        bw.newLine();
        bw.write("PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
        bw.newLine();
        bw.write("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        
        //生成mapper头
        bw.write("<mapper namespace=\"" + packages + ".dao." + daoName + "\">");
        bw.newLine();
        
        //生成resultMap
        bw.write("\t<resultMap id=\"" + changeFirstLetterToLowerCase(initModelName) + "\" type=\"" + packages + ".model." + modelName + "\">");
        bw.newLine();
        int size = columnName.size();
        for(int index =0; index < size; index++) {
        	bw.write("\t\t<result property=\"" + genAttributeName(columnName.get(index))  + "\" column=\"" + columnName.get(index) + "\" />");
        	bw.newLine();
        }
        bw.write("\t</resultMap>");
        bw.newLine();
        bw.newLine();
        
        //生成insert
        bw.write("\t<insert id=\"" + "insert" + initModelName + "\"" + " " + "parameterType=\"" + packages + ".model." + modelName + "\">");
        bw.newLine();
        bw.write("\t\tinsert into " + tableName + "(");
        bw.newLine();
        for(int index =0; index < size; index++) {
        	if(genId == false) {
        		//不生成id
        		if(columnName.get(index).equals(primaryKeyMap.get("attributeName"))) {
        			continue;
        		}
        	}
        	bw.write("\t\t\t" + columnName.get(index));
        	if(!(index == (size-1))) {
        		bw.write(",");
        	}
        	bw.newLine();
        }
        bw.write("\t\t) values (");
        bw.newLine();
        for(int index =0; index < size; index++) {
        	if(genId == false) {
        		//不生成id
        		if(columnName.get(index).equals(primaryKeyMap.get("attributeName"))) {
        			continue;
        		}
        	}
        	bw.write("\t\t\t#{" + genAttributeName(columnName.get(index)) + ", jdbcType=" +  genJdbcType(columnType.get(index)) + "}");
        	if(!(index == (size-1))) {
        		bw.write(",");
        	}
        	bw.newLine();
        }
        bw.write("\t\t)");
        bw.newLine();
        bw.write("\t\t<selectKey keyProperty=\"" + primaryKeyMap.get("attributeName") + "\" resultType=\"" + genAttributeType(primaryKeyMap.get("attributeType"))  + "\">");
        bw.newLine();
        bw.write("\t\t\tSELECT LAST_INSERT_ID() AS value");
        bw.newLine();
        bw.write("\t\t</selectKey>");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        
        //生成update
        bw.newLine();
        bw.write("\t<update id=\"" + "update" + initModelName + "\" parameterType=\"" + packages + ".model." + modelName + "\">");
        bw.newLine();
        bw.write("\t\tupdate " + tableName);
        bw.newLine();
        bw.write("\t\t<set>");
        bw.newLine();
        for(int index =0; index < size; index++) {
        	if(genId == false) {
        		//不生成id
        		if(columnName.get(index).equals(primaryKeyMap.get("attributeName"))) {
        			continue;
        		}
        	}
        	bw.write("\t\t\t<if test=\"" + genAttributeName(columnName.get(index)) + "!= null\">");
        	bw.newLine();
        	bw.write("\t\t\t\t" +  columnName.get(index) + "= #{" + genAttributeName(columnName.get(index)) + "}");
        	if(!(index == (size-1))) {
        		bw.write(",");
        	}
        	bw.newLine();
        	bw.write("\t\t\t</if>");
        	bw.newLine();
        }
        bw.write("\t\twhere " + primaryKeyMap.get("attributeName") + " = #{" + genAttributeName(primaryKeyMap.get("attributeName")) + "}");
        bw.newLine();
        bw.write("\t\t</set>");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        
        //生成select
        bw.newLine();
        bw.write("\t<select id=\"selectByPrimaryKey" + "\"" + " resultMap=\"" + changeFirstLetterToLowerCase(initModelName) + "\">");
        bw.newLine();
        bw.write("\t\tSELECT");
        bw.newLine();
        for(int index=0; index < size; index++) {
        	bw.write("\t\t\t"+columnName.get(index));
        	if(!(index == size-1)) {
        		bw.write(",");
        	}
        	bw.newLine();
        }
        bw.write("\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE " + primaryKeyMap.get("attributeName") + " = #{" + genAttributeName(primaryKeyMap.get("attributeName")) + "}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        
        //生成delete
        bw.newLine();
        bw.write("\t<delete id=\"deleteByPrimaryKey" + "\">");
        bw.newLine();
        bw.write("\t\tDELETE FROM " + tableName + " WHERE " + primaryKeyMap.get("attributeName") + " = #{" + genAttributeName(primaryKeyMap.get("attributeName")) + "}");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.write("</mapper>");
        bw.flush();
        bw.close();
	}
	
	/**
	 * 
	 */
	
	/**
	 * 生成属性类型
	 * @return
	 */
	private String genAttributeType(String type) {
		if ( type.indexOf("char") > -1 ) {
            return "String";
        } else if ( type.indexOf("bigint") > -1 ) {
            return "Long";
        } else if ( type.indexOf("int") > -1 ) {
            return "Integer";
        } else if ( type.indexOf("date") > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf("text") > -1 ) {
            return "String";
        } else if ( type.indexOf("timestamp") > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf("decimal") > -1 ) {
            return "java.math.BigDecimal";
        } else if ( type.indexOf("blob") > -1 ) {
            return "byte[]";
        }
        return null;
	}
	
	/**
	 * 生成insert语句的 jdbcType
	 * @param jdbcType
	 * @return
	 */
	public String genJdbcType(String jdbcType) {
		try {
			if(jdbcType.indexOf("(") != -1) {
				return jdbcType.substring(0, jdbcType.indexOf("(")).toUpperCase();
			} else {
				return jdbcType.toUpperCase();
			}
		} catch (Exception e) {
			System.out.println(jdbcType);
			return null;
		}
	}
	
	/**
	 * 生成model属性名
	 * @param tableName
	 */
	private String genAttributeName(String Str) {
        StringBuffer sb = new StringBuffer();
        String tableNew = Str.toLowerCase();
        String[] tables = tableNew.split("_");
        for(int i=0; i<tables.length; i++) {
        	if (i == 0) {
        		sb.append(changeLetterToLowerCase(tables[i]));
        	} else {
        		sb.append(changeFirstLetterToUpperCase(tables[i]));
        	}
        }
        return sb.toString().trim();
    }
	
	/**
	 * 生成model方法名
	 */
	private String genMethodName(String Str) {
        StringBuffer sb = new StringBuffer();
        String tableNew = Str.toLowerCase();
        String[] tables = tableNew.split("_");
        for(int i=0; i<tables.length; i++) {
        		sb.append(changeFirstLetterToUpperCase(tables[i]));
        }
        return sb.toString().trim();
    }
	
	/**
	 * 生成类上的注释
	 * @return
	 */
	private BufferedWriter genClassNote(BufferedWriter bw, String tableComment) throws Exception{
		bw.write("/**");
        if(!isEmpty(tableComment)) {
        	bw.newLine();
        	bw.write(" * " + tableComment);
        }
        bw.newLine();
        bw.write(" * @author liangliangzhou3");
        bw.newLine();
        bw.write(" * @date " + formatDate(new Date()));
        bw.newLine();
        bw.write(" */");
        bw.newLine();
        return bw;
	}
	
	private String formatDate(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(date);
	}
	
	/**
     * 所有字母都改成小写，包括首字母
     * @param str
     * @return
     */
    private String changeLetterToLowerCase(String str) {
    	str = str.toLowerCase();
        return str.toLowerCase().substring(0, 1).toLowerCase() + str.substring(1);
    }
    
    /**
     * 首字母大写，其他都是小写
     * @param str
     * @return
     */
    private String changeFirstLetterToUpperCase(String str) {
    	str = str.toLowerCase();
    	return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * 首字母改为小心，其他不变
     * @throws Exception
     */
    private String changeFirstLetterToLowerCase(String str) {
    	return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
	
	private void generate() throws Exception{
		init();
		PreparedStatement pstate = null;
		List<String> columnName = new LinkedList<String>();
		List<String> columnType = new LinkedList<String>();
		List<String> columnComment = new LinkedList<String>();
		Map<String,String> tableComment = getTableComment();
		Map<String,String> primaryKeyMap = new HashMap<String,String>();
		List<String> tableNameList = getTables();
		for(String tableName : tableNameList) {
			//获得表中字段的说明
			String sql = "show full fields from " + "`" + tableName + "`";
			pstate = conn.prepareStatement(sql);
			ResultSet rs = pstate.executeQuery();
			while(rs.next()) {
				String field = rs.getString("Field");
				String type = rs.getString("Type");
				String comment = rs.getString("Comment");
				String key = rs.getString("Key");
				columnName.add(field); //字段名称
				columnType.add(type); //字段类型
				columnComment.add(comment); //字段说明--这个用来生成model的说明信息
				if(!isEmpty(key)) {//这个是主键字段
					primaryKeyMap.put("attributeName", field);
					primaryKeyMap.put("attributeType", type);
				}
			}
			//生成model和dao的名称
			genModelAndDaoName(tableName);
			//生成model
			genModel(columnName,columnType, columnComment, tableComment.get(tableName));
			//生成dao
			genDao(primaryKeyMap);
			//生成mapperXml文件
			genMapperXml(columnName,columnType, tableName, primaryKeyMap, false);
			//改写pom文件,增加一些依赖
			rewritePom(false);
			columnName.clear();
			columnType.clear();
			columnComment.clear();
			primaryKeyMap.clear();
			
		}
		
	}

	/**
	 * 改写pom文件，增加一些依赖进去
	 */
	private void rewritePom(boolean flag) throws Exception{
		if (flag == false) {
			return;
		}
		String pomPath = basePath + File.separator + "pom.xml";
		
		BufferedReader br = new BufferedReader(new FileReader(new File(pomPath)));
		StringBuffer sb = new StringBuffer();
		String content = "";
		while((content = br.readLine()) != null) {
			if(!content.trim().equals("<url>http://maven.apache.org</url>")) {
				sb.append(content).append(System.getProperty("line.separator"));
			} else {
				sb.append(content).append(System.getProperty("line.separator")).append(getPomDependencies());
				break;
			}
		}
		while((content = br.readLine()) != null) {
			if(!content.trim().equals("<build>")) {
				continue;
			} else {
				sb.append(content).append(System.getProperty("line.separator"));
				while((content = br.readLine()) != null) {
					sb.append(content).append(System.getProperty("line.separator"));
				}
			}
		}
		
		br.close();
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pomPath)));
		bw.write(sb.toString());
		bw.close();
	}
	
	public static void main(String[] args) throws Exception{
		new IbatisGenerationUtil().generate();
	}
	
	private String getPomDependencies() {
		String dependencys = "\t<properties>" + System.getProperty("line.separator")
		+ "\t\t<spring.version>4.1.8.RELEASE</spring.version>"+ System.getProperty("line.separator")
		+ "\t\t<slf4j.version>1.6.6</slf4j.version>"+ System.getProperty("line.separator")
		+ "\t\t<log4j.version>1.2.12</log4j.version>"+ System.getProperty("line.separator")
		+ "\t\t<mybatis.version>3.2.4</mybatis.version>"+ System.getProperty("line.separator")
	    + "\t</properties>"+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		
	    + "\t<dependencies>"+ System.getProperty("line.separator")
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>junit</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>junit</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>4.12</version>"+ System.getProperty("line.separator")
		+ "\t\t\t<scope>test</scope>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		
		+ "\t\t<dependency> "+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>javax.servlet.jsp</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>javax.servlet.jsp-api</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>2.3.1</version>"+ System.getProperty("line.separator")
		+ "\t\t\t<scope>provided</scope>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>javax.servlet</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>javax.servlet-api</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>3.1.0</version>"+ System.getProperty("line.separator")
		+ "\t\t\t<scope>provided</scope>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>javax.servlet</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>jstl</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.2</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<!-- mysql -->"+ System.getProperty("line.separator")
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>mysql</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>mysql-connector-java</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>5.1.35</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<!-- mybatis核心包 -->"+ System.getProperty("line.separator")
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.mybatis</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>mybatis</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${mybatis.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<!-- mybatis/spring包 -->"+ System.getProperty("line.separator")
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.mybatis</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>mybatis-spring</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.2.2</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency><!-- spring -->"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-context</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-context-support</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-core</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-beans</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-aop</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-tx</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-jdbc</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-web</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-webmvc</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-test</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-aspects</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.aspectj</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>aspectjrt</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.7.1</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.aspectj</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>aspectjweaver</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.7.1</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-expression</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-hibernate</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.2.6</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-hibernate</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.2.6</version>"+ System.getProperty("line.separator")
		+ "\t\t\t<classifier>sources</classifier>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.springframework</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>spring-orm</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${spring.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		
		+ "\t\t<!-- log -->"+ System.getProperty("line.separator")
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>log4j</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>log4j</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${log4j.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.slf4j</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>slf4j-api</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${slf4j.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.slf4j</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>slf4j-log4j12</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>${slf4j.version}</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		
		+ "\t\t<!-- dbcp -->"+ System.getProperty("line.separator")
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>commons-dbcp</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>commons-dbcp</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.4</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>commons-pool</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>commons-pool</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.6</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		
		+ "\t\t<!-- other -->"+ System.getProperty("line.separator")
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.javassist</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>javassist</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>3.19.0-GA</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>org.apache.commons</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>commons-lang3</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>3.4</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>commons-codec</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>commons-codec</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.6</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency> "+ System.getProperty("line.separator")
		
		+ "\t\t<dependency>"+ System.getProperty("line.separator")
		+ "\t\t\t<groupId>com.alibaba</groupId>"+ System.getProperty("line.separator")
		+ "\t\t\t<artifactId>fastjson</artifactId>"+ System.getProperty("line.separator")
		+ "\t\t\t<version>1.2.3</version>"+ System.getProperty("line.separator")
		+ "\t\t</dependency>"+ System.getProperty("line.separator")
		+ "\t</dependencies>"+ System.getProperty("line.separator");
		return dependencys;
	}
}