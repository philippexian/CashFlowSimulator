package assets;

import input.DataModel;
import calculations.DateCalcul;
import processing.Processing;
import profiles.DifferedPaymentProfile;
import profiles.InterestRateProfile;
import profiles.RecoveryProfile;
import profiles.RiskProfile;
import references.ReferenceOps;

public class Asset {
	
    private char assetType;
	private int[] startDate;
	//startDate[0][1][2] represent respectively the year, month and day
	private int[] endDate;
	//endDate[0][1][2] represent respectively the year, month and day
	private double outstanding;
	private char periodicity;
	private char amortizationType;
	private int daycount;
	
    private DifferedPaymentProfile differedPaymentProfile;
	private InterestRateProfile interestRateProfile;
	private RiskProfile defaultProfile;
	private RiskProfile LGDProfile;
	private RiskProfile prepaymentProfile;
	private RecoveryProfile recoveryProfile;

	
	public Asset(){
		//nothing
	}
	

	public Asset(String[] dataRow) throws Exception{//constructor with all elements set
        
        this.startDate=DateCalcul.parseCalendar(
        DataModel.dateConverter(DataModel.getStartDate(), dataRow[DataModel.getStartDate().getColumnNumber()]));//start date
        
        this.endDate=DateCalcul.parseCalendar(
        DataModel.dateConverter(DataModel.getEndDate(), dataRow[DataModel.getEndDate().getColumnNumber()]));//end date
        
        this.outstanding=DataModel.doubleConverter(DataModel.getOutstanding(), dataRow[DataModel.getOutstanding().getColumnNumber()]);//outstanding
        
        this.periodicity=DataModel.periodicityConverter(dataRow[DataModel.getPeriodicity().getColumnNumber()]);//periodicity
    	
        this.amortizationType=DataModel.amortizationTypeConverter(dataRow[DataModel.getAmortizationType().getColumnNumber()]);//amortization type
        
        this.differedPaymentProfile=new DifferedPaymentProfile(
        DataModel.differedPaymentTypeConverter(dataRow[DataModel.getDifferedPaymentType().getColumnNumber()]), //differed payment type
        DataModel.dateConverter(DataModel.getStartDate(), dataRow[DataModel.getStartDate().getColumnNumber()]), //start date
        DataModel.intConverter(DataModel.getDifferedPaymentDuration(), dataRow[DataModel.getDifferedPaymentDuration().getColumnNumber()]), //differed payment duration 
        Processing.getDataDate());//data date
    	
        if(DataModel.rateTypeConverter(dataRow[DataModel.getRateType().getColumnNumber()], 
        		dataRow[DataModel.getFixedToFloatingDate().getColumnNumber()])=='f'){
        	
        	this.interestRateProfile=new InterestRateProfile(
        	DataModel.rateTypeConverter(dataRow[DataModel.getRateType().getColumnNumber()], dataRow[DataModel.getFixedToFloatingDate().getColumnNumber()]),//rate type
        	DataModel.doubleConverter(DataModel.getAnnualRate(), dataRow[DataModel.getAnnualRate().getColumnNumber()]));//fixed rate
        }
        else{
        	this.interestRateProfile=new InterestRateProfile(
        	DataModel.rateTypeConverter(dataRow[DataModel.getRateType().getColumnNumber()], dataRow[DataModel.getFixedToFloatingDate().getColumnNumber()]),//rate type
        	DataModel.doubleConverter(DataModel.getAnnualRate(), dataRow[DataModel.getAnnualRate().getColumnNumber()]), //fixed rate
        	DataModel.capFloorTypeConverter(dataRow[DataModel.getCapFloorType().getColumnNumber()]), //cap floor type
            DataModel.doubleConverter(DataModel.getCapLevel(), dataRow[DataModel.getCapLevel().getColumnNumber()]), //cap level
            DataModel.doubleConverter(DataModel.getFloorLevel(), dataRow[DataModel.getFloorLevel().getColumnNumber()]), //floor level
            DataModel.doubleConverter(DataModel.getMargin(), dataRow[DataModel.getMargin().getColumnNumber()]), //margin
            ReferenceOps.searchIndex(DataModel.indexNameConverter(dataRow[DataModel.getIndexName().getColumnNumber()]), Processing.getCurrency()), //index: index name & currency
            DataModel.dateConverter(DataModel.getFixedToFloatingDate(), dataRow[DataModel.getFixedToFloatingDate().getColumnNumber()]));//fixed to floating date
        }
        
        if(DataModel.DPRateTypeConverter(dataRow[DataModel.getDPRateType().getColumnNumber()])=='f'){
        	this.defaultProfile=new RiskProfile(
        	DataModel.DPRateTypeConverter(dataRow[DataModel.getDPRateType().getColumnNumber()]), //DP rate type
        	DataModel.doubleConverter(DataModel.getDefaultRate(), dataRow[DataModel.getDefaultRate().getColumnNumber()])); //DP rate
        }
        else{
        	this.defaultProfile=new RiskProfile(
        	DataModel.DPRateTypeConverter(dataRow[DataModel.getDPRateType().getColumnNumber()]), //DP rate type
        	ReferenceOps.searchModel(DataModel.DPModelNameConverter(dataRow[DataModel.getDPModelName().getColumnNumber()]))); //model
        }
    	
        if(DataModel.LGDRateTypeConverter(dataRow[DataModel.getLGDRateType().getColumnNumber()])=='f'){
        	this.LGDProfile=new RiskProfile(
        	DataModel.LGDRateTypeConverter(dataRow[DataModel.getLGDRateType().getColumnNumber()]), //LGD rate type
        	DataModel.doubleConverter(DataModel.getLGDRate(), dataRow[DataModel.getLGDRate().getColumnNumber()])); //LGD rate
        }
        else{
        	this.LGDProfile=new RiskProfile(
        	DataModel.LGDRateTypeConverter(dataRow[DataModel.getLGDRateType().getColumnNumber()]), //LGD rate type
        	ReferenceOps.searchModel(DataModel.LGDModelNameConverter(dataRow[DataModel.getLGDModelName().getColumnNumber()]))); //model
        }
    	
        if(DataModel.prepaymentRateTypeConverter(dataRow[DataModel.getPrepaymentRateType().getColumnNumber()])=='f'){
        	this.prepaymentProfile=new RiskProfile(
        	DataModel.prepaymentRateTypeConverter(dataRow[DataModel.getPrepaymentRateType().getColumnNumber()]), //prepayment rate type
        	DataModel.doubleConverter(DataModel.getPrepaymentRate(), dataRow[DataModel.getPrepaymentRate().getColumnNumber()])); //prepayment rate
        }
        else{
        	this.prepaymentProfile=new RiskProfile(
        	DataModel.prepaymentRateTypeConverter(dataRow[DataModel.getPrepaymentRateType().getColumnNumber()]), //prepayment rate type
        	ReferenceOps.searchModel(DataModel.prepaymentModelNameConverter(dataRow[DataModel.getPrepaymentModelName().getColumnNumber()]))); //model
        }
    	
    	this.recoveryProfile=new RecoveryProfile(
    	DataModel.recoveryTypeConverter(dataRow[DataModel.getRecoveryType().getColumnNumber()]), //recovery type
    	DataModel.intConverter(DataModel.getStartRecovery(), dataRow[DataModel.getStartRecovery().getColumnNumber()]), //start recovery
    	DataModel.intConverter(DataModel.getEndRecovery(), dataRow[DataModel.getEndRecovery().getColumnNumber()]));//end recovery
	}
	
	
	//getters setters
	public double getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(double capital) {
		this.outstanding = capital;
	}

