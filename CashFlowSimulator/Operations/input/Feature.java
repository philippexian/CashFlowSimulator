package input;

public class Feature {

	private int columnNumber;
	private String type;//type is the input type
	private Object defaultValue;
	
	public Feature(int columnNumber, String type, Object defaultValue) {

		this.type = type;
		this.defaultValue=defaultValue;

		if(columnNumber>=97){//lower case
			this.columnNumber=columnNumber-97;
		}
		else if(columnNumber>=65&&columnNumber<=90){//upper case
			this.columnNumber=columnNumber-65;
		}
		else if(columnNumber<0){//if it doesn't appear in the file, columnNumber=-1
			this.columnNumber='\u0000';//null character
		}
		else{
			this.columnNumber=columnNumber-1;//1 for the first column
		}

	}
	

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
