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
import references.Index;
import references.ReferenceOps;

public class RateFeatures implements ActionListener{

	public JFrame mainFrame;
	private JLabel headerLabel;
	
	private JPanel controlPanel;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	
	private JButton back = new JButton("back");
	private JButton confirm = new JButton("next");
	
	private JComboBox[] jcs=new JComboBox[20];
	private JTextField[] jtfs=new JTextField[20];
	private JLabel[] slashes=new JLabel[10];
	private JCheckBox[] jcbs=new JCheckBox[15];
	private JLabel[] percents=new JLabel[10];
	
	private int jcCount=0;
	private int jtfCount=0;
	private int slashCount=0;
	private int jcbCount=0;
	private int percentCount=0;
	
    private File saveParam2=new File("RateFeatures.txt");
	
	
	public RateFeatures(){
		prepareGUI();
    }

    public static void entry(){
    	RateFeatures example= new RateFeatures();
		example.showJPanelDemo();
    }

    private void prepareGUI(){
	    mainFrame = new JFrame("RateFeatures");
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
		headerLabel.setText("    Please set rate features        "); 
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
        
        //annual rate
		JLabel annualRateLabel=new JLabel("Fixed annual rate");
		annualRateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		annualRateLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(annualRateLabel, labCnst);
        
        JPanel annualRateCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//0
        annualRateCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//0
        annualRateCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(annualRateCN, labCnst);
		
		ArrayList<String> annualRateT=new ArrayList<String>();
		annualRateT.add("rate");
		annualRateT.add("double");
		jcs[jcCount]= new JComboBox<String>();//0
		jcs[jcCount].setModel(new DefaultComboBoxModel(annualRateT.toArray()));
		jcs[jcCount].addActionListener(this);
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel annualRateDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(4);//1
		annualRateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		percents[percentCount]=new JLabel("");
		percents[percentCount].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		if(jcs[jcCount-1].getSelectedItem().equals("rate")){//set initial value
    		percents[percentCount].setText("%");
    	}
		annualRateDV.add(percents[percentCount]);
		percentCount++;

		labCnst.gridx++;
		mainPanel.add(annualRateDV, labCnst);
        
        //rate type
		JLabel rateTypeLabel=new JLabel("Rate type");
		rateTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		rateTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(rateTypeLabel, labCnst);
        
        JPanel rateTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//1
        rateTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//2
        rateTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(rateTypeCN, labCnst);
		
		JLabel rateTypeT=new JLabel("string");
		rateTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		rateTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(rateTypeT, labCnst);
		
		ArrayList<String> rateTypeDV=new ArrayList<String>();
		rateTypeDV.add("fixed");
		rateTypeDV.add("floating");
		rateTypeDV.add("fixed to floating");
		jcs[jcCount]= new JComboBox<String>();//1
		jcs[jcCount].setModel(new DefaultComboBoxModel(rateTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//capFloorType
		JLabel capFloorTypeLabel=new JLabel("Cap floor type");
		capFloorTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		capFloorTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(capFloorTypeLabel, labCnst);
        
        JPanel capFloorTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//2
        capFloorTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//3
        capFloorTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(capFloorTypeCN, labCnst);
		
		JLabel capFloorTypeT=new JLabel("string");
		capFloorTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		capFloorTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(capFloorTypeT, labCnst);
		
		ArrayList<String> capFloorTypeDV=new ArrayList<String>();
		capFloorTypeDV.add("none");
		capFloorTypeDV.add("cap");
		capFloorTypeDV.add("floor");
		capFloorTypeDV.add("collar");
		jcs[jcCount]= new JComboBox<String>();//2
		jcs[jcCount].setModel(new DefaultComboBoxModel(capFloorTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
        //cap level
		JLabel capLevelLabel=new JLabel("Cap level");
		capLevelLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		capLevelLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(capLevelLabel, labCnst);
        
        JPanel capLevelCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//3
        capLevelCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//4
        capLevelCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(capLevelCN, labCnst);
		
		ArrayList<String> capLevelT=new ArrayList<String>();
		capLevelT.add("rate");
		capLevelT.add("double");
		jcs[jcCount]= new JComboBox<String>();//3
		jcs[jcCount].setModel(new DefaultComboBoxModel(capLevelT.toArray()));
		jcs[jcCount].addActionListener(this);
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel capLevelDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(4);//5
		capLevelDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		percents[percentCount]=new JLabel("");
		percents[percentCount].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		if(jcs[jcCount-1].getSelectedItem().equals("rate")){//set initial value
    		percents[percentCount].setText("%");
    	}
		capLevelDV.add(percents[percentCount]);
		percentCount++;

		labCnst.gridx++;
		mainPanel.add(capLevelDV, labCnst);
		
        //floor level
		JLabel floorLevelLabel=new JLabel("Floor level");
		floorLevelLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		floorLevelLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(floorLevelLabel, labCnst);
        
        JPanel floorLevelCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//4
        floorLevelCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//6
        floorLevelCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(floorLevelCN, labCnst);
		
		ArrayList<String> floorLevelT=new ArrayList<String>();
		floorLevelT.add("rate");
		floorLevelT.add("double");
		jcs[jcCount]= new JComboBox<String>();//4
		jcs[jcCount].setModel(new DefaultComboBoxModel(floorLevelT.toArray()));
		jcs[jcCount].addActionListener(this);
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel floorLevelDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(4);//7
		floorLevelDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		percents[percentCount]=new JLabel("");
		percents[percentCount].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		if(jcs[jcCount-1].getSelectedItem().equals("rate")){//set initial value
    		percents[percentCount].setText("%");
    	}
		floorLevelDV.add(percents[percentCount]);
		percentCount++;

		labCnst.gridx++;
		mainPanel.add(floorLevelDV, labCnst);

        //margin
		JLabel marginLabel=new JLabel("Margin");
		marginLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		marginLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(marginLabel, labCnst);
        
        JPanel marginCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//5
        marginCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//8
        marginCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(marginCN, labCnst);
		
		ArrayList<String> marginT=new ArrayList<String>();
		marginT.add("rate");
		marginT.add("double");
		jcs[jcCount]= new JComboBox<String>();//5
		jcs[jcCount].setModel(new DefaultComboBoxModel(marginT.toArray()));
		jcs[jcCount].addActionListener(this);
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel marginDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(4);//8
		marginDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		percents[percentCount]=new JLabel("");
		percents[percentCount].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		if(jcs[jcCount-1].getSelectedItem().equals("rate")){//set initial value
    		percents[percentCount].setText("%");
    	}
		marginDV.add(percents[percentCount]);
		percentCount++;

		labCnst.gridx++;
		mainPanel.add(marginDV, labCnst);
		
		//index name
		JLabel indexNameLabel=new JLabel("Index name");
		indexNameLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		indexNameLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(indexNameLabel, labCnst);
        
        JPanel indexNameCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//6
        indexNameCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//9
        indexNameCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(indexNameCN, labCnst);
		
		JLabel indexNameT=new JLabel("string");
		indexNameT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		indexNameT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(indexNameT, labCnst);
		
		ArrayList<String> indexNameDV=new ArrayList<String>();
		indexNameDV.add(" ");
		if(ReferenceOps.getIndices()!=null){
			for(Index index: ReferenceOps.getIndices()){
				indexNameDV.add(index.getName());
			}
		}

		jcs[jcCount]= new JComboBox<String>();//6
		jcs[jcCount].setModel(new DefaultComboBoxModel(indexNameDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//fixed to floating date
		JLabel fixedToFloatingDateLabel=new JLabel("Fixed to floating date");
		fixedToFloatingDateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(fixedToFloatingDateLabel, labCnst);
		
        JPanel fixedToFloatingDateCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//7
        fixedToFloatingDateCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//10
        fixedToFloatingDateCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(fixedToFloatingDateCN, labCnst);
		
		ArrayList<String> fixedToFloatingDateT=new ArrayList<String>();
		if(Processing.getFileType().contains("xls")){
			fixedToFloatingDateT.add("excel date");
		}
		else{
			fixedToFloatingDateT.add("date string");
		}
		jcs[jcCount]= new JComboBox<String>();//7
		jcs[jcCount].setModel(new DefaultComboBoxModel(fixedToFloatingDateT.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel fixedToFloatingDateDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(2);//11
		fixedToFloatingDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3]=new JLabel("DD/", JLabel.CENTER);  
		slashes[slashCount*3].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		fixedToFloatingDateDV.add(slashes[slashCount*3]);
		
		jtfs[jtfCount]=new JTextField(2);//12
		fixedToFloatingDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3+1]=new JLabel("MM/", JLabel.CENTER);  
		slashes[slashCount*3+1].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		fixedToFloatingDateDV.add(slashes[slashCount*3+1]);
		
		jtfs[jtfCount]=new JTextField(4);//13
		fixedToFloatingDateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		slashes[slashCount*3+2]=new JLabel("YYYY", JLabel.CENTER);  
		slashes[slashCount*3+2].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		fixedToFloatingDateDV.add(slashes[slashCount*3+2]);
		
		slashCount++;
		
		labCnst.gridx++;
		mainPanel.add(fixedToFloatingDateDV, labCnst);
		
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
			AssetFeatures.entry();
		}
		else if(ae.getSource()==confirm){
			int countJc=0;
			int countJtf=0;
			int countJcb=0;
			
			//set features in the data model
			
			DataModel.setAnnualRate(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					(String)jcs[countJc++].getSelectedItem(), Double.parseDouble(jtfs[countJtf++].getText())));//set annual interest rate
			
			DataModel.setRateType(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", rateTypeConverter((String)jcs[countJc++].getSelectedItem())));//set interest rate type
			
			DataModel.setCapFloorType(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", capFloorTypeConverter((String)jcs[countJc++].getSelectedItem())));//set cap floor type
			
			DataModel.setCapLevel(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					(String)jcs[countJc++].getSelectedItem(), Double.parseDouble(jtfs[countJtf++].getText())));//set cap level
			
			DataModel.setFloorLevel(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					(String)jcs[countJc++].getSelectedItem(), Double.parseDouble(jtfs[countJtf++].getText())));//set floor level
			
			DataModel.setMargin(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					(String)jcs[countJc++].getSelectedItem(), Double.parseDouble(jtfs[countJtf++].getText())));//set margin
			
			DataModel.setIndexName(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", (String)jcs[countJc++].getSelectedItem()));//set index name
			
			DataModel.setFixedToFloatingDate(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), (String)jcs[countJc++].getSelectedItem(), 
			new GregorianCalendar(Integer.parseInt(jtfs[14].getText()), Integer.parseInt(jtfs[13].getText())-1, Integer.parseInt(jtfs[12].getText()))));
			//set fixed to floating date
			
			SAVE();
			mainFrame.setVisible(false);
			RiskFeatures.entry();

		}
		
		//show % or not
		else if(ae.getSource()==jcs[0]){//annual rate
			if(jcs[0].getSelectedItem()=="rate"){
	    		percents[0].setText("%");
	    	}
			else{
				percents[0].setText("");
			}
		}
		
		else if(ae.getSource()==jcs[3]){//cap level
			if(jcs[3].getSelectedItem()=="rate"){
	    		percents[1].setText("%");
	    	}
			else{
				percents[1].setText("");
			}
		}
		
		else if(ae.getSource()==jcs[4]){//floor level
			if(jcs[4].getSelectedItem()=="rate"){
	    		percents[2].setText("%");
	    	}
			else{
				percents[2].setText("");
			}
		}
		
		else if(ae.getSource()==jcs[5]){//margin
			if(jcs[5].getSelectedItem()=="rate"){
	    		percents[3].setText("%");
	    	}
			else{
				percents[3].setText("");
			}
		}
		
	}
	
	public void SAVE(){//Save the text of all text fields

        try {
            if(!saveParam2.exists()){
            	saveParam2.createNewFile();  //if the file !exist create a new one
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(saveParam2.getAbsolutePath()));
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
        	JOptionPane.showMessageDialog(null, e.toString(), "error in saving the data",
                    JOptionPane.ERROR_MESSAGE);
        }        

    }//End Of Save
	
	public void UPDATE(){ //UPDATE ON OPENING THE APPLICATION

        try {
            if(saveParam2.exists()){

                Scanner scan = new Scanner(saveParam2);   //Use Scanner to read the File

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
	
	//column & type converters
	private char rateTypeConverter(String selectedItem){
		char returnValue;
		switch(selectedItem){
		case("fixed"):
			returnValue='f';
		    break;
		case("floating"):
			returnValue='v';
		    break;
		case("fixed to floating"):
			returnValue='F';
		    break;
		case("custom"):
			returnValue='c';
		    break;
		default:
			returnValue='f';
			break;
		}
		return returnValue;
	} 
	
	private char capFloorTypeConverter(String selectedItem){
		char returnValue;
		switch(selectedItem){
		case("none"):
			returnValue='n';
		    break;
		case("cap"):
			returnValue='c';
		    break;
		case("floor"):
			returnValue='f';
		    break;
		case("collar"):
			returnValue='C';
		    break;
		default:
			returnValue='n';
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
