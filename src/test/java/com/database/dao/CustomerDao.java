package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {

	private static final String CUSTOMER_DETAIL_QUERY = """
			select * from tr_customer where id=269376
			""";

	public static CustomerDBModel getCustomerInfo() throws SQLException {
		Connection conn = DatabaseManager.getConnection();

		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(CUSTOMER_DETAIL_QUERY);
		CustomerDBModel customerDBModel = null;
		while (result.next()) {
			System.out.println(result.getString("first_name"));
			System.out.println(result.getString("email_id"));

			customerDBModel = new CustomerDBModel(result.getString("first_name"), result.getString("last_name"),
					result.getString("mobile_number"), result.getString("mobile_number_alt"),
					result.getString("email_id"), result.getString("email_id_alt"));
		}
		
		return customerDBModel;
	}

}
