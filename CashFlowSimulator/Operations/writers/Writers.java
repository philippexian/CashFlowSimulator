package writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import processing.Processing;
import calculations.CashflowPf;

public class Writers {
	
	private static String outputAddress="C:\\Users";//default address


	public static void writeTxtOutput(DecimalFormat f, SimpleDateFormat dateF, CashflowPf cashflowPort){
		try {            
            
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(new File(outputAddress+ "\\ResultPort.txt")));
            writer1.write("Date						");
            writer1.write("Outstanding				");
            writer1.write("Interest					");
            writer1.write("Principal               ");
            writer1.write("Default                 ");
            writer1.write("Recovery                ");
            writer1.write("Prepayment              ");
            writer1.write("Payoff                  ");
            writer1.write("\r\n");
            
            for(int j=0;j<cashflowPort.getTableauSize();j++){
                writer1.write(dateF.format(cashflowPort.getTableauDate(j).getTime()));
                writer1.write("                  ");
                writer1.write(f.format(cashflowPort.getTableauOutstandingPort(j)));
                writer1.write("                  ");
                writer1.write(f.format(cashflowPort.getTableauInterestPort(j)));
                writer1.write("                  ");
                writer1.write(f.format(cashflowPort.getTableauPrincipalPort(j)));
                writer1.write("                 ");
                writer1.write(f.format(cashflowPort.getTableauDefaultPort(j)));
                writer1.write("                 ");
                writer1.write(f.format(cashflowPort.getTableauRecoveryPort(j)));
                writer1.write("                 ");
                writer1.write(f.format(cashflowPort.getTableauPrepaymentPort(j)));
                writer1.write("                 ");
                writer1.write(f.format(cashflowPort.getTableauPayoffPort(j)));
                writer1.write("\r\n");
            }
            writer1.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Processing.class.getName()).log(Level.SEVERE, null, ex);
        }
	}//end write txt
	
	
	public static void writeCsvOutput(DecimalFormat f, SimpleDateFormat dateF, CashflowPf cashflowPort){
		File csvFile1 = null;
        BufferedWriter csvWtriter1 = null;
        try {
        	csvFile1 = new File(outputAddress+ "\\ResultCalPort.csv");
        	File parent = csvFile1.getParentFile();
        	if (parent != null && !parent.exists()) {
        		parent.mkdirs();
        	}
        	csvFile1.createNewFile();

        	csvWtriter1 = new BufferedWriter(new FileWriter(csvFile1));
        	
        	// write header
        	List<Object> head = new ArrayList<Object>();
        	head.add("Date");
        	head.add("Outstanding");
        	head.add("Interest");
        	head.add("Principal");
        	head.add("Default");
        	head.add("Recovery");
        	head.add("Prepayment");
        	head.add("Payoff");
        	writeRow(head, csvWtriter1);
        	
        	//write contents
        	for (int j=0;j<cashflowPort.getTableauSize();j++) {
    		    List<Object> row=new ArrayList<Object>();
    			row.add(dateF.format(cashflowPort.getTableauDate(j).getTime()));
    			row.add(f.format(cashflowPort.getTableauOutstandingPort(j)));
    			row.add(f.format(cashflowPort.getTableauInterestPort(j)));
    			row.add(f.format(cashflowPort.getTableauPrincipalPort(j)));
    			row.add(f.format(cashflowPort.getTableauDefaultPort(j)));
    			row.add(f.format(cashflowPort.getTableauRecoveryPort(j)));
    			row.add(f.format(cashflowPort.getTableauPrepaymentPort(j)));
    			row.add(f.format(cashflowPort.getTableauPayoffPort(j)));
    		    writeRow(row, csvWtriter1);
    		}
    		csvWtriter1.flush();
    		
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
        		csvWtriter1.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        		System.out.println("fail to write the csv file!");
        	}
        }
	}//end write csv
	
    
		    //write a row in csv: transient function in writeCsvOutput
		    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
				
		    	for (Object data : row) {
		    		StringBuffer sb = new StringBuffer();
		    		String rowStr = sb.append("\"").append(data).append("\",").toString();
		    		csvWriter.write(rowStr);
		    	}
		    	csvWriter.newLine();
		    }
		    
	@SuppressWarnings("resource")
	public static void writeXlsxOutput(CashflowPf cashflowPort){
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Output");
        
        int rowCount=0;
        
        //create header
        Row head=sheet.createRow(0);
        Cell[] headCells=new Cell[10];
        
        int headCellCount=0;
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Date");
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Outstanding");
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Interest");
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Principal");
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Default");
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Recovery");
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Prepayment");
        
        headCells[headCellCount]=head.createCell(headCellCount);
        headCells[headCellCount++].setCellValue("Payoff");
        
        //write contents
         
        for (int j=0;j<cashflowPort.getTableauSize();j++) {
        	
            Row row = sheet.createRow(j+1);
             
            int columnCount = 0;
            
            Cell[] cells=new Cell[10];
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauDate(j));
            
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauOutstandingPort(j));
            
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauInterestPort(j));
            
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauPrincipalPort(j));
            
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauDefaultPort(j));
            
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauRecoveryPort(j));
            
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauPrepaymentPort(j));
            
            cells[columnCount] = row.createCell(columnCount);
            cells[columnCount++].setCellValue(cashflowPort.getTableauPayoffPort(j));
            
            rowCount++;
             
        }
         
        //exception treatments 
        try (FileOutputStream outputStream = new FileOutputStream(outputAddress+"\\Result.xlsx")) {
            try {
				workbook.write(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("A problem arises at row "+rowCount);
			}
        } catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("cannot find the file!");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("A problem arises at row "+rowCount);
		} 
	}
	
	

	public static String getOutputAddress() {
		return outputAddress;
	}


	public static void setOutputAddress(String outputAddress) {
		Writers.outputAddress = outputAddress;
	}
}
