package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;

public class ExcelReaderUtil2 {

	private ExcelReaderUtil2() {

	}

	public static Iterator<UserCredentials> loadTestData(String path) throws IOException {
		// TODO Auto-generated method stub
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);

		XSSFWorkbook myWorkbook = new XSSFWorkbook(is);

		XSSFSheet mySheet = myWorkbook.getSheet("LoginTestData");
		XSSFRow myRow;
		XSSFCell myCell;

		XSSFRow rowHeaders = mySheet.getRow(0);

		int usernameIndex = -1;
		int passwordIndex = -1;
		for (Cell cell : rowHeaders) {
			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
				usernameIndex = cell.getColumnIndex();
			}

			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}
		}

		int lastRowIndex = mySheet.getLastRowNum();
		XSSFRow rowData;
		UserCredentials userCredentials;
		List<UserCredentials> userList = new ArrayList<UserCredentials>();
		for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
			rowData = mySheet.getRow(rowIndex);
			userCredentials = new UserCredentials(rowData.getCell(usernameIndex).toString(),
					rowData.getCell(passwordIndex).toString());
			userList.add(userCredentials);

		}
		return userList.iterator();
	}

}
