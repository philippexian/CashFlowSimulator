package calculations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import processing.Processing;
import assets.Asset;

public class CashflowAsset {

    private Asset asset;
    private static int maxYears=Processing.getMaxYears();
    private static double errorMargin=Processing.getErrorMargin();

    private int nbPeriods;
    
    private Calendar[] tableauDate;
	private double[] tableauInterest;
    private double[] tableauPrincipal;
    private double[] tableauOutstanding;
    private double[] tableauDefault;
    private double[] tableauRecovery;
    private double[] tableauPayoff;
    private double[] tableauPrepayment;
    private double[] tableauVarOstding;
    
    
    public CashflowAsset(Asset asset){//initialization
    	this.asset= asset;

    	tableauInterest=new double[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauPrincipal=new double[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauVarOstding=new double[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauOutstanding=new double[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauDefault=new double[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauRecovery=new double[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauDate= new Calendar[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauPrepayment=new double[(int) (maxYears*getNbPeriodsPY(asset))];
        tableauPayoff = new double[(int) (maxYears*getNbPeriodsPY(asset))];
    }
    
    
    public static double getNbPeriodsPY(Asset asset){//depend on nb of days
		int nbPeriodsPY = 0;
		switch(asset.getPeriodicity()){
		case('M'):
			nbPeriodsPY=12;
		    break;
		case('A'):
			nbPeriodsPY=1;
		    break;
		case('B'):
			nbPeriodsPY=2;
		    break;
		case('Q'):
			nbPeriodsPY=4;
		    break;
		case('W'):
			nbPeriodsPY=Processing.getNbOfDays()/7+1;
		    break;
		case('D'):
			nbPeriodsPY=Processing.getNbOfDays();
		    break; 
		case('I'):
			nbPeriodsPY=2;
		    break;
		default:
			nbPeriodsPY=2;
			break;
		}

		return nbPeriodsPY;
	}

    
    //transform the annual rate to the rate per period
	public double CalculRatePP(String type, double AnnualRate, Asset asset) throws Exception{
		
		double ratePP;
		if(type=="proportional"){
			ratePP=AnnualRate/getNbPeriodsPY(asset);
		}
		else if(type=="actuarial"){
			ratePP=Math.pow((1+AnnualRate), 1.0/getNbPeriodsPY(asset))-1;
		}
		else{
			throw new Exception("no such type!");
		}
		return ratePP;
		
	}
	
	
	//next payment date is counted according to the end date and the periodicity
	public Calendar calculNextPaymentDate(char periodicity, Calendar endDate){
		Calendar dataDate=Processing.getDataDate();
		Calendar nextPaymentDate=new GregorianCalendar(dataDate.get(Calendar.YEAR), dataDate.get(Calendar.MONTH), 
		Math.max(1, endDate.get(Calendar.DAY_OF_MONTH)));//restrict it between 1 to the last day of month
		
		if(periodicity=='M'){//monthly
			nextPaymentDate.add(Calendar.MONTH, 1);
		}//the next month with the same day-of-month with the end date
		
		else if(periodicity=='Q'){//quarterly
			nextPaymentDate.add(Calendar.MONTH, (endDate.get(Calendar.MONTH)-dataDate.get(Calendar.MONTH))%3);
			if(!nextPaymentDate.after(dataDate)){
				nextPaymentDate.add(Calendar.MONTH, 3);
			}
		}
		
		else if(periodicity=='B'){//biannual
			nextPaymentDate.add(Calendar.MONTH, (endDate.get(Calendar.MONTH)-dataDate.get(Calendar.MONTH))%6);
			if(!nextPaymentDate.after(dataDate)){
				nextPaymentDate.add(Calendar.MONTH, 6);
			}
		}
		
		else if(periodicity=='A'){//annual
			nextPaymentDate.add(Calendar.MONTH, (endDate.get(Calendar.MONTH)-dataDate.get(Calendar.MONTH))%12);
			if(!nextPaymentDate.after(dataDate)){
				nextPaymentDate.add(Calendar.MONTH, 12);
			}
		}
		
		else if(periodicity=='W'){//weekly
			while(!nextPaymentDate.after(dataDate)){
				nextPaymentDate.add(Calendar.DAY_OF_MONTH, 7);
			}
		}
		else if(periodicity=='D'){//daily
			while(!nextPaymentDate.after(dataDate)){
				nextPaymentDate.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
		else{//in fine
			nextPaymentDate=DateCalcul.getCalendarDate(asset.getEndDate());
		}
		
		return nextPaymentDate;
	}
	
	
	public double getActualRate(double indexRate, double margin, char capFloorType, double capLevel, double floorLevel){
		double specificRate;
		if(indexRate+margin>capLevel && (capFloorType=='c' || capFloorType=='C')){
			specificRate=capLevel;
		}
		else if(indexRate+margin<floorLevel && (capFloorType=='f' || capFloorType=='C')){
			specificRate=floorLevel;
		}
		else{
			specificRate=indexRate+margin;
		}
		return specificRate;
	}
	

	
    public  void Calcul() throws Exception{
    	Calendar nextPaymentDate=this.calculNextPaymentDate(asset.getPeriodicity(), DateCalcul.getCalendarDate(asset.getEndDate()));
    	//first of all calculate the next payment date, very important!!
    	nbPeriods=DateCalcul.difDate(nextPaymentDate, DateCalcul.getCalendarDate(asset.getEndDate()), asset.getPeriodicity())+1;
    	//+1 because already one period from now to the next payment date
    	
    	//initial outstanding, date
        tableauOutstanding[0] = asset.getOutstanding();
        tableauDate[0]=Processing.getDataDate();
        
        //initial rate PP
        double ratePP = CalculRatePP(Processing.getRateProperty(), asset.getInterestRateProfile().getRate(), asset);
        double prepaymentRate=asset.getPrepaymentProfile().getRate();
        double DP=asset.getDefaultProfile().getRate();
        double LGD=asset.getLGDProfile().getRate();
        
        //cursors to find the corresponding rate value to a certain date
    	int interestCursor=0;
    	int prepaymentCursor=0;
    	int DPCursor=0;
    	int LGDCursor=0;
    	
    	//date lists and rate lists for different rates
    	//initialization
    	ArrayList<int[]> interestDates=new ArrayList<int[]>();
    	ArrayList<Double> interestRates=new ArrayList<Double>();
    	ArrayList<int[]> DPDates=new ArrayList<int[]>();
    	ArrayList<Double> DPRates=new ArrayList<Double>();
    	ArrayList<int[]> LGDDates=new ArrayList<int[]>();
    	ArrayList<Double> LGDRates=new ArrayList<Double>();
    	ArrayList<int[]> prepaymentDates=new ArrayList<int[]>();
    	ArrayList<Double> prepaymentRates=new ArrayList<Double>();
    	
    	//definition if the type isn't fixed
    	if(asset.getInterestRateProfile().getRateType()!='f'){
        	interestDates=asset.getInterestRateProfile().getDateList();
        	interestRates=asset.getInterestRateProfile().getRateList();
    	}

    	if(asset.getDefaultProfile().getRateType()!='f'){
        	DPDates=asset.getDefaultProfile().getDateList();
        	DPRates=asset.getDefaultProfile().getRateList();
    	}

    	if(asset.getLGDProfile().getRateType()!='f'){
        	LGDDates=asset.getLGDProfile().getDateList();
        	LGDRates=asset.getLGDProfile().getRateList();
    	}

    	if(asset.getPrepaymentProfile().getRateType()!='f'){
        	prepaymentDates=asset.getPrepaymentProfile().getDateList();
        	prepaymentRates=asset.getPrepaymentProfile().getRateList();
    	}


        int period=1;//count the number of periods passed
        
        //calculate the cashflow during the differed payment
        //if is not differed, just skip it
        
        if(asset.getDifferedPaymentProfile().isDiffered()){
        	
        	Calendar movingDate=DateCalcul.EDate(nextPaymentDate, period-1, asset.getPeriodicity());
        	//the beginning of the "moving date" should be the 1st period after the data date, so here is period-1.
        	
        	while(movingDate.before(asset.getDifferedPaymentProfile().getEndDifferedDate())){

        		tableauDate[period]=movingDate;

    			//consider the variation of the interest rate during the differed period
        		
        		if(asset.getInterestRateProfile().getRateType()=='v'){
        			while(interestRates.size()>interestCursor && 
        				DateCalcul.compareTwoDates(tableauDate[period], interestDates.get(interestCursor))>=0){
        				//when the date is after the cursor date in the changing date list
        				double annualRate=getActualRate(interestRates.get(interestCursor), 
        						asset.getInterestRateProfile().getMargin(), asset.getInterestRateProfile().getCapFloorType(), 
        						asset.getInterestRateProfile().getCap(), asset.getInterestRateProfile().getFloor());
        				
        				ratePP=CalculRatePP(Processing.getRateProperty(), annualRate, asset);
                        interestCursor++;
        			}
        		}
        		
        		if(asset.getInterestRateProfile().getRateType()=='F'){
        			while(interestRates.size()>interestCursor && 
        				DateCalcul.compareTwoDates(tableauDate[period], interestDates.get(interestCursor))>=0 && 
        				DateCalcul.compareTwoDates(tableauDate[period], asset.getInterestRateProfile().getFixedToFloatingDate())>=0){
        				//when the date is after the cursor date in the changing date list AND after the fixed to floating date 
        				double annualRate=getActualRate(interestRates.get(interestCursor), 
        						asset.getInterestRateProfile().getMargin(), asset.getInterestRateProfile().getCapFloorType(), 
        						asset.getInterestRateProfile().getCap(), asset.getInterestRateProfile().getFloor());
        				
        				ratePP=CalculRatePP(Processing.getRateProperty(), annualRate, asset);
                        interestCursor++;
        			}
        		}
    			
                //do not consider the default, recovery and prepayment during the differed payment period 
        		
        		if(asset.getDifferedPaymentProfile().getDifferedType()=='P'){//partial
        			tableauInterest[period] = CalculMethod.calculInterest(tableauOutstanding[period-1], ratePP, 0, getNbPeriodsPY(asset));
        			//suppose the DP is zero during the differed period
        			tableauPrincipal[period]=0;
        			tableauOutstanding[period] = tableauOutstanding[0];
        			tableauDefault[period]=0;
        			tableauRecovery[period]=0;
        			tableauPrepayment[period]=0;
        			tableauVarOstding[period]=0;
                    tableauPayoff[period] = CalculMethod.calculPayoff
                    (tableauPrincipal[period], tableauInterest[period], tableauRecovery[period], tableauPrepayment[period]);
        		}
        		
        		if(asset.getDifferedPaymentProfile().getDifferedType()=='T'){//total: capitalize the interest
        			tableauInterest[period] = 0;
        			tableauPrincipal[period]=0;
        			tableauDefault[period]=0;
        			tableauRecovery[period]=0;
        			tableauPrepayment[period]=0;
        			tableauVarOstding[period]=CalculMethod.calculVarOutstanding(tableauPrincipal[period], tableauPrepayment[period], 0, 
        					CalculMethod.calculInterest(tableauOutstanding[period-1], ratePP, 0, getNbPeriodsPY(asset)), 
        					tableauOutstanding[period-1]);
        			tableauOutstanding[period] = CalculMethod.calculOutstanding(tableauOutstanding[period-1], tableauVarOstding[period]);
        			tableauPayoff[period]=0;
        		}
        		
        		period++;
        		movingDate=DateCalcul.EDate(nextPaymentDate, period-1, asset.getPeriodicity());
        	}
        }

    	
    	//calculate cash flow during the life cycle of the asset
        
        for (int i = period; i <= nbPeriods; i++){
        	
            tableauDate[i]=DateCalcul.EDate(nextPaymentDate, i-1, asset.getPeriodicity());
            //i-1 because the tableauDate[0] is the data date
            
            //interest rate variation
            if(asset.getInterestRateProfile().getRateType()=='v'){
    			while(interestRates.size()>interestCursor && 
    				DateCalcul.compareTwoDates(tableauDate[i], interestDates.get(interestCursor))>=0){
    				//when the date is after the cursor date in the changing date list
    				double annualRate=getActualRate(interestRates.get(interestCursor), 
    						asset.getInterestRateProfile().getMargin(), asset.getInterestRateProfile().getCapFloorType(), 
    						asset.getInterestRateProfile().getCap(), asset.getInterestRateProfile().getFloor());
    				
    				ratePP=CalculRatePP(Processing.getRateProperty(), annualRate, asset);
                    interestCursor++;
    			}
    		}
    		
    		if(asset.getInterestRateProfile().getRateType()=='F'){
    			while(interestRates.size()>interestCursor && 
    				DateCalcul.compareTwoDates(tableauDate[i], interestDates.get(interestCursor))>=0 && 
    				DateCalcul.compareTwoDates(tableauDate[i], asset.getInterestRateProfile().getFixedToFloatingDate())>=0){
    				//when the date is after the cursor date in the changing date list AND after the fixed to floating date 
    				double annualRate=getActualRate(interestRates.get(interestCursor), 
    						asset.getInterestRateProfile().getMargin(), asset.getInterestRateProfile().getCapFloorType(), 
    						asset.getInterestRateProfile().getCap(), asset.getInterestRateProfile().getFloor());
    				
    				ratePP=CalculRatePP(Processing.getRateProperty(), annualRate, asset);
                    interestCursor++;
    			}
    		}
	        	
            if(asset.getDefaultProfile().getRateType()!='f'){
    			while(DPRates.size()>DPCursor && //when the date is not the last in the list
    			DateCalcul.compareTwoDates(tableauDate[i], DPDates.get(DPCursor))>=0){
    				//when the date is after the cursor date in the changing date list
    				DP=DPRates.get(DPCursor);
                    DPCursor++;
    			}
    		}//DP rate variation  
	
            if(asset.getLGDProfile().getRateType()!='f'){
    			while(LGDRates.size()>LGDCursor && //when the date is not the last in the list
    			DateCalcul.compareTwoDates(tableauDate[i], LGDDates.get(LGDCursor))>=0){
    				//when the date is after the cursor date in the changing date list
    				LGD=LGDRates.get(LGDCursor);
                    LGDCursor++;
    			}
    		} //LGD variation 
            
            if(asset.getPrepaymentProfile().getRateType()!='f'){
    			while(prepaymentRates.size()>prepaymentCursor && //when the date is not the last in the list
    			DateCalcul.compareTwoDates(tableauDate[i], prepaymentDates.get(prepaymentCursor))>=0){
    				//when the date is after the cursor date in the changing date list
    				prepaymentRate=prepaymentRates.get(prepaymentCursor);
                    prepaymentCursor++;
    			}
    		} //prepayment rate variation
            
	        
            //default expectation method
            tableauInterest[i] = CalculMethod.calculInterest(tableauOutstanding[i-1], ratePP, DP, getNbPeriodsPY(asset));
            tableauPrepayment[i]=CalculMethod.calculPrepayment(tableauOutstanding[i-1], prepaymentRate, DP, getNbPeriodsPY(asset));
            tableauPrincipal[i] = CalculMethod.calculPrincipal(tableauOutstanding[i-1], ratePP, nbPeriods-i+1, prepaymentRate, DP, 
            		getNbPeriodsPY(asset), asset.getAmortizationType());
            tableauDefault[i] = CalculMethod.calculDefault(tableauOutstanding[i-1], DP, getNbPeriodsPY(asset));

        	for (int j=i+DateCalcul.nbMonthsToNbPeriods(asset.getRecoveryProfile().getStartRecovery(), asset);
        		j<=i+DateCalcul.nbMonthsToNbPeriods(asset.getRecoveryProfile().getEndRecovery(), asset);j++){
        		tableauRecovery[j] += CalculMethod.calculRecovery(tableauDefault[i], LGD, 
        		DateCalcul.nbMonthsToNbPeriods(asset.getRecoveryProfile().getStartRecovery(), asset),
        		DateCalcul.nbMonthsToNbPeriods(asset.getRecoveryProfile().getEndRecovery(), asset));//after x periods
        	}     
            
            tableauVarOstding[i] = CalculMethod.calculVarOutstanding(tableauPrincipal[i], tableauPrepayment[i], tableauDefault[i], 
            		0, tableauOutstanding[i-1]);
            tableauOutstanding[i] = CalculMethod.calculOutstanding(tableauOutstanding[i-1], tableauVarOstding[i]);
            tableauPayoff[i] = CalculMethod.calculPayoff(tableauPrincipal[i], tableauInterest[i], tableauRecovery[i], tableauPrepayment[i]);

        }//end calculating basic cashflow elements
        
        //extension because of recovery
        for(int i=nbPeriods+1;i<=nbPeriods+asset.getRecoveryProfile().getEndRecovery();i++){

	        tableauDate[i] = DateCalcul.EDate(nextPaymentDate, i-1, asset.getPeriodicity());
            //i-1 because the tableauDate[0] is the data date
        	tableauPayoff[i] = CalculMethod.calculPayoff(tableauPrincipal[i], tableauInterest[i], tableauRecovery[i], tableauPrepayment[i]);
        	if(tableauPayoff[i]<errorMargin){
        		break;
        	}
        }//the recovery may be longer than the loan's life

    }//end calcul
    
    
    //Getters, Setters
    
    public Asset getAsset() {
	    return asset;
    }    

    public double getTableauInterest(int i) {
	    return tableauInterest[i];
    }

    public double getTableauPrincipal(int i) {
	    return tableauPrincipal[i];
    }

    public double getTableauOutstanding(int i) {
	    return tableauOutstanding[i];
    }

    public double getTableauDefault(int i) {
	    return tableauDefault[i];
    }

    public double getTableauRecovery(int i) {
	    return tableauRecovery[i];
    }

    public double getTableauPayoff(int i) {
	    return tableauPayoff[i];
    }
    
    public double getTableauPrepayment(int i){
    	return tableauPrepayment[i];
    }
    
    public double getTableauVarOstding(int i){
    	return tableauVarOstding[i];
    }
        
    public Calendar getTableauDate(int i) {
		return tableauDate[i];
	}
            
    public int getNbPeriods() {
	    return nbPeriods;
    }
    

}