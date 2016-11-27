package input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import assets.Asset;

public class XlsReader {

	@SuppressWarnings("resource")
	public static ArrayList<String[]> readXlsFile(String filePath) throws IOException{
		InputStream ExcelFileToRead = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;
		
		ArrayList<String[]> dataRows = new ArrayList<String[]>();
		
		Iterator<Row> rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			
			String[] rowValues=new String[30];
			int cellCounter=0;
			
			while (cells.hasNext() && cellCounter<rowValues.length){
				cell=(HSSFCell) cells.next();

				if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
					//System.out.println(cell.getStringCellValue());
					rowValues[cellCounter++]=cell.getStringCellValue();
				}
				else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
					//System.out.println(cell.getNumericCellValue());
					rowValues[cellCounter++]=""+cell.getNumericCellValue();
				}
				else{
					rowValues[cellCounter++]="";
				}
			}
			
			dataRows.add(rowValues);
		}
		
		System.out.println("success");
		return dataRows;
	}
	
	
	@SuppressWarnings("resource")
	public static ArrayList<Asset> setFromXlsFile(String filePath, int nbOfColumns) throws Exception{
    	ArrayList<Asset> port=new ArrayList<Asset>();//create a portfolio: an asset list
    	String[] row=new String[nbOfColumns];//user can set the number of columns
		
		InputStream ExcelFileToRead = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow ROW; 
		HSSFCell cell;
		Iterator<Row> rows = sheet.rowIterator();
		
        int counter=0;

		while (rows.hasNext())
		{
			counter++;
			
			ROW=(HSSFRow) rows.next();
			Iterator<Cell> cells = ROW.cellIterator();
			int cellCounter=0;
			
			while (cells.hasNext() && cellCounter<nbOfColumns){
				cell=(HSSFCell) cells.next();

				if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
					row[cellCounter++]=cell.getStringCellValue();
				}
				else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
					row[cellCounter++]=""+cell.getNumericCellValue();
				}
				else{
					row[cellCounter++]="";
				}
			}
			
        	for(int j=cellCounter;j<nbOfColumns;j++){//fill up the row
        		row[j]="";
        	}
        	
        	if(counter>1){//avoid the header
                port.add(new Asset(row));
    		}
		}
		
		return port;
			
	}

}

