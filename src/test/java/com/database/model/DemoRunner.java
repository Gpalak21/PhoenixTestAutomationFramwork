package com.database.model;

import com.database.dao.CustomerProductDao;
import com.database.dao.MapJobProblemDao;

public class DemoRunner {

	public static void main(String[] args) {
		
		MapJobProblemDBModel mapJobProblemDBModel=MapJobProblemDao.getProblemDetails(270435);
		
		
		System.out.println(mapJobProblemDBModel);

	}

}
