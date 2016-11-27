package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import assets.Asset;

public class CsvReader {
    

    public static ArrayList<String[]> readCsvFiles(String filePath, String delimiter) {//each element in array list is a row
        ArrayList<String[]> table=new ArrayList<String[]>();
        int counter=0;
        
        try {

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            
            while((line=br.readLine())!=null && !line.isEmpty()){
                String[] b = line.split(delimiter);
                table.add(b);
            }
            
            br.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("fail to read the csv file!");
            System.out.println(counter);
        }
        return table;
    }
    
    
	public static ArrayList<Asset> setFromCsvFile(String filePath, String delimiter, int nbOfColumns) throws Exception{
    	ArrayList<Asset> port=new ArrayList<Asset>();//create a portfolio: an asset list
    	String[] row=new String[nbOfColumns];//user can set the number of columns
    	
        int counter=0;
    	try {

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            
            while((line=br.readLine())!=null){
            	counter++;
        		
        		String[] readRow=line.split(delimiter);
        		
        		if(readRow.length==0){
        			break;
        		}
        		
        		else{
            		for(int j=0;j<readRow.length;j++){
                		row[j]=readRow[j];
                	}
                	for(int j=readRow.length;j<nbOfColumns;j++){//fill up the row
                		row[j]="";
                	}
        		}

        		if(counter>1){//avoid the header
                    port.add(new Asset(row));
        		}//
            }
            
            br.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("fail to read the csv file!");
        }
    	
    	return port;
	}
}
