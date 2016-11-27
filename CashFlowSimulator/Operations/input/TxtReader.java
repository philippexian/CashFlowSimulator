package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import assets.Asset;

public class TxtReader {

    
    public static ArrayList<String[]> readTxtFiles(String filePath, String delimiter) {
          ArrayList<String[]> table=new ArrayList<String[]>();
          int counter=0;
          
          try {
                                  
                 
                 File file = new File(filePath);
                 FileReader fileReader = new FileReader(file);
                 BufferedReader bufferedReader = new BufferedReader(fileReader);
                 String line;
                 
                 while ((line= bufferedReader.readLine()) != null && !line.isEmpty()) {

                        //analyze the line and find parameters
                        table.add(line.split(delimiter));

                        
                        counter++;
                 }
                 fileReader.close();

          } catch (IOException e) {
                 e.printStackTrace();
                 System.out.println("fail to read the text file!");
                 System.out.println(counter);
          }
          
          return table;
    }
    
    
	public static ArrayList<Asset> setFromTxtFile(String filePath, String delimiter, int nbOfColumns) throws Exception{
    	ArrayList<Asset> port=new ArrayList<Asset>();//create a portfolio: an asset list
    	String[] row=new String[nbOfColumns];//user can set the number of columns
    	
    	
        int counter=0;
        
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            
            while ((line= bufferedReader.readLine()) != null) {
            	
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
        		}
            }
            
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail to read the text file!");
        }
        
        return port;

	}

}
