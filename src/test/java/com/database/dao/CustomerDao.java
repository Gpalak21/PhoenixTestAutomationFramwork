package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {

	private static final String CUSTOMER_DETAIL_QUERY = """
			select * from tr_customer where id=?
			""";

	public static CustomerDBModel getCustomerInfo(int customerId){
		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet result ;
		CustomerDBModel customerDBModel = null;
		try {
			conn = DatabaseManager.getConnection();

			preparedStatement = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
		preparedStatement.setInt(1, customerId);
		result	= preparedStatement.executeQuery();
		
		while (result.next()) {
			System.out.println(result.getString("first_name"));
			System.out.println(result.getString("email_id"));

			customerDBModel = new CustomerDBModel(result.getInt("id"),result.getString("first_name"), result.getString("last_name"),
					result.getString("mobile_number"), result.getString("mobile_number_alt"),
					result.getString("email_id"), result.getString("email_id_alt"), result.getInt("tr_customer_address_id"));
		}
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return customerDBModel;
	}

}
