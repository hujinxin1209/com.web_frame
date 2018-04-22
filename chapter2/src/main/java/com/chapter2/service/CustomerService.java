package com.chapter2.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.chapter2.helper.DatabaseManager;
import com.chapter2.model.Customer;
import com.chapter2.util.PropsUtil;
import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
	public List<Customer> getCustomerList(){
		Connection conn = DatabaseManager.getConnection();
		try {
//			List<Customer> customers = new ArrayList<Customer>();
//			String sql = "select * from customer";
//			PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
//			ResultSet result = stmt.executeQuery();
//			while(result.next()) {
//				Customer customer = new Customer();
//				customer.setId(result.getLong("id"));
//				customer.setName(result.getString("name"));
//				customer.setContact(result.getString("contact"));
//				customer.setTelephone(result.getString("telephone"));
//				customer.setEmail(result.getString("email"));
//				customer.setRemark(result.getString("remark"));
//				customers.add(customer);
//			}
			String sql = "select * from customer";
			return DatabaseManager.queryEntityList(Customer.class, sql);
			
		} finally {
			DatabaseManager.closeConnection(conn);
		}

	}

	public Customer getCustomer(long id) {

		return null;
	}

	public boolean createCustomer(Map<String, Object> fieldMap) {
		return DatabaseManager.insertEntity(Customer.class, fieldMap);
	}

	public boolean updateCustomer(long id, Map<String, Object> fieldMap) {

		return DatabaseManager.updateEntity(Customer.class, id, fieldMap);
	}

	public boolean deleteCustomer(long id) {
		
		return DatabaseManager.deleteEntity(Customer.class, id);
	}
}
