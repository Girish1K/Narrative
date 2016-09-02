package com.sciformix.utility;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.OrderedJSONObject;



public class FileUtility {

	public JSONArray getJson(String fileName, int searchSheetNo, int maxColumnSizeOfFile, int defaultGridSize) throws JSONException {
		JSONArray array = new JSONArray();
		
		int count = 0;
		try {
			
			System.out.println("In Print JSOn file Name =>" + fileName);
			FileInputStream inp = new FileInputStream(fileName);
			Workbook workbook = WorkbookFactory.create(inp);
			String[] list = new String[maxColumnSizeOfFile];
		
			Sheet sheet = workbook.getSheetAt(searchSheetNo);

			int temp = 0;
			while (sheet.getRow(temp) == null)
				temp++;
			if(sheet.getNumMergedRegions()>0)
				temp=sheet.getNumMergedRegions();
			Row row = sheet.getRow(temp);
		
			if (sheet.getRow(temp) != null)
				for (int j = 0; j < sheet.getRow(temp)
						.getPhysicalNumberOfCells() && j < list.length; j++) {
					Cell cell = row.getCell(j);
					if(cell!=null )
						switch(cell.getCellType()){
						case Cell.CELL_TYPE_NUMERIC:
							list[j] = ""+cell.getNumericCellValue();
							break;
						case Cell.CELL_TYPE_STRING:
							list[j] = cell.getStringCellValue();
							break;
					    case Cell.CELL_TYPE_BOOLEAN:
					    	list[j] = ""+cell.getBooleanCellValue();
							break;
					    case Cell.CELL_TYPE_FORMULA:
					    	list[j] = cell.getCellFormula();
					    	break;
					    	
					    case Cell.CELL_TYPE_BLANK:
					    	list[j] ="";
					    	break;
						}
					
				}
			
			System.out.println("\n\nArray List length==>>" + list);
			Iterator<Row> rowsIT = sheet.rowIterator();
			rowsIT.next();
			while(temp>0){
				rowsIT.next();
				temp--;
			}
			while (rowsIT.hasNext()) {
				Row row1 = rowsIT.next();

				int i = 0;
				// Iterate through the cells.
				OrderedJSONObject jsonObj = new OrderedJSONObject();
				//for (Iterator<Cell> cellsIT = row1.cellIterator(); cellsIT.hasNext();) {
				for (int k = 0; k <row1.getPhysicalNumberOfCells() && k < list.length; k++) {
					
					Cell cell = row1.getCell(k);
				
					if(cell!=null)
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						jsonObj.put(list[i].replaceAll("[\\W_]", " "),
								cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:

						jsonObj.put(list[i].replaceAll("[\\W_]", ""), cell
								.getStringCellValue().replaceAll("[\\W_]", " "));
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						jsonObj.put(list[i].replaceAll("[\\W_]", ""),
								("" + cell.getBooleanCellValue()).replaceAll(
										"[\\W_]", " "));
						break;
					case Cell.CELL_TYPE_BLANK:
						jsonObj.put(list[i].replaceAll("[\\W_]", ""), cell
								.getStringCellValue().replaceAll("[\\W_]", " "));
						break;
					case Cell.CELL_TYPE_FORMULA:
						jsonObj.put(list[i].replaceAll("[\\W_]", ""), cell
								.getCellFormula().replaceAll("[\\W_]", " "));
						break;
					case Cell.CELL_TYPE_ERROR:
						break;
					}

					i++;
				}
				count++;
				if (!(array.length() < defaultGridSize)) 
					;
				else
					array.put(jsonObj);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("----------total record---------" + count);
		for(Object o:array){
			JSONObject jsonObject=(JSONObject) o;
			System.out.println(jsonObject.keys());
		}
		return array;
	}

      	 
}
