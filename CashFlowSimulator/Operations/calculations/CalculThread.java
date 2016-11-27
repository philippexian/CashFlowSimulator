package calculations;

import java.util.ArrayList;
import processing.Processing;
import assets.Asset;


public class CalculThread extends Thread{

	private int start;
	private int end;
	private static int maxYears=Processing.getMaxYears();
	private static double errorMargin=Processing.getErrorMargin();
	
	private double[] sumPrincipal;
	private double[] sumInterest;
	private double[] sumPayoff;
	private double[] sumDefault;
	private double[] sumRecovery;
	private double[] sumPrepayment;
	private double[] sumVarOstding;
	
	public CalculThread(int start, int end){
		//start, end delimit the part of portfolio to be calculated in this thread
		this.start=start;
		this.end=end;
		int nbPeriodsPY=12;
    	switch(Processing.getTimeLine()){
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
			nbPeriodsPY=(int)(Processing.getNbOfDays()/7);
		    break;
		case('D'):
			nbPeriodsPY=Processing.getNbOfDays();//depend on the nb of days
		    break;
		}
    	
		sumPrincipal=new double[maxYears*nbPeriodsPY];
		sumInterest=new double[maxYears*nbPeriodsPY];
		sumPayoff=new double[maxYears*nbPeriodsPY];
		sumDefault=new double[maxYears*nbPeriodsPY];
		sumRecovery=new double[maxYears*nbPeriodsPY];
		sumPrepayment=new double[maxYears*nbPeriodsPY];
		sumVarOstding=new double[maxYears*nbPeriodsPY];
		
	}
	

	public void run(){
		
		ArrayList<Asset> portfolio=Processing.getPortfolio();
		
		for(int i=start;i<end;i++){

			//calculate the cashflow of each asset 
    		CashflowAsset cashflowAsset=new CashflowAsset(portfolio.get(i));
    		
            
    		try {
				cashflowAsset.Calcul();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
    		
    		//calculate the cash flow sum with the correct date
    		for(int j=1;(j<=cashflowAsset.getNbPeriods()) || (cashflowAsset.getTableauPayoff(j)>=errorMargin);j++){
    			
    			int k=DateCalcul.difDate(Processing.getDataDate(), cashflowAsset.getTableauDate(j), Processing.getTimeLine());
    			
    			sumPayoff[k]+=cashflowAsset.getTableauPayoff(j);
				sumInterest[k]+=cashflowAsset.getTableauInterest(j);
				sumPrincipal[k]+=cashflowAsset.getTableauPrincipal(j);
				sumDefault[k]+=cashflowAsset.getTableauDefault(j);
				sumRecovery[k]+=cashflowAsset.getTableauRecovery(j);
				sumPrepayment[k]+=cashflowAsset.getTableauPrepayment(j);
				sumVarOstding[k]+=cashflowAsset.getTableauVarOstding(j);
    		}
		}
		
	}//end run
	
	
	public double getSumPrincipal(int i){
		return sumPrincipal[i];
	}

	public double getSumInterest(int i){
		return sumInterest[i];
	}

	public double getSumPayoff(int i){
		return sumPayoff[i];
	}
	
	public double getSumDefault(int i){
		return sumDefault[i];
	}
	
	public double getSumRecovery(int i){
		return sumRecovery[i];
	}
	
	public double getSumPrepayment(int i){
		return sumPrepayment[i];
	}
	
	public double getSumVarOstding(int i){
		return sumVarOstding[i];
	}

	
	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
	
	
}
