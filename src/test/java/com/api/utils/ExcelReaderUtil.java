package com.api.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
		
		XSSFWorkbook myWorkbook = new XSSFWorkbook(is);
		
		XSSFSheet mySheet=myWorkbook.getSheet("LoginTestData");
		XSSFRow myRow;
		XSSFCell myCell;
		
		
		int lastRowIndex=mySheet.getLastRowNum();
		System.out.println(lastRowIndex);
		
		XSSFRow rowHeader = mySheet.getRow(0);
		int lastCellIndex = rowHeader.getLastCellNum()-1;
		System.out.println(lastCellIndex);
		
		for(int rowIndex=0;rowIndex<=lastRowIndex;rowIndex++) {
			for(int colIndex=0;colIndex<=lastCellIndex;colIndex++) {
				myRow = mySheet.getRow(rowIndex);
				myCell= myRow.getCell(colIndex);
				System.out.print(myCell+" ");
				
			}
			System.out.println("");
		}
	}
	
	

}
