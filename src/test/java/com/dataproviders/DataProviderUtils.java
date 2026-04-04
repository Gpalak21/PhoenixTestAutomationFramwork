package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserPOJO;

public class DataProviderUtils {
	
	@DataProvider(name="loginAPIDataProvider", parallel=true)
	public static Iterator<UserPOJO> loginAPIDataProvider() {
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv",UserPOJO.class);
		
	}
	
	@DataProvider(name="createJobDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {
	Iterator<CreateJobBean> createJobBeanIterator=CSVReaderUtil.loadCSV("testData/CreateJob.csv", CreateJobBean.class);
	List<CreateJobPayload> payloadList = new ArrayList<>();
	
	CreateJobBean tempBean;
	CreateJobPayload tempPayload;
	while(createJobBeanIterator.hasNext()) {
		tempBean=createJobBeanIterator.next();
		tempPayload=CreateJobBeanMapper.mapper(tempBean);
		payloadList.add(tempPayload);
	}
	
	return payloadList.iterator();
	
	
	}
	

}
