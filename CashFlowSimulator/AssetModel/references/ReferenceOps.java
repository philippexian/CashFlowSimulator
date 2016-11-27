package references;

import input.CsvReader;
import input.ExcelFileReader;
import input.TxtReader;
import input.XlsReader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import calculations.DateCalcul;

public class ReferenceOps {
	
	private static ArrayList<Index> indices;
	private static ArrayList<RiskModel> models;
	
	private static String modelFolderPath;
	private static String modelFileFormatDelimiter;
	private static String modelValueType;
	private static SimpleDateFormat modelDateFormat;
	
	private static String indexFolderPath;
	private static String indexFileNameSeparator;
	private static String indexFileFormatDelimiter;
	private static String indexValueType;
	private static SimpleDateFormat indexDateFormat;

	
	public static void initIndices() throws Exception{

		//initiate indices
		indices=new ArrayList<Index>();
		
		File folder=new File(indexFolderPath);
		for(File file:folder.listFiles()){
			String fileName=file.getName();
			String filePath=file.getAbsolutePath();
			ArrayList<String[]> table;
			
			//read excel file
			if(fileName.endsWith("xlsx")||fileName.endsWith("xlsm")){
				ExcelFileReader reader= new ExcelFileReader(filePath);
	            table=reader.readRows(1000000);//maximum 1000000
			}
			
			else if(fileName.endsWith("xls")){
				table=XlsReader.readXlsFile(filePath);
			}
			
			//read txt file
	        else if(fileName.endsWith("txt")){//read txt
	        	table=TxtReader.readTxtFiles(filePath, indexFileFormatDelimiter);
	        }
	        
	        //read csv file
	        else if(fileName.endsWith("csv")){//read csv
	        	table=CsvReader.readCsvFiles(filePath, indexFileFormatDelimiter);
	        }
	        
	        else{
	        	throw new Exception("bad file!");
	        }
			
			//initiate index
			Index index=new Index(readIndexInfo(fileName, indexFileNameSeparator)[0], readIndexInfo(fileName, indexFileNameSeparator)[1]);
			//0=name
			//1=currency
			
			//fill in the index table
			
			for(int i=1;i<table.size();i++){//skip the head
				String[] row=table.get(i);
				
				if(row.length<2){
					break;
				}//avoid empty cells and lines
				
				try{
					if(row[1].charAt(0)>='0'&&row[1].charAt(0)<='9'){//if it's a number

						index.getIndexMap().put(DateCalcul.parseCalendar(dateConverter(fileName, row[0], indexDateFormat)), 
								doubleConverter(indexValueType, row[1]));
					    
					    //0=date
					    //1=value
					}
					else{
						throw new NumberFormatException("the rate in the "+i+"th row in the file "+fileName+" is not in correct type!");
					}

				}catch(NumberFormatException e){
					e.printStackTrace();
					System.out.println(fileName);
					System.out.println(i);
				}
			}
			indices.add(index);
		}
	}
	
	public static void initModels() throws Exception{
		
		//initiate models
		models=new ArrayList<RiskModel>();
		
		File folder=new File(modelFolderPath);
		
		for(File file:folder.listFiles()){
			String fileName=file.getName();//file name (including the format)
			String filePath=file.getAbsolutePath();
			ArrayList<String[]> table;
			
			//read excel file
			if(fileName.endsWith("xlsx")||fileName.endsWith("xlsm")){
				ExcelFileReader reader= new ExcelFileReader(filePath);
	            table=reader.readRows(1000000);//maximum 1000000
			}
			
			else if(fileName.endsWith("xls")){
				table=XlsReader.readXlsFile(filePath);
			}
			
			//read txt file
	        else if(fileName.endsWith("txt")){//read txt
	        	table=TxtReader.readTxtFiles(filePath, indexFileFormatDelimiter);
	        }
	        
	        //read csv file
	        else if(fileName.endsWith("csv")){//read csv
	        	//csvReader reader=new csvReader(filePath);
	        	table=CsvReader.readCsvFiles(filePath, indexFileFormatDelimiter);
	        }
	        
	        else{
	        	throw new Exception("bad file!");
	        }
			
			//initiate model
			RiskModel model=new RiskModel(fileName.substring(0, fileName.lastIndexOf('.')));//file name except the format
			
			//fill in the index table
			for(int i=1;i<table.size();i++){//avoid the header
				String[] row=table.get(i);
				
				if(row.length<2){
					break;
				}//avoid empty lines and empty cells
				
				try{
					if(row[1].charAt(0)>='0'&&row[1].charAt(0)<='9'){//if it's a number

						model.getModelMap().put((DateCalcul.parseCalendar(dateConverter(fileName, row[0], modelDateFormat))), 
								doubleConverter(modelValueType, row[1]));
					    //0=date
					    //1=value
					}
					else{
						throw new NumberFormatException("the rate in the "+i+"th row in the file "+fileName+" is not in correct type!");
					}

				}catch(NumberFormatException e){
					e.printStackTrace();
					System.out.println(fileName);
					System.out.println(i);
				}
			}
			models.add(model);
		}//end for

		
	}
	
	
	public static Index searchIndex(String name, String currency) throws Exception{//search index by its name and currency
		Index indexGet=null;
		boolean flag=false;
		for(Index i: indices){
			if(i.getName().contains(name)&&i.getCurrency().contains(currency)){
				flag=true;
				indexGet=i;
			}
		}
		
		if(!flag){
			throw new Exception("index not found!");
		}
		
		return indexGet;
	}
	
