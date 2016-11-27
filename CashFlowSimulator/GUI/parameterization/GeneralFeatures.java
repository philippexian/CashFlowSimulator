package parameterization;

import input.DataModel;

import javax.swing.*;

import controlFlow.ImportModels;
import processing.Processing;

import java.awt.*;
import java.awt.event.ActionEvent;//
import java.awt.event.ActionListener;//
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class GeneralFeatures implements ActionListener{
	
	public JFrame mainFrame;
	private JLabel headerLabel;
	
	private JPanel mainPanel = new JPanel();
	private JPanel buttonPanel  = new JPanel();

	private JPanel dataDate=new JPanel();
	private JPanel rateProperty=new JPanel();
	private JPanel currency=new JPanel();
	private JPanel columnNumber=new JPanel();
	private JPanel maxYears=new JPanel();
	private JPanel nbOfDays=new JPanel();
	private JPanel dateFormat=new JPanel();

	private JLabel[] brs=new JLabel[15];
	private JComboBox[] jcs=new JComboBox[10];
	private JTextField[] jtfs=new JTextField[10];

	private int jcCount=0;
	private int jtfCount=0;
	private int brCount=0;
	
	private JPanel controlPanel;

	private ImageIcon imageIcon;
	private JLabel imageLabel;
	   
	private JButton back = new JButton("back");
	private JButton confirm = new JButton("next");

    private JLabel warningLabel;
    
    private File saveBasicParam=new File("GeneralFeatures.txt");


	public GeneralFeatures(){
		prepareGUI();
    }

    public static void entry(){
		GeneralFeatures example= new GeneralFeatures();
		example.showJPanelDemo();
    }

    private void prepareGUI(){
	    mainFrame = new JFrame("GeneralFeatures");
		mainFrame.setSize(800,650);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BorderLayout(30, 30));

		mainFrame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent windowEvent){
		        System.exit(0);
		    }        
		});    
		headerLabel = new JLabel("", JLabel.CENTER);          

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		
		mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	      
	    mainFrame.add(controlPanel, BorderLayout.NORTH);
		mainFrame.add(buttonPanel, BorderLayout.SOUTH);
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		mainFrame.setVisible(true);  
    }


    private void showJPanelDemo(){
    	
    	//header and image
	    headerLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 32)); 
		headerLabel.setText("    Some general features...        ");      

		imageIcon = new ImageIcon("hehe.gif"); //write the path of the doc
	    imageLabel = new JLabel(imageIcon);         //initialize JLabel
	    imageLabel.setBounds(0, 0, 2*imageIcon.getIconWidth(), 2*imageIcon.getIconHeight());
		JPanel imagePanel = new JPanel();
		
		imagePanel.setBackground(Color.WHITE);
	    imagePanel.setLayout(new FlowLayout());  
	    imagePanel.add(imageLabel);

	    controlPanel.add(BorderLayout.WEST, headerLabel);
		controlPanel.add(BorderLayout.EAST, imagePanel); 
		
		//input settings
		JLabel inputSettingLabel=new JLabel("Input settings: ");
		inputSettingLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		inputSettingLabel.setForeground(Color.BLACK);
	    mainPanel.add(BorderLayout.WEST, inputSettingLabel);
		brs[brCount]= new JLabel("                                                                                                          "
				+"                                                                                                                          ");
		mainPanel.add(brs[brCount]);
		brCount++;
	    
		//data date
	    JLabel dataDateLabel = new JLabel("        Data date:    ", JLabel.CENTER);  
		dataDateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		dataDateLabel.setForeground(Color.BLACK);
	    
	    JLabel slash1 = new JLabel("DD/", JLabel.CENTER);  
		slash1.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		slash1.setForeground(Color.BLACK);
		
		JLabel slash2 = new JLabel("MM/", JLabel.CENTER);  
		slash2.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		slash2.setForeground(Color.BLACK);
		
		JLabel slash3 = new JLabel("YYYY", JLabel.CENTER);  
		slash3.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		slash3.setForeground(Color.BLACK);
		
		dataDate.add(dataDateLabel);
		
		jtfs[jtfCount]=new JTextField(2);
		dataDate.add(jtfs[jtfCount]);
		jtfCount++;
		
		dataDate.add(slash1);
		
		jtfs[jtfCount]=new JTextField(2);
		dataDate.add(jtfs[jtfCount]);
		jtfCount++;
		
		dataDate.add(slash2);
		
		jtfs[jtfCount]=new JTextField(4);
		dataDate.add(jtfs[jtfCount]);
		jtfCount++;
		
		dataDate.add(slash3);

		//br
		mainPanel.add(BorderLayout.WEST, dataDate);
		brs[brCount]= new JLabel("                                                                                                          ");
		mainPanel.add(brs[brCount]);
		brCount++;
	    
		//rate property
		JLabel ratePropertyLabel=new JLabel("        Rate property:    ");
		ratePropertyLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		ratePropertyLabel.setForeground(Color.BLACK);
		rateProperty.add(ratePropertyLabel);
				
		ArrayList<String> items3=new ArrayList<String>();
		items3.add("proportional");
		items3.add("actuarial");
		
		jcs[jcCount]= new JComboBox<String>();
		jcs[jcCount].setModel(new DefaultComboBoxModel(items3.toArray()));
		rateProperty.add(jcs[jcCount]);
		jcCount++;
				
		//br
		mainPanel.add(BorderLayout.WEST, rateProperty);
		brs[brCount]= new JLabel("                                                                                                                           ");
		mainPanel.add(brs[brCount]);
		brCount++;
		
		//currency
		JLabel currencyLabel=new JLabel("        Currency:    ");
		currencyLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		currencyLabel.setForeground(Color.BLACK);
		currency.add(currencyLabel);
				
		ArrayList<String> items4=new ArrayList<String>();
		items4.add("EUR");
		items4.add("USD");
		items4.add("GBP");
		items4.add("CHF");
		items4.add("JPY");
		jcs[jcCount]= new JComboBox<String>();
		jcs[jcCount].setModel(new DefaultComboBoxModel(items4.toArray()));
		currency.add(jcs[jcCount]);
		jcCount++;
				
		//br
		mainPanel.add(BorderLayout.WEST, currency);
		brs[brCount]= new JLabel("                                                                                                                              ");
		mainPanel.add(brs[brCount]);
		brCount++;
		
		//total column numbers
		JLabel columnNumberLabel = new JLabel("        Maximum number of columns in the input file:    ", JLabel.CENTER);  
		columnNumberLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		columnNumberLabel.setForeground(Color.BLACK);
		columnNumber.add(columnNumberLabel);
		
		jtfs[jtfCount] = new JTextField(4);
		columnNumber.add(jtfs[jtfCount]);
		jtfCount++;

		//br
		mainPanel.add(BorderLayout.WEST, columnNumber);
		brs[brCount]= new JLabel("                                                                                                              ");
		mainPanel.add(brs[brCount]);
		brCount++;
		
        //max years
  		JLabel maxYearsLabel = new JLabel("        Maximum maturity of an asset:    ", JLabel.CENTER);  
  		maxYearsLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
  		maxYearsLabel.setForeground(Color.BLACK);
  		maxYears.add(maxYearsLabel);
  		
  		jtfs[jtfCount] = new JTextField(4);
  		maxYears.add(jtfs[jtfCount]);
  		jtfCount++;
  		
  		JLabel yearLabel = new JLabel("years", JLabel.CENTER);  
  		yearLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
  		yearLabel.setForeground(Color.BLACK);
  		maxYears.add(yearLabel);

  		//br
  		mainPanel.add(BorderLayout.WEST, maxYears);
  		brs[brCount]= new JLabel("                                                                                                          ");
  		mainPanel.add(brs[brCount]);
  		brCount++;
  		
  		//number of days
  		JLabel nbOfDaysLabel = new JLabel("        Number of days in a year:    ", JLabel.CENTER);  
  		nbOfDaysLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
  		nbOfDaysLabel.setForeground(Color.BLACK);
  		nbOfDays.add(nbOfDaysLabel);
  		
  		jtfs[jtfCount] = new JTextField(4);
  		nbOfDays.add(jtfs[jtfCount]);
  		jtfCount++;

  		//br
  		mainPanel.add(BorderLayout.WEST, nbOfDays);
  		brs[brCount]= new JLabel("                                                                                                          ");
  		mainPanel.add(brs[brCount]);
  		brCount++;
  		
  		//date format
  		JLabel dateFormatLabel = new JLabel("        Date format (for dates in String):    ", JLabel.CENTER);  
  		dateFormatLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
  		dateFormatLabel.setForeground(Color.BLACK);
  		dateFormat.add(dateFormatLabel);
  		
  		jtfs[jtfCount] = new JTextField(10);
  		dateFormat.add(jtfs[jtfCount]);
  		jtfCount++;
  		
  		//br
  		mainPanel.add(BorderLayout.WEST, dateFormat);
  		brs[brCount]= new JLabel("                                                                                                          ");
  		mainPanel.add(brs[brCount]);
  		brCount++;
  		
  		//attention and explanation
  		JLabel attentionLabel1=new JLabel("        Attention: please look at the cells for start date, end date etc. "
  				+ "and write down the date format int the text field, ", JLabel.CENTER);
  		mainPanel.add(BorderLayout.WEST, attentionLabel1);
  		brs[brCount]= new JLabel("                   ");
  		mainPanel.add(brs[brCount]);
  		brCount++;
  		
  		JLabel attentionLabel2=new JLabel("        represent the day by dd (2 digits), the month by MM (2 digits) and the year by yyyy (4 digits), "
  				+ "example: dd/MM/yyyy", JLabel.CENTER);
  		mainPanel.add(BorderLayout.WEST, attentionLabel2);
  		brs[brCount]= new JLabel("                                                                                                          ");
  		mainPanel.add(brs[brCount]);
  		brCount++;
		
	    //buttons
		back.addActionListener(this);
		buttonPanel.add(back);
		confirm.addActionListener(this);
		buttonPanel.add(confirm);

		UPDATE();//important! To remember the parameters set before
		
		mainFrame.setVisible(true);      
	}
    

    
	boolean run=false;
	
		   
	public String getYear() {
		return jtfs[2].getText();
	}

	public String getMonth() {
		return jtfs[1].getText();
	}

	public String getDay() {
		return jtfs[0].getText();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {//define the actions 
			
		if(ae.getSource()==back){
			mainFrame.setVisible(false);
			ImportModels.entry();
		}
		else{			
			Processing.setRateProperty((String) jcs[0].getSelectedItem());//set rate property
			Processing.setCurrency((String) jcs[1].getSelectedItem());//set currency
		    Processing.setNbOfColumns(Integer.parseInt(jtfs[3].getText()));//set the total number of columns
		    Processing.setMaxYears(Integer.parseInt(jtfs[4].getText()));//set the maximum years in an asset
		    Processing.setNbOfDays(Integer.parseInt(jtfs[5].getText()));//set the number of days in a year in convention for all assets
		    DataModel.setDateFormat(new SimpleDateFormat(jtfs[6].getText()));//set date format
			
		    //check if the date is in range
			if(Integer.parseInt(this.getMonth())>=1 && Integer.parseInt(this.getMonth())<=12 && Integer.parseInt(this.getDay())>=1 && Integer.parseInt(this.getDay())<=31){
			    Processing.setDataDate(new GregorianCalendar(Integer.parseInt(this.getYear()), Integer.parseInt(this.getMonth())-1, Integer.parseInt(this.getDay())));
			    SAVE();
				mainFrame.setVisible(false);
			    GeneralFeatures2.entry();

			}
			else{//manage bad date
				warningLabel=new JLabel("    Bad date! Please verify the day, month and year", JLabel.CENTER);
			    warningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
			    warningLabel.setForeground(Color.RED);
			    mainPanel.add(warningLabel, BorderLayout.CENTER);
		        mainFrame.setVisible(true); 
			}
			

			
		}
			
	}//end action performed
	
	public void SAVE(){//Save the text of all text fields

        try {
            if(!saveBasicParam.exists()){
            	saveBasicParam.createNewFile();  //if the file !exist create a new one
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(saveBasicParam.getAbsolutePath()));
            for (int i=0;i<jtfCount;i++){
            	bw.write(jtfs[i].getText());
            	bw.newLine();
            }
            bw.close(); //close the BufferdWriter

        } catch (IOException e) { 
        	e.printStackTrace(); 
        	JOptionPane.showMessageDialog(null, e.toString(), "error in saving the data",
                    JOptionPane.ERROR_MESSAGE);
        }        

    }//End Of Save
	
	public void UPDATE(){ //UPDATE ON OPENING THE APPLICATION

        try {
            if(saveBasicParam.exists()){

                Scanner scan = new Scanner(saveBasicParam);   //Use Scanner to read the File
                for (int i=0;i<jtfCount;i++){
                	jtfs[i].setText(scan.nextLine());
                }
                scan.close();
            }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString(), "error in updating the data",
                    JOptionPane.ERROR_MESSAGE);
        }                

    }//End OF UPDATE
	
}
