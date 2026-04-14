package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.FakerDataGenerator;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserPOJO;

public class DataProviderUtils {
	
	@DataProvider(name="loginAPIDataProvider", parallel=true)
	public static Iterator<UserPOJO> loginAPIDataProvider() {
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv",UserPOJO.class);
		
	}
	
	@DataProvider(name="CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(fakerCount); 
		Iterator<CreateJobPayload> payloadIterator=FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}
	

}
