package profiles;

import java.util.ArrayList;
import java.util.Calendar;

import calculations.DateCalcul;
import references.Index;

public class InterestRateProfile extends Profile{
	
	private Index index;
	private double cap;
	private double floor;
	private double margin;
	private char capFloorType;
	private int[] fixedToFloatingDate;

	
	public InterestRateProfile(char type, double fixedRate){
		super(type);	
		this.setRate(fixedRate);	
	}
	
	public InterestRateProfile(char type, double fixedRate, char capFloorType, double capLevel, 
			double floorLevel, double margin, Index index, Calendar fixedToFloatingDate) {
		super(type);
		
		this.index=index;

		this.cap=capLevel;
		this.floor=floorLevel;
		this.margin=margin;
		this.capFloorType=capFloorType;
		
		if(type=='F'){
			this.setRate(fixedRate);
			this.fixedToFloatingDate=DateCalcul.parseCalendar(fixedToFloatingDate);
		}
	}

	//getters, setters
	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public double getCap() {
		return cap;
	}

	public void setCap(double cap) {
		this.cap = cap;
	}

	public double getFloor() {
		return floor;
	}

	public void setFloor(double floor) {
		this.floor = floor;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public char getCapFloorType() {
		return capFloorType;
	}

	public void setCapFloorType(char capFloorType) {
		this.capFloorType = capFloorType;
	}

	public int[] getFixedToFloatingDate() {
		return fixedToFloatingDate;
	}

	public void setFixedToFloatingDate(int[] fixedToFloatingDate) {
		this.fixedToFloatingDate = fixedToFloatingDate;
	}

	public ArrayList<int[]> getDateList(){
		return new ArrayList<int[]>(this.index.getIndexMap().keySet());
	}
	
	public ArrayList<Double> getRateList(){
		return new ArrayList<Double>(this.index.getIndexMap().values());
	}
	

}
