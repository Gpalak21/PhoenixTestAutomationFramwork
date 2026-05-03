package com.database.model;

import com.database.dao.CustomerProductDao;

public class DemoRunner {

	public static void main(String[] args) {
		
		CustomerProductDBModel customerProductDBModel = CustomerProductDao.getCustomerProductInfoFromDB(270098);
		
		
		System.out.println(customerProductDBModel);

	}

}
