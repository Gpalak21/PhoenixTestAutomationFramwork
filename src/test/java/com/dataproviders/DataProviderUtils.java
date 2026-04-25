package com.dataproviders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserPOJO;

public class DataProviderUtils {

	@DataProvider(name = "loginAPIDataProvider", parallel = true)
	public static Iterator<UserPOJO> loginAPIDataProvider() {
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserPOJO.class);

	}

	@DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(fakerCount);
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}

	@DataProvider(name = "LoginAPIJsonDataProvider", parallel = true)
	public static Iterator<UserCredentials> loginAPIJsonDataProvider() {
		return JsonReaderUtil.loadJSON("testData/loginApiTestData.json", UserCredentials[].class);

	}

	@DataProvider(name = "CreateJobAPIJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIJsonDataProvider() {
		return JsonReaderUtil.loadJSON("testData/CreateJobAPIData.json", CreateJobPayload[].class);

	}

	@DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserPOJO> loginAPIExcelDataProvider() throws IOException {
		return ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx", "LoginTestData", UserPOJO.class);

	}

	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIExcelDataProvider() {
		Iterator<CreateJobBean> iterator = ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);

		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (iterator.hasNext()) {
			tempBean = iterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);
		}
		return payloadList.iterator();

	}
}