	public static RiskModel searchModel(String name) throws Exception{//search risk model by its name
		RiskModel modelGet=null;
		boolean flag=false;
		for(RiskModel m: models){
			if(m.getModelName().contains(name)){//not exactly the same string
				flag=true;
				modelGet=m;
			}
		}
		
		if(!flag){
			throw new Exception("risk model not found!");
		}
		
		return modelGet;
	}
	
	//extract the index name, basis and currency from the file name
	//0=name
	//1=currency
	public static String[] readIndexInfo(String fileName, String separator){
		String[] indexInfo=new String[3];
		indexInfo[0]=fileName.substring(0, fileName.lastIndexOf(separator));//lastIndexOf returns the last occurrence of the character
		//the first part of the file name is the index name

		indexInfo[1]=fileName.substring(fileName.lastIndexOf(separator)+1, fileName.lastIndexOf('.'));
		//the second part is the currency
		
		return indexInfo;
	}
	
	//necessary converters
    
    public static Calendar dateConverter(String fileName, String str, SimpleDateFormat format) throws ParseException{//2 steps, first parse to date then from date to calendar
    	Calendar cal;
    	
    	if(fileName.substring(fileName.lastIndexOf('.')).contains("xls")){
    		//excel file
    		cal=excelDateConverter(str);
    	}

    	else{//for txt & csv
        	Date date=format.parse(str);
        	cal = Calendar.getInstance();
        	cal.setTime(date);
    	}
    	return cal;
    }
    
	        //transient function used in dateConverter
			public static Calendar excelDateConverter(String str){
		        Calendar date=new GregorianCalendar(1899, 11, 30);
		        date.add(Calendar.DAY_OF_MONTH, Integer.parseInt(str));
		        return date;       
		    }
	
    
    
    public static double doubleConverter(String type, String str){
    	String str2=str.replace(',', '.');
    	if(type.contentEquals("percentage")){
    		return Double.parseDouble(str2)/100;
    	}
    	else{
    		return Double.parseDouble(str2);
    	}
    }
    

    //getters and setters
	public static ArrayList<Index> getIndices() {
		return indices;
	}

	public static void setIndices(ArrayList<Index> indices) {
		ReferenceOps.indices = indices;
	}

	public static ArrayList<RiskModel> getModels() {
		return models;
	}

	public static void setModels(ArrayList<RiskModel> models) {
		ReferenceOps.models = models;
	}

	public static String getIndexFolderPath() {
		return indexFolderPath;
	}

	public static void setIndexFolderPath(String folderPath) {
		ReferenceOps.indexFolderPath = folderPath;
	}

	public static String getIndexFileNameSeparator() {
		return indexFileNameSeparator;
	}

	public static void setIndexFileNameSeparator(String fileNameSeparator) {
		ReferenceOps.indexFileNameSeparator = fileNameSeparator;
	}
	
	public static String getIndexFileFormatDelimiter() {
		return indexFileFormatDelimiter;
	}
	
	public static void setIndexFileFormatDelimiter(String fileFormatDelimiter) {
		ReferenceOps.indexFileFormatDelimiter = fileFormatDelimiter;
	}
	
	public static SimpleDateFormat getIndexDateFormat() {
		return indexDateFormat;
	}
	
	public static void setIndexDateFormat(SimpleDateFormat dateFormat) {
		ReferenceOps.indexDateFormat = dateFormat;
	}

	public static String getIndexValueType() {
		return indexValueType;
	}

	public static void setIndexValueType(String valueType) {
		ReferenceOps.indexValueType = valueType;
	}

	public static String getModelFolderPath() {
		return modelFolderPath;
	}

	public static void setModelFolderPath(String modelFolderPath) {
		ReferenceOps.modelFolderPath = modelFolderPath;
	}

	public static String getModelFileFormatDelimiter() {
		return modelFileFormatDelimiter;
	}

	public static void setModelFileFormatDelimiter(String modelFileFormatDelimiter) {
		ReferenceOps.modelFileFormatDelimiter = modelFileFormatDelimiter;
	}

	public static String getModelValueType() {
		return modelValueType;
	}

	public static void setModelValueType(String modelValueType) {
		ReferenceOps.modelValueType = modelValueType;
	}

	public static SimpleDateFormat getModelDateFormat() {
		return modelDateFormat;
	}

	public static void setModelDateFormat(SimpleDateFormat modelDateFormat) {
		ReferenceOps.modelDateFormat = modelDateFormat;
	}

	
}
