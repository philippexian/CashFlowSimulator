package calculations;

import java.util.ArrayList;
import java.util.Calendar;

import processing.Processing;
import assets.Asset;

public class CashflowPf {

	//entry

    private int maxYears=Processing.getMaxYears();
    private int tableauSize=0;
    private double errorMargin=Processing.getErrorMargin();
    
    private Calendar[] tableauDate;    
    private double[] tableauInterestPort;
    private double[] tableauPrincipalPort;
    private double[] tableauOutstandingPort;
    private double[] tableauPayoffPort;
    private double[] tableauDefaultPort;
    private double[] tableauRecoveryPort;
    private double[] tableauPrepaymentPort;
    private double[] tableauVarOstdingPort;
    
    //each time the new asset replaces the old one and its result is added to the tableau of portfolio

    public CashflowPf(){
    	
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
			nbPeriodsPY=(int)(Processing.getNbOfDays()/7);//should be day (nb of days)/7
		    break;
		case('D'):
			nbPeriodsPY=Processing.getNbOfDays();//should be nb of days
		    break;
		}
    	
    	tableauDate=new Calendar[maxYears*nbPeriodsPY];
        tableauInterestPort=new double[maxYears*nbPeriodsPY];
        tableauPrincipalPort=new double[maxYears*nbPeriodsPY];
        tableauOutstandingPort=new double[maxYears*nbPeriodsPY];
        tableauPayoffPort=new double[maxYears*nbPeriodsPY];
        tableauDefaultPort=new double[maxYears*nbPeriodsPY];
        tableauRecoveryPort=new double[maxYears*nbPeriodsPY];
        tableauPrepaymentPort=new double[maxYears*nbPeriodsPY];
        tableauVarOstdingPort=new double[maxYears*nbPeriodsPY];
    }
    

	//calculations
	
	public double calculTotalOutstanding(){
		double sum=0;
		for(Asset asset:Processing.getPortfolio()){
			sum+=asset.getOutstanding();
		}
		return sum;
	}//set the sum of the capitals as the capital of the portfolio
    

	public void Calcul() throws Exception{
    	
	    int nbAssets=Processing.getPortfolio().size();
	    int nbThreads=4;
	    //we can change the number of threads
	    ArrayList<CalculThread> threads=new ArrayList<CalculThread>();
	    
	    //divide the whole portfolio into several parts, each thread charge of one part
	    for(int i=0;i<nbThreads;i++){
	    	threads.add(new CalculThread(i*nbAssets/nbThreads, (i+1)*nbAssets/nbThreads));
	    }
    	
    	//execute n threads in parallel
    	
    	try{
    		for(int i=0;i<nbThreads;i++){
    			threads.get(i).start();
    		}
    		for(int i=0;i<nbThreads;i++){
    			threads.get(i).join();
    		}
    	}
    	catch (InterruptedException e) {   
    		e.printStackTrace();   
    	}
    	
    	//sum up calculation results from threads
    	
    	tableauOutstandingPort[0]=calculTotalOutstanding();

    	tableauDate[0]=DateCalcul.EDate(Processing.getDataDate(), 0, Processing.getTimeLine());
    	
    	    
    	for(int j=1;j<tableauDate.length;j++){//avoid index out of bound
    	    	
    	    tableauSize++;

    	    tableauDate[j]=DateCalcul.EDate(Processing.getDataDate(), j, Processing.getTimeLine());
    	    	
    		for(int i=0;i<nbThreads;i++){
    			tableauPrincipalPort[j]+=threads.get(i).getSumPrincipal(j);
    			tableauInterestPort[j]+=threads.get(i).getSumInterest(j);
    			tableauPayoffPort[j]+=threads.get(i).getSumPayoff(j);
    			tableauDefaultPort[j]+=threads.get(i).getSumDefault(j);
    			tableauRecoveryPort[j]+=threads.get(i).getSumRecovery(j);
    			tableauPrepaymentPort[j]+=threads.get(i).getSumPrepayment(j);
    			tableauVarOstdingPort[j]+=threads.get(i).getSumVarOstding(j);
    		}
    		
    		tableauOutstandingPort[j]=tableauOutstandingPort[j-1]-tableauVarOstdingPort[j];
    		
    		if((tableauOutstandingPort[j]<50*errorMargin) && (tableauPayoffPort[j]<errorMargin)){
    			//when the residual outstanding falls below the materiality threshold*50 and the total cashflow falls below the materiality threshold
    			//we stop
        	    break;
        	}
    	}
    	    
    }//end run


    public double getTableauInterestPort(int i) {
		return tableauInterestPort[i];
	}
	
	public double getTableauPrincipalPort(int i) {
		return tableauPrincipalPort[i];
	}
	
	public double getTableauOutstandingPort(int i) {
		return tableauOutstandingPort[i];
	}
	
	public double getTableauPayoffPort(int i) {
		return tableauPayoffPort[i];
	}
	
	public double getTableauDefaultPort(int i){
		return this.tableauDefaultPort[i];
	}
	
	public double getTableauRecoveryPort(int i){
		return this.tableauRecoveryPort[i];
	}
	
	public double getTableauPrepaymentPort(int i){
		return this.tableauPrepaymentPort[i];
	}
	
	public double getTableauVarOstdingPort(int i){
		return this.tableauVarOstdingPort[i];
	}

	public Calendar getTableauDate(int i) {
		return tableauDate[i];
	}
	
    public int getTableauSize() {
		return tableauSize;
	}

	public void setTableauSize(int tableauSize) {
		this.tableauSize = tableauSize;
	}
	
	public double[] getTableauInterestPort() {
		return tableauInterestPort;
	}
	
	public double[] getTableauPrincipalPort() {
		return tableauPrincipalPort;
	}
	
	public double[] getTableauOutstandingPort() {
		return tableauOutstandingPort;
	}
	
	public double[] getTableauPayoffPort() {
		return tableauPayoffPort;
	}
	
	public double[] getTableauDefaultPort(){
		return tableauDefaultPort;
	}
	
	public double[] getTableauRecoveryPort(){
		return tableauRecoveryPort;
	}
	
	public double[] getTableauPrepaymentPort(){
		return tableauPrepaymentPort;
	}
	
	public double[] getTableauVarOstdingPort(){
		return tableauVarOstdingPort;
	}

	public Calendar[] getTableauDate() {
		return tableauDate;
	}

	
}
