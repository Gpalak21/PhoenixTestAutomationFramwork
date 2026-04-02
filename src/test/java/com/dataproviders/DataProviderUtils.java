package com.dataproviders;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.api.utils.CSVReaderUtil;
import com.dataproviders.api.bean.UserPOJO;

public class DataProviderUtils {
	
	@DataProvider(name="loginAPIDataProvider", parallel=true)
	public static Iterator<UserPOJO> loginAPIDataProvider() {
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv");
		
	}
	

}
