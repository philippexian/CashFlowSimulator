package references;

import java.util.TreeMap;

import calculations.DateCalcul;

public class Index {

	private String name;
	private String currency;
	private TreeMap<int[], Double> indexMap;
	
	public Index(String name, String currency) {//characterized by its name (basis included) and currency
		this.name = name;
		this.currency=currency;
		this.indexMap=new TreeMap<int[], Double>(DateCalcul.myComparator);
	}

	//getters, setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public TreeMap<int[], Double> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(TreeMap<int[], Double> indexMap) {
		this.indexMap = indexMap;
	}
	
}
