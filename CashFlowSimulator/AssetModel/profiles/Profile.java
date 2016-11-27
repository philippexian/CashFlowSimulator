package profiles;

public class Profile {

	private char rateType;

	private double rate;//rate is the fixed rate
	
	public Profile(char type){
		this.rateType = type;
	}
		
	
	//getters, setters
	public char getRateType() {
		return rateType;
	}

	public void setRateType(char rateType) {
		this.rateType = rateType;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	
}
