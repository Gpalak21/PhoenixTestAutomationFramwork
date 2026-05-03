package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerAddressDBModel customerAddressDBModel=CustomerAddressDao.getCustomerAddress(269710);
		
		System.out.println(customerAddressDBModel);

	}

}
