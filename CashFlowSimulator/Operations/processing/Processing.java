package processing;

import input.CsvReader;
import input.ExcelFileReader;
import input.TxtReader;
import input.XlsReader;

import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import assets.Asset;
import calculations.CashflowPf;
import references.ReferenceOps;
import writers.Writers;


/**
 * @author Zebin (Philippe)
 *
 */
public class Processing {
	
	//general features
	private static String filePath;
	private static String fileType;
	private static String delimiter;

	private static String rateProperty;
	private static String outputFormat;
	private static char timeLine;
	private static String currency;
	private static Calendar dataDate;
	private static int nbDecimal;
	private static int nbOfColumns;
	private static int maxYears;
	private static double errorMargin;
	private static int nbOfDays;

	private static CashflowPf cashflowPort;
	private static ArrayList<Asset> port;

	
	//initiate the references objects
	public static void importReferences() throws Exception{
    	ReferenceOps.initIndices();
    	ReferenceOps.initModels();
	}

	//set portfolio
	//there is no longer the step of importing file or reading file 
	//the file is read in line by line and then the portfolio is set line by line
	public static void setPorfolio() throws Exception {

        long start = System.currentTimeMillis();
        
        //try different setters adapting to different formats

        //set from excel file
        if(fileType.endsWith("xlsx")||fileType.endsWith("xlsm")){
        	
        	ExcelFileReader reader=new ExcelFileReader(filePath);
        	port=reader.setFromXmlFile(nbOfColumns);
        }
        
        else if(fileType.endsWith("xls")){
        	port=XlsReader.setFromXlsFile(filePath, nbOfColumns);
        }
        
        //set from txt file
        else if(fileType.endsWith("txt")){//read txt
        	port=TxtReader.setFromTxtFile(filePath, delimiter, nbOfColumns);
        }
        
        //set from csv file
        else if(fileType.endsWith("csv")){//read csv  	
        	port=CsvReader.setFromCsvFile(filePath, delimiter, nbOfColumns);
        }
        
        else{
        	throw new Exception("bad file!");
        }
        
        
        //setting portfolio time
        System.out.println("portfolio successfully set");
        long end = System.currentTimeMillis();
        double registeringTime = ((double)end - (double)start)/1000;
        System.out.println("The setting process takes: " + registeringTime +"s");       
        
    }
    
    //calculation
    public static void simulateCashflow() throws Exception {    
        
        long start = System.currentTimeMillis();
              
        cashflowPort = new CashflowPf();
        cashflowPort.Calcul();
        
        long end = System.currentTimeMillis();
        double calculTime = ((double)end - (double)start)/1000;
        System.out.println("finish calculating");
        System.out.println("The calculating time is: " + calculTime +"s");
        
    }
    
    
    //output

	public static void writeDoc(String address){//output function
		
		Writers.setOutputAddress(address);
        
        DecimalFormat df = new DecimalFormat();
	    df.setMaximumFractionDigits(nbDecimal);//decimals
	    
	    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");//output date format
	    
	    //output txt
	    if(outputFormat=="txt"){
	        Writers.writeTxtOutput(df, format1, cashflowPort);
	    }
	    
	    
        //output csv	    
	    if(outputFormat=="csv"){
	    	Writers.writeCsvOutput(df, format1, cashflowPort);
	    }
	    
	    //output excel
	    if(outputFormat=="xlsx"){
	    	Writers.writeXlsxOutput(cashflowPort);
	    }

    }//end writing
    

	//getters setters
    public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		Processing.filePath = filePath;
	}

	public static String getFileType() {
		return fileType;
	}

	public static void setFileType(String fileType) {
		Processing.fileType = fileType;
	}

	public static String getDelimiter() {
		return delimiter;
	}

	public static void setDelimiter(String delimiter) {
		Processing.delimiter = delimiter;
	}

	public static int getDecimal() {
		return nbDecimal;
	}

	public static void setDecimal(int decimal) {
		Processing.nbDecimal = decimal;
	}

	public static String getRateProperty() {
		return rateProperty;
	}

	public static void setRateProperty(String rateProperty) {
		Processing.rateProperty = rateProperty;
	}

	public static char getTimeLine() {
		return timeLine;
	}

	public static void setTimeLine(char timeLine) {
		Processing.timeLine = timeLine;
	}

	public static String getOutputFormat() {
		return outputFormat;
	}

	public static void setOutputFormat(String outputFormat) {
		Processing.outputFormat = outputFormat;
	}

	public static Calendar getDataDate() {
		return dataDate;
	}

	public static void setDataDate(Calendar dataDate) {
		Processing.dataDate = dataDate;
	}

	public static String getCurrency() {
		return currency;
	}

	public static void setCurrency(String currency) {
		Processing.currency = currency;
	}

	public static int getMaxYears() {
		return maxYears;
	}

	public static void setMaxYears(int maxYears) {
		Processing.maxYears = maxYears;
	}
	
	public static double getErrorMargin() {
		return errorMargin;
	}

	public static void setErrorMargin(double errorMargin) {
		Processing.errorMargin = errorMargin;
	}

	public static int getNbOfDays() {
		return nbOfDays;
	}

	public static void setNbOfDays(int nbOfDays) {
		Processing.nbOfDays = nbOfDays;
	}

	public static Asset getLastAsset(ArrayList<Asset> portfolio){
		return portfolio.get(portfolio.size()-1);
	}

    public static ArrayList<Asset> getPortfolio() {
		return port;
	}

	public static void setPortfolio(ArrayList<Asset> port) {
		Processing.port = port;
	}

	public static int getNbOfColumns() {
		return nbOfColumns;
	}

	public static void setNbOfColumns(int nbOfColumns) {
		Processing.nbOfColumns = nbOfColumns;
	}
}
