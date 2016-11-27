package programEntry;

import input.DataModel;
import input.Feature;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import processing.Processing;
import references.ReferenceOps;

public class StartWithoutInterface {
	
	//all parameters
	//TODO for users to define
	
	//import data file
	public static String filePath="";
	private static String delimiter="";
	
	//import indices
	private static String indexFolderPath="";
	private static String indexFileFormatDelimiter="";
	private static String indexValueType="";
	private static SimpleDateFormat indexDateFormat=new SimpleDateFormat("dd/MM/yyyy");
	
	//import risk models
	private static String modelFolderPath="";
	private static String modelFileFormatDelimiter="";
	private static String modelValueType="";
	private static SimpleDateFormat modelDateFormat=new SimpleDateFormat("dd/MM/yyyy");

	//general features
	private static String rateProperty="proportional";
	private static String outputFormat="xlsx";
	private static char timeLine='M';
	private static String currency="EUR";
	private static Calendar dataDate=new GregorianCalendar();
	private static SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
	
	private static int nbDecimal=2;
	private static int nbOfColumns=30;
	private static int maxYears=40;
	private static double errorMargin=0.1;
	private static int nbOfDays=360;
	
	//asset features
	private static Feature startDate=new Feature(2, "date string", new GregorianCalendar());
	private static Feature endDate=new Feature(3, "date string", new GregorianCalendar());
	private static Feature outstanding=new Feature(4, "double", 0);
	private static Feature periodicity=new Feature(6, "String", 'M');
	private static Feature amortizationType=new Feature(7, "String", 'A');
	private static Feature differedPaymentType=new Feature(8, "String", 'N');
	private static Feature differedPaymentDuration=new Feature(9, "int", 0);
	
	//rate features
	private static Feature annualRate=new Feature(10, "rate", 0);
	private static Feature rateType=new Feature(11, "String", 'f');
	private static Feature capFloorType=new Feature(12, "String", 'N');
	private static Feature capLevel=new Feature(13, "rate", 100);
	private static Feature floorLevel=new Feature(14, "rate", 0);
	private static Feature margin=new Feature(15, "rate", 0);
	private static Feature indexName=new Feature(16, "String", "EURIBOR1M");
	private static Feature fixedToFloatingDate=new Feature(17, "date string", new GregorianCalendar());
	
	//risk features
	private static Feature DPRate=new Feature(18, "rate", 0);	
	private static Feature DPRateType=new Feature(19, "String", 'f');
	private static Feature DPModelName=new Feature(20, "String", "Fitch DP");
	private static Feature LGDRate=new Feature(21, "rate", 0);
	private static Feature LGDRateType=new Feature(22, "String", 'f');	
	private static Feature LGDModelName=new Feature(24, "String", "Fitch LGD");
	private static Feature recoveryType=new Feature(23, "String", 'I');
	private static Feature startRecovery=new Feature(24, "int", 6);
	private static Feature endRecovery=new Feature(25, "int", 6);
	private static Feature prepaymentRate=new Feature(26, "rate", 0);
	private static Feature prepaymentRateType=new Feature(27, "String", 'f');
	private static Feature prepaymentModelName=new Feature(28, "String", "RAUP");
	
	//output address
	private static String outputAddress="";
	

