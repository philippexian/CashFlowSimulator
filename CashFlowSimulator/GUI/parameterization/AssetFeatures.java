package parameterization;

import input.DataModel;
import input.Feature;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import processing.Processing;

public class AssetFeatures implements ActionListener{

	public JFrame mainFrame;
	private JLabel headerLabel;
	
	private JPanel controlPanel;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	
	private JButton back = new JButton("back");
	private JButton confirm = new JButton("next");
	
	private JComboBox[] jcs=new JComboBox[20];
	private JTextField[] jtfs=new JTextField[20];
	private JLabel[] slashes=new JLabel[15];
	private JCheckBox[] jcbs=new JCheckBox[15];
	
	private int jcCount=0;
	private int jtfCount=0;
	private int slashCount=0;
	private int jcbCount=0;
	
	File saveParam1=new File("AssetFeatures.txt");
	
	
	public AssetFeatures(){
		prepareGUI();
    }

    public static void entry(){
    	AssetFeatures example= new AssetFeatures();
		example.showJPanelDemo();
    }

    private void prepareGUI(){
	    mainFrame = new JFrame("AssetFeatures");
		mainFrame.setSize(800,650);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BorderLayout(40, 30));

		mainFrame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent windowEvent){
		        System.exit(0);
		    }        
		});    
		headerLabel = new JLabel("", JLabel.CENTER);          

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		
		mainPanel = new JPanel(new java.awt.GridBagLayout());
		buttonPanel  = new JPanel();
		
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	      
	    mainFrame.add(controlPanel, BorderLayout.NORTH);
		mainFrame.add(buttonPanel, BorderLayout.SOUTH);
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		mainFrame.setVisible(true);  
    }
    
	private void showJPanelDemo(){
    	//title
	    headerLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 32)); 
		headerLabel.setText("    Please set asset features        "); 
		controlPanel.add(BorderLayout.NORTH, headerLabel);
		
		//grid preparation
	    GridBagConstraints labCnst = new GridBagConstraints();
	    labCnst.insets = new Insets(5, 5, 5, 5);
	    labCnst.anchor=GridBagConstraints.WEST;
	    
	    Dimension nameSize = new Dimension(180,20);
	    Dimension CNSize = new Dimension(120,20);
	    Dimension typeSize = new Dimension(120,20);
	    
	    JLabel emptyLabel=new JLabel("");
	    emptyLabel.setPreferredSize(typeSize);
	    
	    //header
	    JLabel nameLabel=new JLabel("Feature name");
	    nameLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
	    nameLabel.setPreferredSize(nameSize);
        labCnst.gridx = 0;
        labCnst.gridy = 0;
        mainPanel.add(nameLabel, labCnst);
	    
	    JLabel CNLabel=new JLabel("Column number");
		CNLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		CNLabel.setPreferredSize(CNSize);
        labCnst.gridx++;
        mainPanel.add(CNLabel, labCnst);
        
        JLabel typeLabel=new JLabel("Data type");
		typeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		typeLabel.setPreferredSize(typeSize);
        labCnst.gridx++;
        mainPanel.add(typeLabel, labCnst);
        
        JLabel DVLabel=new JLabel("Default value");
		DVLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
        labCnst.gridx++;
        mainPanel.add(DVLabel, labCnst);
        
		//start date
		JLabel startDateLabel=new JLabel("Start date");
		startDateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		startDateLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(startDateLabel, labCnst);
        
        JPanel startDateCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//1
        startDateCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//1
        startDateCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(startDateCN, labCnst);
		
		ArrayList<String> startDateT=new ArrayList<String>();
		if(Processing.getFileType().contains("xls")){
			startDateT.add("excel date");
		}
		else{
			startDateT.add("date string");
		}
		jcs[jcCount]= new JComboBox<String>();//1
		jcs[jcCount].setModel(new DefaultComboBoxModel(startDateT.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel startDateDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(2);//2
		startDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3]=new JLabel("DD/", JLabel.CENTER);  
		slashes[slashCount*3].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		startDateDV.add(slashes[slashCount*3]);
		
		jtfs[jtfCount]=new JTextField(2);//3
		startDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3+1]=new JLabel("MM/", JLabel.CENTER);  
		slashes[slashCount*3+1].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		startDateDV.add(slashes[slashCount*3+1]);
		
		jtfs[jtfCount]=new JTextField(4);//4
		startDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3+2]=new JLabel("YYYY", JLabel.CENTER);  
		slashes[slashCount*3+2].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		startDateDV.add(slashes[slashCount*3+2]);
		
		slashCount++;
		
		labCnst.gridx++;
		mainPanel.add(startDateDV, labCnst);
		
		//end date
		JLabel endDateLabel=new JLabel("End date");
		endDateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(endDateLabel, labCnst);
		
        JPanel endDateCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//2
        endDateCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//5
        endDateCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(endDateCN, labCnst);
		
		ArrayList<String> endDateT=new ArrayList<String>();
		if(Processing.getFileType().contains("xls")){
			endDateT.add("excel date");
		}
		else{
			endDateT.add("date string");
		}
		jcs[jcCount]= new JComboBox<String>();//2
		jcs[jcCount].setModel(new DefaultComboBoxModel(endDateT.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel endDateDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(2);//6
		endDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3]=new JLabel("DD/", JLabel.CENTER);  
		slashes[slashCount*3].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		endDateDV.add(slashes[slashCount*3]);
		
		jtfs[jtfCount]=new JTextField(2);//7
		endDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3+1]=new JLabel("MM/", JLabel.CENTER);  
		slashes[slashCount*3+1].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		endDateDV.add(slashes[slashCount*3+1]);
		
		jtfs[jtfCount]=new JTextField(4);//8
		endDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3+2]=new JLabel("YYYY", JLabel.CENTER);  
		slashes[slashCount*3+2].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		endDateDV.add(slashes[slashCount*3+2]);
		
		slashCount++;
		
		labCnst.gridx++;
		mainPanel.add(endDateDV, labCnst);
		
        //outstanding
		JLabel outstandingLabel=new JLabel("Outstanding");
		outstandingLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		outstandingLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(outstandingLabel, labCnst);
        
        JPanel outstandingCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//3
        outstandingCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//9
        outstandingCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(outstandingCN, labCnst);
		
		JLabel outstandingT=new JLabel("double");
		outstandingT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		outstandingT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(outstandingT, labCnst);
		
		jtfs[jtfCount]=new JTextField(6);//10
		labCnst.gridx++;
		mainPanel.add(jtfs[jtfCount], labCnst);
		jtfCount++;
		
		//periodicity
		JLabel periodicityLabel=new JLabel("Periodicity");
		periodicityLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		periodicityLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(periodicityLabel, labCnst);
        
        JPanel periodicityCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//5
        periodicityCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//13
        periodicityCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(periodicityCN, labCnst);
		
		JLabel periodicityT=new JLabel("string");
		periodicityT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		periodicityT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(periodicityT, labCnst);
		
		ArrayList<String> periodicityDV=new ArrayList<String>();
		periodicityDV.add("monthly");
		periodicityDV.add("quarterly");
		periodicityDV.add("biannual");
		periodicityDV.add("annual");
		periodicityDV.add("weekly");
		periodicityDV.add("daily");
		periodicityDV.add("infine");
		jcs[jcCount]= new JComboBox<String>();//4
		jcs[jcCount].setModel(new DefaultComboBoxModel(periodicityDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//amortization type
		JLabel amortizationTypeLabel=new JLabel("Amortization type");
		amortizationTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		amortizationTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(amortizationTypeLabel, labCnst);
        
        JPanel amortizationTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//6
        amortizationTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//14
        amortizationTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(amortizationTypeCN, labCnst);
		
		JLabel amortizationTypeT=new JLabel("string");
		amortizationTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		amortizationTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(amortizationTypeT, labCnst);
		
		ArrayList<String> amortizationTypeDV=new ArrayList<String>();
		amortizationTypeDV.add("annuity");
		amortizationTypeDV.add("equal");
		amortizationTypeDV.add("bullet");
		jcs[jcCount]= new JComboBox<String>();//5
		jcs[jcCount].setModel(new DefaultComboBoxModel(amortizationTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//differed payment type
		JLabel differedPaymentTypeLabel=new JLabel("Differed payment type");
		differedPaymentTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		differedPaymentTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(differedPaymentTypeLabel, labCnst);
        
        JPanel differedPaymentTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//7
        differedPaymentTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//15
        differedPaymentTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(differedPaymentTypeCN, labCnst);
		
		JLabel differedPaymentTypeT=new JLabel("string");
		differedPaymentTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		differedPaymentTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(differedPaymentTypeT, labCnst);
		
		ArrayList<String> differedPaymentTypeDV=new ArrayList<String>();
		differedPaymentTypeDV.add("none");
		differedPaymentTypeDV.add("partial");
		differedPaymentTypeDV.add("total");
		jcs[jcCount]= new JComboBox<String>();//6
		jcs[jcCount].setModel(new DefaultComboBoxModel(differedPaymentTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//differed payment duration
		JLabel differedPaymentDurationLabel=new JLabel("Differed payment duration");
		differedPaymentDurationLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		differedPaymentDurationLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(differedPaymentDurationLabel, labCnst);
        
        JPanel differedPaymentDurationCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//8
        differedPaymentDurationCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//16
        differedPaymentDurationCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(differedPaymentDurationCN, labCnst);
		
		JLabel differedPaymentDurationT=new JLabel("string");
		differedPaymentDurationT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		differedPaymentDurationT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(differedPaymentDurationT, labCnst);
		
		JPanel differedPaymentDurationDV=new JPanel(new FlowLayout());
		jtfs[jtfCount]=new JTextField(4);//17
        differedPaymentDurationDV.add(jtfs[jtfCount]);
		jtfCount++;
		JLabel differedMonths=new JLabel("months");
		differedMonths.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		differedPaymentDurationDV.add(differedMonths);
		
		labCnst.gridx++;
		mainPanel.add(differedPaymentDurationDV, labCnst);
		
		//attention
		JLabel attentionLabel=new JLabel("<html>Attention: please tick the tick box if this feature exists in the input file"
			+"<br>the system will take the default value in the absence of the feature's value in the file<br></html>", JLabel.CENTER);
		labCnst.gridwidth=600;
		labCnst.gridx=0;
		labCnst.gridy++;
		mainPanel.add(attentionLabel, labCnst);
		
		//buttons
		back.addActionListener(this);
		buttonPanel.add(back);
		confirm.addActionListener(this);
		buttonPanel.add(confirm);

		UPDATE();   
		mainFrame.setVisible(true); 
    }
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource()==back){
			mainFrame.setVisible(false);
			GeneralFeatures2.entry();
		}
		else{
			
			int countJc=0;
			int countJcb=0;
			
			//set parameters in data model
			/*DataModel.setAssetType(new Feature(columnNumberConverter(jtfs[0].getText(), countJcb++), "String",
			assetTypeConverter((String)jcs[countJc++].getSelectedItem())));//set asset type*/
			
			DataModel.setStartDate(new Feature(columnNumberConverter(jtfs[0].getText(), countJcb++), (String)jcs[countJc++].getSelectedItem(), 
			new GregorianCalendar(Integer.parseInt(jtfs[3].getText()), Integer.parseInt(jtfs[2].getText())-1, Integer.parseInt(jtfs[1].getText()))));
			//set start date
			
			DataModel.setEndDate(new Feature(columnNumberConverter(jtfs[4].getText(), countJcb++), (String)jcs[countJc++].getSelectedItem(), 
			new GregorianCalendar(Integer.parseInt(jtfs[7].getText()), Integer.parseInt(jtfs[6].getText())-1, Integer.parseInt(jtfs[5].getText()))));
			//set end date
			
			DataModel.setOutstanding(new Feature(columnNumberConverter(jtfs[8].getText(), countJcb++), "double", 
					Double.parseDouble(jtfs[9].getText())));//set outstanding
			
			/*DataModel.setDaycount(new Feature(columnNumberConverter(jtfs[11].getText(), countJcb++), "int", 
					Integer.parseInt(jtfs[12].getText())));//set day count*/
			
			DataModel.setPeriodicity(new Feature(columnNumberConverter(jtfs[10].getText(), countJcb++), "String", 
					periodicityConverter((String)jcs[countJc++].getSelectedItem())));//set periodicity
			
			DataModel.setAmortizationType(new Feature(columnNumberConverter(jtfs[11].getText(), countJcb++), "String", 
					amortizationTypeConverter((String)jcs[countJc++].getSelectedItem())));//set amortization type
			
			DataModel.setDifferedPaymentType(new Feature(columnNumberConverter(jtfs[12].getText(), countJcb++), "String", 
					differedPaymentTypeConverter((String)jcs[countJc++].getSelectedItem())));//set differed payment type
			
			DataModel.setDifferedPaymentDuration(new Feature(columnNumberConverter(jtfs[13].getText(), countJcb++), "int", 
					Integer.parseInt(jtfs[14].getText())));//set differed payment duration
			
			SAVE();
			RateFeatures.entry();
			mainFrame.setVisible(false);
		}
	}
	
	public void SAVE(){//Save the text of all text fields

        try {
            if(!saveParam1.exists()){
            	saveParam1.createNewFile();  //if the file !exist create a new one
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(saveParam1.getAbsolutePath()));
            for (int i=0;i<jtfCount;i++){
            	bw.write(jtfs[i].getText());
            	bw.newLine();
            }
            
            for(int i=0;i<jcbCount;i++){
            	if(jcbs[i].isSelected()){
            		bw.write("true");
            	}
            	else{
            		bw.write("false");
            	}
            	bw.newLine();
            }
            bw.close(); //close the BufferdWriter

        } catch (IOException e) { 
        	e.printStackTrace(); 
        	JOptionPane.showMessageDialog(null, e.toString(), "error in updating the data",
                    JOptionPane.ERROR_MESSAGE);
        }        

    }//End Of Save
	
	public void UPDATE(){ //UPDATE ON OPENING THE APPLICATION

        try {
            if(saveParam1.exists()){

                Scanner scan = new Scanner(saveParam1);   //Use Scanner to read the File

                for (int i=0;i<jtfCount;i++){
                	jtfs[i].setText(scan.nextLine());
                }
                
                for(int i=0;i<jcbCount;i++){
                	if(scan.nextLine().contains("true")){
                		jcbs[i].setSelected(true);
                	}
                }
                scan.close();
            }

        } catch (FileNotFoundException e) { 
        	e.printStackTrace();
        	JOptionPane.showMessageDialog(null, e.toString(), "error in updating the data",
                    JOptionPane.ERROR_MESSAGE);
        }                

    }//End OF UPDATE

	//converters
	private char assetTypeConverter(String selectedItem){
		char returnValue;
		switch(selectedItem){
		case("loan"):
			returnValue='L';
		    break;
		case("bond"):
			returnValue='B';
		    break;
		case("mortgage"):
			returnValue='M';
		    break;
		default:
			returnValue='L';
			break;
		}
		return returnValue;
	}
	
	private char periodicityConverter(String selectedItem){
		char returnValue;
		switch(selectedItem){
		case("monthly"):
			returnValue='M';
		    break;
		case("quarterly"):
			returnValue='Q';
		    break;
		case("biannual"):
			returnValue='B';
		    break;
		case("annual"):
			returnValue='A';
		    break;
		case("weekly"):
			returnValue='W';
		    break;
		case("daily"):
			returnValue='D';
		    break;
		case("infine"):
			returnValue='I';
		    break;
		default:
			returnValue='M';
			break;
		}
		return returnValue;
	}
	
	private char amortizationTypeConverter(String selectedItem){
		char returnValue;
		switch(selectedItem){
		case("annuity"):
			returnValue='A';
		    break;
		case("equal"):
			returnValue='E';
		    break;
		case("bullet"):
			returnValue='B';
		    break;
		default:
			returnValue='A';
			break;
		}
		return returnValue;
	} 
	
	private char differedPaymentTypeConverter(String selectedItem){
		char returnValue;
		switch(selectedItem){
		case("none"):
			returnValue='N';
		    break;
		case("partial"):
			returnValue='P';
		    break;
		case("total"):
			returnValue='T';
		    break;
		default:
			returnValue='N';
			break;
		}
		return returnValue;
	} 
	
	public int columnNumberConverter(String text, int jcbCount){
		if(jcbs[jcbCount].isSelected()){
			if(text.charAt(0)>=65){
				return text.charAt(0);
			}
			else{
				return Integer.parseInt(text);
			}
		}
		else{
			return -1;
		}

	}
	
}
