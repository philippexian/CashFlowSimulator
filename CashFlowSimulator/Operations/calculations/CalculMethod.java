package calculations;

public class CalculMethod {
	
	//deterministic method: calculate the expectation of default and its effects
	
	
	public static double calculInterest(double formerOS, double rate, double DPPerYear, double nbPeriodsPY){
		double DPPerPeriod=1-Math.pow((1-DPPerYear), 1/nbPeriodsPY);
		
		return formerOS*rate*(1-DPPerPeriod);
		//default proba during the period i is DPPerPeriod
	}
	
	//prepayment rate must be transformed to a rate per period
	public static double calculPrepayment(double OS, double prepaymentRate, double DPPerYear, double nbPeriodsPY){
		double DPPerPeriod=1-Math.pow((1-DPPerYear), 1/nbPeriodsPY);
		return OS*(prepaymentRate/nbPeriodsPY)*(1-DPPerPeriod);
	}
	
	
	public static double calculPrincipal(double formerOS, double rate, int nbPeriods, double prepaymentRate, 
			double DPPerYear, double nbPeriodsPY, char amortizationType) throws Exception {
		double principal=0;
		double DPPerPeriod=1-Math.pow((1-DPPerYear), 1/nbPeriodsPY);
		double prepaymentRatePP=prepaymentRate/nbPeriodsPY;
		formerOS*=(1-prepaymentRatePP);//do not consider the prepayment
		
		if(nbPeriods==1){
			principal=formerOS;
		}//the last period all the rest of the outstanding is payed in principal
		
		else{
			if(amortizationType=='A'){//annuity
				principal=calculAnnuityPayoff(rate, formerOS, nbPeriods)-formerOS*rate;
			}
			if(amortizationType=='E'){//equal
				if(nbPeriods!=0){
					principal=formerOS/nbPeriods;
				}
			}
		}

		return principal*(1-DPPerPeriod);//expectation: normal principal value * proba that it doesn't fall in default
	}
	
	
			//transient function, to calculate the annuity principal
			public static double calculAnnuityPayoff(double RatePP, double capital, double nbPeriods) throws Exception{
				double payoff;
				if(RatePP!=0){
				    payoff=(capital*RatePP)/(1-Math.pow(1+RatePP, (-1)*nbPeriods));
				    if(nbPeriods==0){
				    	throw new Exception("nbPeriods is 0!");
				    }
				    if(1-Math.pow(1+RatePP, (-1)*nbPeriods)==0){
				    	throw new Exception("divided by 0!");
				    }
		
				}
				else{
					payoff=capital/nbPeriods;			
				}
				
			    return payoff;
			}//monthly payment=capital*r/(1 -pow(1+r,-n))
			
	
	//expectation of default: Outstanding * DP per period
    public static double calculDefault(double OS, double DPPerYear, double nbPeriodsPY){
		double DPPerPeriod=1-Math.pow((1-DPPerYear), 1/nbPeriodsPY);
		
		return OS*DPPerPeriod;
	}

    //end second method
    

    //calculate recovery of EACH recovery period
	public static double calculRecovery(double Default, double LGD, int startRecoveryPeriod, int endRecoveryPeriod){
		if(endRecoveryPeriod<=startRecoveryPeriod){
			return Default*(1-LGD);
		}
		else{
			return Default*(1-LGD)/(endRecoveryPeriod-startRecoveryPeriod+1);
		}
		
	}
	
	//the variation of outstanding must not exceed the former OS
	public static double calculVarOutstanding(double principal, double prepayment, double Default, double capitalizedInterest, 
		double formerOS){
		return Math.min((principal+prepayment+Default-capitalizedInterest), formerOS);
	}
	
	public static double calculOutstanding(double formerOS, double varOS) {
		return formerOS-varOS;
	}

	public static double calculPayoff(double principal, double interest, double recovery, double prepayment){
		return (principal+interest+recovery+prepayment);
	}
	

	//here in case of default, the interest is lost but not taken account in the LGD
}