	public static void main(String[] args) throws Exception{
		
		//set all parameters in code without interface
		
		//part "choose an input file"
		Processing.setFilePath(filePath);
		Processing.setFileType(filePath.substring(filePath.lastIndexOf('.')));
		Processing.setDelimiter(delimiter);
		
		//part "import indexes"
		ReferenceOps.setIndexFolderPath(indexFolderPath);//index folder path to enter
    	ReferenceOps.setIndexFileFormatDelimiter(indexFileFormatDelimiter);//for csv or txt files
    	ReferenceOps.setIndexFileNameSeparator("_");//format: name_currency. Attention CANNOT be modified!!
    	ReferenceOps.setIndexValueType(indexValueType);//percentage or just numbers
    	ReferenceOps.setIndexDateFormat(indexDateFormat);//date format in the date list
    	
    	ReferenceOps.initIndices();
    	
    	//part "import risk models"
    	ReferenceOps.setModelFolderPath(modelFolderPath);//risk models folder path
    	ReferenceOps.setModelFileFormatDelimiter(modelFileFormatDelimiter);//for csv or txt files
    	ReferenceOps.setModelValueType(modelValueType);//percentage or just numbers
    	ReferenceOps.setModelDateFormat(modelDateFormat);//date format in the date list
    	
    	ReferenceOps.initModels();
		
		//part "general features"
		Processing.setRateProperty(rateProperty);//set rate property
		Processing.setCurrency(currency);//set currency
	    Processing.setNbOfColumns(nbOfColumns);
	    Processing.setMaxYears(maxYears);//set the maximum years in an asset
	    Processing.setNbOfDays(nbOfDays);//set the number of days in a year in convention for all assets
	    DataModel.setDateFormat(dateFormat);//set date format
	    
	    Processing.setDecimal(nbDecimal);//set the number of decimals in the output data
	    Processing.setTimeLine(timeLine);//set time line
		Processing.setOutputFormat(outputFormat);//set output format
	    Processing.setDataDate(dataDate);//year, month and day
	    Processing.setErrorMargin(errorMargin);//set the minimum margin in errors in the calculation results
	    
	    //part "asset features"
	    //features with the first element of column number, second element of data type and the third element of default value
				
		DataModel.setStartDate(startDate);//set start date
		//date string: csv or txt, excel date for excel files
				
		DataModel.setEndDate(endDate);//set end date
				
		DataModel.setOutstanding(outstanding);//set outstanding
				
		DataModel.setPeriodicity(periodicity);//set periodicity
				
		DataModel.setAmortizationType(amortizationType);//set amortization type
				
		DataModel.setDifferedPaymentType(differedPaymentType);//set differed payment type
				
		DataModel.setDifferedPaymentDuration(differedPaymentDuration);//set differed payment duration
		
		//part "rate features"
		DataModel.setAnnualRate(annualRate);//set annual interest rate
		
		DataModel.setRateType(rateType);//set interest rate type
		
		DataModel.setCapFloorType(capFloorType);//set cap floor type
		
		DataModel.setCapLevel(capLevel);//set cap level
		
		DataModel.setFloorLevel(floorLevel);//set floor level
		
		DataModel.setMargin(margin);//set margin
		
		DataModel.setIndexName(indexName);//set index name
		
		DataModel.setFixedToFloatingDate(fixedToFloatingDate);//set fixed to floating date
		
		//part "risk features"
		DataModel.setDPRate(DPRate);//set default probability
		
		DataModel.setDPRateType(DPRateType);//set DP rate type
		
		DataModel.setDPModelName(DPModelName);//set DP model name
		
		DataModel.setLGDRate(LGDRate);//set LGD
		
		DataModel.setLGDType(LGDRateType);//set LGD rate type
		
		DataModel.setLGDModelName(LGDModelName);//set LGD model name
		
		DataModel.setRecoveryType(recoveryType);//set recovery type
		
		DataModel.setStartRecovery(startRecovery);//set the recovery starting month: x months after the default
		
		DataModel.setEndRecovery(endRecovery);//set the recovery end month: x months after the default
		
		DataModel.setPrepaymentRate(prepaymentRate);//set prepayment rate
		
		DataModel.setPrepaymentRateType(prepaymentRateType);//set prepayment rate type
		
		DataModel.setPrepaymentModelName(prepaymentModelName);//set prepayment model name
    	
    	//import
		Processing.importReferences();
		
		//set portfolio
		Processing.setPorfolio();
		
		//calculate and output
		Processing.simulateCashflow();
		Processing.writeDoc(outputAddress);
	    
	}
}