	public int[] getStartDate() {
		return startDate;
	}

	//month varies from 0-11, representing January...until December
	
	public void setStartDate(int[] startDate) {
		this.startDate = startDate;
	}

	public int[] getEndDate() {
		return endDate;
	}
	
	public void setEndDate(int[] endDate) {
		this.endDate = endDate;
	}

	//other getters and setters
	public void setPeriodicity(char periodicity){
		this.periodicity=periodicity;
	}
	
	public char getPeriodicity(){
		return this.periodicity;
	}
	
	public char getAssetType() {
		return assetType;
	}

	public void setAssetType(char type) {
		this.assetType = type;
	}

	public char getAmortizationType() {
		return amortizationType;
	}

	public void setAmortizationType(char amortizationType) {
		this.amortizationType = amortizationType;
	}	

	public DifferedPaymentProfile getDifferedPaymentProfile() {
		return differedPaymentProfile;
	}

	public void setDifferedPaymentProfile(DifferedPaymentProfile differedPaymentProfile) {
		this.differedPaymentProfile = differedPaymentProfile;
	}
	
	public int getDaycount() {
		return daycount;
	}
	
	public void setDaycount(int daycount) {
		this.daycount = daycount;
	}
	
	public RiskProfile getPrepaymentProfile() {
		return prepaymentProfile;
	}

	public void setPrepaymentProfile(RiskProfile prepaymentProfile) {
		this.prepaymentProfile = prepaymentProfile;
	}

	public RiskProfile getDefaultProfile() {
		return defaultProfile;
	}

	public void setDefaultProfile(RiskProfile defaultProfile) {
		this.defaultProfile = defaultProfile;
	}

	public RiskProfile getLGDProfile() {
		return LGDProfile;
	}

	public void setLGDProfile(RiskProfile lGDProfile) {
		LGDProfile = lGDProfile;
	}

	public RecoveryProfile getRecoveryProfile() {
		return recoveryProfile;
	}

	public void setRecoveryProfile(RecoveryProfile recoveryProfile) {
		this.recoveryProfile = recoveryProfile;
	}

	public InterestRateProfile getInterestRateProfile() {
		return interestRateProfile;
	}

	public void setInterestRateProfile(InterestRateProfile profile) {
		this.interestRateProfile = profile;
	}
	
	

}
