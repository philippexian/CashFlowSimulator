package input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DataModel {
	//all Features
	private static Feature assetType;//loan, bond
	private static Feature startDate;
	private static Feature endDate;
	private static Feature outstanding;
	
	private static Feature daycount;//different values on Internet, 360 or 365/30 or ACT
	private static Feature periodicity;//add in fine
	private static Feature amortizationType;//annuity, equal, bullet, custom (user defines outstanding in each turn)
	private static Feature differedPaymentType;//total, partial, none
	private static Feature differedPaymentDuration;
	
	private static Feature annualRate;
	private static Feature rateType;//fixed, floating, custom, fixed to floating
	private static Feature capFloorType;//cap, floor, collar, none
	private static Feature capLevel;
	private static Feature floorLevel;
	private static Feature margin;
	private static Feature indexName;//libor hibor
	private static Feature indexBasis;//basis the frequency of the index
	private static Feature fixedToFloatingDate;
	
	private static Feature DPRateType;//fixed, floating, custom
	private static Feature DPRate;//each profile define static Features
	private static Feature DPModelName;
	private static Feature LGDRateType;
	private static Feature LGDRate;
	private static Feature LGDModelName;
	private static Feature recoveryType;
	private static Feature startRecovery;
	private static Feature endRecovery;
	private static Feature prepaymentRateType;
	private static Feature prepaymentRate;
	private static Feature prepaymentModelName;
	
	private static SimpleDateFormat dateFormat;
	
	//initialize Features
	public DataModel(){

	}

		
	//converters
	//if the Feature doesn't exist in the file, the converter will take its default value
    

	public static Calendar excelDateConverter(String str){
        Calendar date=new GregorianCalendar(1899, 11, 30);
        date.add(Calendar.DAY_OF_MONTH, (int)Double.parseDouble(str));
        //in excel, when the integer number is parsed to string, it keeps its form int.0
        return date;       
    }
    
    
    public static Calendar dateConverter(Feature param, String str) throws ParseException{//2 steps, first parse to date then from date to calendar
    	Calendar cal;
    	
    	if(param.getColumnNumber()!='\u0000' && param.getType().contentEquals("date string") && !str.isEmpty()){//avoid reading empty cells
        	Date date=dateFormat.parse(str);
        	cal = Calendar.getInstance();
        	cal.setTime(date);
    	}

    	else if(param.getColumnNumber()!='\u0000' && param.getType().contentEquals("excel date") && !str.isEmpty()){
    		cal=excelDateConverter(str);
    	}
    	else{
    		cal=(Calendar)param.getDefaultValue();
    	}
    	return cal;
    }
    
    
    public static double doubleConverter(Feature param, String str){
    	String str2=str.replace(',', '.');
    	
    	if(param.getType().contentEquals("rate")){
    		if(param.getColumnNumber()!='\u0000' && !str2.isEmpty()){//avoid reading the empty cells
    			return Double.parseDouble(str2)/100;
    		}
    		else{
    			return (double) param.getDefaultValue()/100;
    		}
    	}
    	else{
    		if(param.getColumnNumber()!='\u0000' && !str2.isEmpty()){
    			return Double.parseDouble(str2);
    		}
    		else{
    			return (double) param.getDefaultValue();
    		}
    	}
    }
    
    public static int intConverter(Feature param, String str){//smallest int larger than the value
    	if(param.getColumnNumber()!='\u0000' && !str.isEmpty()){
    		return (int)(Double.parseDouble(str)-0.01)+1;
    	}
    	else{
    		return (int) param.getDefaultValue();
    	}
    }
    
    
    //for type converters, the comparison of string is necessary
    //the function contentEquals compares 2 strings, if their content is identical, it returns true
    
    //f for fixed
    //v for variable (floating)
    //t for fixed to floating
    
    /*
     * TODO for users to define: the key number and the type corresponding to each key
     */
    public static char rateTypeConverter(String type, String fixedToFloatingDate){
    	char rateType;

    	if(type.contentEquals("0")){//let users to define
    		rateType='f';
    	}
    	else if(type.contentEquals("1")||type.contentEquals("2")||type.contentEquals("3")||type.contentEquals("4")){
    		if(!fixedToFloatingDate.isEmpty() && fixedToFloatingDate!=null){
    			rateType='F';
    		}
    		else{
    			rateType='v';
    		}
    	}
    	else{
    		rateType=(char)DataModel.rateType.getDefaultValue();
    	}
    	return rateType;
    }
    
    //N for none
    //c for cap
    //F for floor
    //C for collar
    
    /*
     * TODO for users to define: the key number and the type corresponding to each key
     */
    public static char capFloorTypeConverter(String str){
    	char capFloorType;
    	if(str.contentEquals("0")){
    		capFloorType='N';
    	}
    	else if(str.contentEquals("1")){
    		capFloorType='c';
    	}
    	else if(str.contentEquals("2")){
    		capFloorType='f';
    	}
    	else if(str.contentEquals("3")){
    		capFloorType='C';
    	}
    	else{
    		capFloorType=(char) DataModel.capFloorType.getDefaultValue();
    	}
    	return capFloorType;
    }
    
    
    //M for monthly
    //Q for quarterly
    //B for biannual
    //A for annual
    //W for weekly
    //D for daily
    //I for infine
    
    /*
     * TODO for users to define: the key number and the type corresponding to each key
     */
    public static char periodicityConverter(String str){
    	char periodicity;
    	if(str.contentEquals("1")){
    		periodicity='M';
    	}
    	else if(str.contentEquals("2")){
    		periodicity='Q';
    	}
    	else if(str.contentEquals("3")){
    		periodicity='B';
    	}
    	else if(str.contentEquals("4")){
    		periodicity='A';
    	}
    	else if(str.contentEquals("0")){
    		periodicity='I';
    	}
    	else{
    		periodicity=(char) DataModel.periodicity.getDefaultValue();
    	}
    	return periodicity;
    }
    
    //A for annuity
    //E for equal
    //B for bullet
    
    /*
     * TODO for users to define: the key number and the type corresponding to each key
     */
    public static char amortizationTypeConverter(String str){
    	char amortizationType;
    	if(str.contentEquals("0")||str.contentEquals("2")||str.contentEquals("4")||str.contentEquals("5")||str.contentEquals("8")){
    		amortizationType='A';
    	}
    	else if(str.contentEquals("1")){
    		amortizationType='E';
    	}
    	else if(str.contentEquals("3")||str.contentEquals("6")||str.contentEquals("7")){
    		amortizationType='B';
    	}
    	else{
    		amortizationType=(char) DataModel.amortizationType.getDefaultValue();
    	}
    	return amortizationType;
    }
    
    //N for none
    //T for total
    //P for partial
    
    /*
     * TODO for users to define: the key number and the type corresponding to each key
     */
    public static char differedPaymentTypeConverter(String str){
    	char differedPaymentType;
    	if(str.contentEquals("0")){
    		differedPaymentType='N';
    	}
    	else if(str.contentEquals("1")){
    		differedPaymentType='T';
    	}
    	else if(str.contentEquals("2")){
    		differedPaymentType='P';
    	}
    	else{
    		differedPaymentType=(char) DataModel.differedPaymentType.getDefaultValue();
    	}
    	return differedPaymentType;
    }

	
    //TODO for users to define the keys and the corresponding values
    
    public static String indexNameConverter(String key){
    	if(key.contentEquals("3")){
    		return "EURIBOR1M";
    	}
    	else if(key.contentEquals("4")||key.contentEquals("13")){
    		return "EURIBOR3M";
    	}
    	else if(key.contentEquals("6")){
    		return "EURIBOR12M";
    	}
    	else if(key.contentEquals("16")){
    		return "TEC5";
    	}
    	else if(key.contentEquals("26")){
    		return "TEC10";
    	}
    	else if(key.contentEquals("8")){
    		return "TME";
    	}
    	else{
    	    return (String)indexName.getDefaultValue();
    	}
    }
    
	//TODO converters to define, now we take the default value
    //for users to define the keys and the corresponding values
    //we just give the examples
    
    public static String DPModelNameConverter(String key){
    	return (String) DPModelName.getDefaultValue();
    	
    }
    
    public static String LGDModelNameConverter(String key){
    	return (String) LGDModelName.getDefaultValue();
    }
    
    public static String prepaymentModelNameConverter(String key){
    	return (String) prepaymentModelName.getDefaultValue();
    }
    
    //f for fixed
    //v for floating
    public static char DPRateTypeConverter(String key){
    	if(key.contentEquals("1")){
    		return 'f';
    	}
    	else if(key.contentEquals("2")){
    		return 'v';
    	}
    	else{
    	    return (char)DPRateType.getDefaultValue();
    	}
    }
    
    //f for fixed
    //v for floating
    public static char LGDRateTypeConverter(String key){
    	if(key.contentEquals("1")){
    		return 'f';
    	}
    	else if(key.contentEquals("2")){
    		return 'v';
    	}
    	else{
    	    return (char)LGDRateType.getDefaultValue();
    	}
    }
    
    //I for infine
    //L for linear
    public static char recoveryTypeConverter(String key){
    	if(key.contentEquals("1")){
    		return 'I';
    	}
    	else if(key.contentEquals("2")){
    		return 'L';
    	}
    	else{
    	    return (char)recoveryType.getDefaultValue();
    	}
    }
    
    //f for fixed
    //v for floating
    public static char prepaymentRateTypeConverter(String key){
    	if(key.contentEquals("1")){
    		return 'f';
    	}
    	else if(key.contentEquals("2")){
    		return 'v';
    	}
    	else{
    	    return (char)prepaymentRateType.getDefaultValue();
    	}
    }
    
    
	//getters and setters
	public static Feature getStartDate() {
		return startDate;
	}

	public static void setStartDate(Feature startDate) {
		DataModel.startDate = startDate;
	}

	public static Feature getOutstanding() {
		return outstanding;
	}

	public static void setOutstanding(Feature outstanding) {
		DataModel.outstanding = outstanding;
	}

	public static Feature getAnnualRate() {
		return annualRate;
	}

	public static void setAnnualRate(Feature annualRate) {
		DataModel.annualRate = annualRate;
	}

	public static Feature getRateType() {
		return rateType;
	}

	public static void setRateType(Feature rateType) {
		DataModel.rateType = rateType;
	}

	public static Feature getCapFloorType() {
		return capFloorType;
	}

	public static void setCapFloorType(Feature capFloorType) {
		DataModel.capFloorType = capFloorType;
	}

	public static Feature getCapLevel() {
		return capLevel;
	}

	public static void setCapLevel(Feature capLevel) {
		DataModel.capLevel = capLevel;
	}

	public static Feature getFloorLevel() {
		return floorLevel;
	}

	public static void setFloorLevel(Feature floorLevel) {
		DataModel.floorLevel = floorLevel;
	}

	public static Feature getPeriodicity() {
		return periodicity;
	}

	public static void setPeriodicity(Feature periodicity) {
		DataModel.periodicity = periodicity;
	}

	public static Feature getAmortizationType() {
		return amortizationType;
	}

	public static void setAmortizationType(Feature amortizationType) {
		DataModel.amortizationType = amortizationType;
	}

	public static Feature getDifferedPaymentType() {
		return differedPaymentType;
	}

	public static void setDifferedPaymentType(Feature differedPaymentType) {
		DataModel.differedPaymentType = differedPaymentType;
	}

	public static Feature getDifferedPaymentDuration() {
		return differedPaymentDuration;
	}

	public static void setDifferedPaymentDuration(Feature differedPaymentDuration) {
		DataModel.differedPaymentDuration = differedPaymentDuration;
	}

	public static Feature getDefaultRate() {
		return DPRate;
	}

	public static void setDPRate(Feature dPRate) {
		DPRate = dPRate;
	}

	public static Feature getLGDRate() {
		return LGDRate;
	}

	public static void setLGDRate(Feature lGDRate) {
		LGDRate = lGDRate;
	}

	public static Feature getEndDate() {
		return endDate;
	}

	public static void setEndDate(Feature endDate) {
		DataModel.endDate = endDate;
	}

	public static Feature getMargin() {
		return margin;
	}

	public static void setMargin(Feature margin) {
		DataModel.margin = margin;
	}

	public static Feature getPrepaymentRate() {
		return prepaymentRate;
	}

	public static void setPrepaymentRate(Feature prepaymentRate) {
		DataModel.prepaymentRate = prepaymentRate;
	}
    
    public static Feature getAssetType() {
		return assetType;
	}

	public static void setAssetType(Feature assetType) {
		DataModel.assetType = assetType;
	}

	public static Feature getDaycount() {
		return daycount;
	}

	public static void setDaycount(Feature daycount) {
		DataModel.daycount = daycount;
	}

	public static Feature getIndexName() {
		return indexName;
	}

	public static void setIndexName(Feature indexName) {
		DataModel.indexName = indexName;
	}

	public static Feature getIndexBasis() {
		return indexBasis;
	}

	public static void setIndexBasis(Feature indexBasis) {
		DataModel.indexBasis = indexBasis;
	}

	public static Feature getFixedToFloatingDate() {
		return fixedToFloatingDate;
	}

	public static void setFixedToFloatingDate(Feature fixedToFloatingDate) {
		DataModel.fixedToFloatingDate = fixedToFloatingDate;
	}

	public static Feature getDPRateType() {
		return DPRateType;
	}

	public static void setDPRateType(Feature dPRateType) {
		DPRateType = dPRateType;
	}

	public static Feature getDPModelName() {
		return DPModelName;
	}

	public static void setDPModelName(Feature DPModelName) {
		DataModel.DPModelName = DPModelName;
	}

	public static Feature getLGDRateType() {
		return LGDRateType;
	}

	public static void setLGDType(Feature lGDRateType) {
		LGDRateType = lGDRateType;
	}

	public static Feature getLGDModelName() {
		return LGDModelName;
	}

	public static void setLGDModelName(Feature lGDModelName) {
		LGDModelName = lGDModelName;
	}

	public static Feature getRecoveryType() {
		return recoveryType;
	}

	public static void setRecoveryType(Feature recoveryType) {
		DataModel.recoveryType = recoveryType;
	}

	public static Feature getStartRecovery() {
		return startRecovery;
	}

	public static void setStartRecovery(Feature startRecovery) {
		DataModel.startRecovery = startRecovery;
	}

	public static Feature getEndRecovery() {
		return endRecovery;
	}

	public static void setEndRecovery(Feature endRecovery) {
		DataModel.endRecovery = endRecovery;
	}

	public static Feature getPrepaymentRateType() {
		return prepaymentRateType;
	}

	public static void setPrepaymentRateType(Feature prepaymentRateType) {
		DataModel.prepaymentRateType = prepaymentRateType;
	}
	
    public static Feature getPrepaymentModelName() {
		return prepaymentModelName;
	}

	public static void setPrepaymentModelName(Feature prepaymentModelName) {
		DataModel.prepaymentModelName = prepaymentModelName;
	}

	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public static void setDateFormat(SimpleDateFormat dateFormat) {
		DataModel.dateFormat = dateFormat;
	}


}
