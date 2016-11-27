package references;

import java.util.TreeMap;

import calculations.DateCalcul;

public class RiskModel {

	private String modelName;
	private TreeMap<int[], Double> modelMap;
	
	public RiskModel(String name){//characterized only by its name
		this.modelName=name;
		this.modelMap=new TreeMap<int[], Double>(DateCalcul.myComparator);
	}
	
	//getters, setters
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public TreeMap<int[], Double> getModelMap() {
		return modelMap;
	}

	public void setModelMap(TreeMap<int[], Double> modelMap) {
		this.modelMap = modelMap;
	}

}
