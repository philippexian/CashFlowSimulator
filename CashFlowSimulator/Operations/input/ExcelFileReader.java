package input;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import assets.Asset;

 
/**
 * 
 * XSSF and XML Stream Reader
 * 
 * If memory footprint is an issue, then for XSSF, you can get at the underlying XML data, and process it yourself. This is intended for intermediate
 * developers who are willing to learn a little bit of low level structure of .xlsx files, and who are happy processing XML in java. Its relatively
 * simple to use, but requires a basic understanding of the file structure. The advantage provided is that you can read a XLSX file with a relatively
 * small memory footprint.
 * 
 * @author lchen
 * 
 */
public class ExcelFileReader{
    private int rowNum = 0;
    private OPCPackage opcPkg;
    private ReadOnlySharedStringsTable stringsTable;
    private XMLStreamReader xmlReader;
 
    public ExcelFileReader(String excelPath) throws Exception {
        opcPkg = OPCPackage.open(excelPath, PackageAccess.READ);
        this.stringsTable = new ReadOnlySharedStringsTable(opcPkg);
 
        XSSFReader xssfReader = new XSSFReader(opcPkg);
        InputStream inputStream = xssfReader.getSheetsData().next();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        xmlReader = factory.createXMLStreamReader(inputStream);
 
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (xmlReader.isStartElement()) {
                if (xmlReader.getLocalName().equals("sheetData"))
                    break;
            }
        }
    }
 

    public int rowNum() {
        return rowNum;
    }
    
    public XMLStreamReader getXmlReader(){
    	return xmlReader;
    }

    public ArrayList<String[]> readRows(int batchSize) throws XMLStreamException {//batchSize is the max number of rows it can read
        String elementName = "row";
        ArrayList<String[]> dataRows = new ArrayList<String[]>();
        if (batchSize > 0) {
            while (xmlReader.hasNext()) {
                xmlReader.next();
                if (xmlReader.isStartElement()) {
                    if (xmlReader.getLocalName().equals(elementName)) {
                        rowNum++;
                        dataRows.add(getDataRow());
                        if (dataRows.size() == batchSize)
                            break;
                    }
                }
            }
        }
        return dataRows;
    }
    
    
    public ArrayList<Asset> setFromXmlFile(int nbOfColumns) throws Exception{
    	ArrayList<Asset> port=new ArrayList<Asset>();//create a portfolio: an asset list
    	String[] row=new String[nbOfColumns];//user can set the number of columns
    	
    	while(xmlReader.hasNext()){
    		
    		String[] readRow=getARow();
    		
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

    		if(rowNum>1){//avoid the header
                port.add(new Asset(row));
    		}

    	}//end while
    	
    	return port;
    }
	
    
	public String[] getARow() throws XMLStreamException {
    	xmlReader.next();
        if (xmlReader.isStartElement()) {
            if (xmlReader.getLocalName().equals("row")) {
                rowNum++;
            }
        }
        return getDataRow();
    }

    private String[] getDataRow() throws XMLStreamException {
        List<String> rowValues = new ArrayList<String>();
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (xmlReader.isStartElement()) {
                if (xmlReader.getLocalName().equals("c")) {
                    CellReference cellReference = new CellReference(xmlReader.getAttributeValue(null, "r"));
                    // Fill in the possible blank cells!
                    while (rowValues.size() < cellReference.getCol()) {
                        rowValues.add("");
                    }
                    String cellType = xmlReader.getAttributeValue(null, "t");
                    rowValues.add(getCellValue(cellType));
                }
            } 
            else if (xmlReader.isEndElement() && xmlReader.getLocalName().equals("row")) {
                break;
            }
        }
        return rowValues.toArray(new String[rowValues.size()]);
    }
 
    private String getCellValue(String cellType) throws XMLStreamException {
        String value = ""; // by default
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (xmlReader.isStartElement()) {
                if (xmlReader.getLocalName().equals("v")) {
                    if (cellType != null && cellType.equals("s")) {
                        int idx = Integer.parseInt(xmlReader.getElementText());
                        return new XSSFRichTextString(stringsTable.getEntryAt(idx)).toString();
                    } else {
                        return xmlReader.getElementText();
                    }
                }
            } else if (xmlReader.isEndElement() && xmlReader.getLocalName().equals("c")) {
                break;
            }
        }
        return value;
    }
 
    boolean flag=false;
 
    @Override
    protected void finalize() throws Throwable {

         if (opcPkg != null){
    	     flag=true;
    	     opcPkg.close();
         }
         super.finalize();
    }
 

 
}
