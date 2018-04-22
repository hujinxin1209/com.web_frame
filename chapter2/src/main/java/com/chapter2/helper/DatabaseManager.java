package com.chapter2.helper;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chapter2.service.CustomerService;
import com.chapter2.util.CollectionUtil;
import com.chapter2.util.PropsUtil;
import com.mysql.jdbc.Connection;

public final class DatabaseManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);
	
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();
	private static Connection conn = null;
	
	
	static {
		Properties conf = PropsUtil.loadProps("db.properties");
		DRIVER = conf.getProperty("jdbc.driver");
		URL = conf.getProperty("jdbc.url");
		USERNAME = conf.getProperty("jdbc.username");
		PASSWORD = conf.getProperty("jdbc.password");
		
		try {
			Class.forName(DRIVER);
		} catch(ClassNotFoundException e) {
			LOGGER.error("can not load driver", e);
		}
	}
	
	public static Connection getConnection() {
		
		try {
			conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch(SQLException e) {
			LOGGER.error("execute sql failuer", e);
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
				LOGGER.error("close connection failuer", e);
			}
		}
	}
	
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
		List<T> entityList;
		try {
			entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
		} catch(SQLException e) {
			LOGGER.error("query entity list failuer", e);
			throw new RuntimeException();
		} finally {
			closeConnection(conn);
		}
		return entityList;
	}
	
	public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
		T entity;
		try {
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
		} catch(SQLException e) {
			LOGGER.error("query entity failure", e);
			throw new RuntimeException();
		} finally {
			closeConnection(conn);
		}
		return entity;
	}
	public static List<Map<String, Object>> executeQuery(String sql, Object... params){
		List<Map<String, Object>> result;
		try {
			result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
		} catch(SQLException e) {
			LOGGER.error("excute query failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	public static int executeUpdate(String sql, Object... params) {
		int rows = 0;
		try {
			rows = QUERY_RUNNER.update(conn, sql, params);
		} catch(SQLException e) {
			LOGGER.error("execute update failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return rows;
	}
	
	private static <T> String getTableName(Class<T> entityClass) {
		return entityClass.getSimpleName().toUpperCase();
	}
	
	// 插入实体
	public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
		if(CollectionUtil.isEmpty(fieldMap)) {
			LOGGER.error("can not insert entity: fieldMap is empty");
			return false;
		}
		String sql = "insert into " + getTableName(entityClass);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for(String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}
		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		sql += columns + " VALUES " + values;
		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}
	// 更新实体
	public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
		if(CollectionUtil.isEmpty(fieldMap)) {
			LOGGER.error("can not update entity: fieldMap is empty");
			return false;
		}
		String sql = "update " + getTableName(entityClass) + " set ";
		StringBuilder columns = new StringBuilder();
		for(String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append("=?, ");
		}
		sql += columns.substring(0, columns.lastIndexOf(", ")) + " where id = ?";
		List<Object> paramList = new ArrayList<Object>();
		
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();
		return executeUpdate(sql, id) == 1;
	}
	public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
		String sql = "delete from " + getTableName(entityClass) + " where id = ?";
		return executeUpdate(sql, id) == 1;
	}
}
