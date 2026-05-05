package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	
	private static final String CUSTOMER_PRODUCT_QUERY="""
			select * from tr_customer_product where id  =?;
			""";
	
	
	public static CustomerProductDBModel getCustomerProductInfoFromDB(int tr_customer_prodcut_id) {
		Connection conn;
		PreparedStatement statement;
		ResultSet resultSet;
		CustomerProductDBModel customerProductDBModel = null;
		try {
			 conn=DatabaseManager.getConnection();
			 statement=conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			statement.setInt(1, tr_customer_prodcut_id);
			 resultSet=statement.executeQuery();
			
			while(resultSet.next()) {
				 customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("id"), resultSet.getInt("tr_customer_id"), resultSet.getString("mst_model_id"), resultSet.getString("dop"), resultSet.getString("popurl"), resultSet.getString("imei2"), resultSet.getString("imei1"), resultSet.getString("serial_number"));
				
			}
		} catch (SQLException e) {
			
			System.err.println(e.getMessage());
		}
		
		return customerProductDBModel;
	}

}
