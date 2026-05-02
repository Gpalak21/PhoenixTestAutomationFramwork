package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBData=CustomerDao.getCustomerInfo();
		System.out.println(customerDBData);
		System.out.println(customerDBData.getFirst_name());
		Customer customer = new Customer("Rosa", "Little", "779-883-1802", "", "Vincenzo_Schuster15@yahoo.com", "");
		Assert.assertEquals(customerDBData.getFirst_name(), customer.first_name());

	}

}
