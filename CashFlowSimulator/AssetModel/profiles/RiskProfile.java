package profiles;

import java.util.ArrayList;

import references.RiskModel;

public class RiskProfile extends Profile{
	
	private RiskModel model;

	public RiskProfile(char type, double rate){
		super(type);
		this.setRate(rate);
	}
	
	public RiskProfile(char type, RiskModel model){
		super(type);
		this.model=model;
	}

	//getters, setters
	public RiskModel getModel() {
		return model;
	}

	public void setModel(RiskModel model) {
		this.model = model;
	}

	public ArrayList<int[]> getDateList(){
		return new ArrayList<int[]>(this.model.getModelMap().keySet());
	}
	
	public ArrayList<Double> getRateList(){
		return new ArrayList<Double>(this.model.getModelMap().values());
	}
	
}
