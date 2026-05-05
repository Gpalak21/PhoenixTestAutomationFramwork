package com.database.dao;

import com.database.model.JobHeadDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) {
		
		JobHeadDBModel jobHeadDBModel=JobHeadDao.getJobHeadData(271480);
		
		System.out.println(jobHeadDBModel);

	}

}
