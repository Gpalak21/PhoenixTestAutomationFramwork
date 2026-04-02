package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.dataproviders.api.bean.UserPOJO;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {

	/*
	 * 
	 * Constructor is private static - static methods! Job: Help me Read the CSV
	 * file and map it to a Bean
	 */

	private CSVReaderUtil() {

	}

	public static Iterator<UserPOJO> loadCSV(String pathOfCSVFile) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);

		CsvToBean<UserPOJO> csvToBean = new CsvToBeanBuilder(csvReader).withType(UserPOJO.class)
				.withIgnoreEmptyLine(true).build();

		List<UserPOJO> dataList = csvToBean.parse();
		return dataList.iterator();

	}

}
