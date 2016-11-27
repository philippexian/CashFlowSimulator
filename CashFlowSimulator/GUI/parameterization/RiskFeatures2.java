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

import controlFlow.Running;
import references.ReferenceOps;
import references.RiskModel;

public class RiskFeatures2 implements ActionListener{

	public JFrame mainFrame;
	private JLabel headerLabel;
	
	private JPanel controlPanel;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	
	private JButton back = new JButton("back");
	private JButton next = new JButton("next");
	
	private JComboBox[] jcs=new JComboBox[20];
	private JTextField[] jtfs=new JTextField[20];
	private JCheckBox[] jcbs=new JCheckBox[15];
	private JLabel percent;
	
	private int jcCount=0;
	private int jtfCount=0;
	private int jcbCount=0;
	
    private File saveParam5=new File("RiskFeatures2.txt");
	
	public RiskFeatures2(){
		prepareGUI();
    }

    public static void entry(){
    	RiskFeatures2 example= new RiskFeatures2();
		example.showJPanelDemo();
    }

    private void prepareGUI(){
	    mainFrame = new JFrame("RiskFeatures2");
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
		headerLabel.setText("    Please set risk features        "); 
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
		//DVLabel.setPreferredSize(DVSize);
        labCnst.gridx++;
        mainPanel.add(DVLabel, labCnst);
        
        //recovery type
        JLabel recoveryTypeLabel=new JLabel("Recovery type");
		recoveryTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		recoveryTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(recoveryTypeLabel, labCnst);
        
        JPanel recoveryTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//0
        recoveryTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//0
        recoveryTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(recoveryTypeCN, labCnst);
		
		JLabel recoveryTypeT=new JLabel("string");
		recoveryTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		recoveryTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(recoveryTypeT, labCnst);
		
		ArrayList<String> recoveryTypeDV=new ArrayList<String>();
		recoveryTypeDV.add("infine");
		recoveryTypeDV.add("linear");
		jcs[jcCount]= new JComboBox<String>();//0
		jcs[jcCount].setModel(new DefaultComboBoxModel(recoveryTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
        
		//start recovery
        JLabel startRecoveryLabel=new JLabel("Recovery start month");
		startRecoveryLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		startRecoveryLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(startRecoveryLabel, labCnst);
        
        JPanel startRecoveryCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//1
        startRecoveryCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//1
        startRecoveryCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(startRecoveryCN, labCnst);
		
		JLabel startRecoveryT=new JLabel("int");
		startRecoveryT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		startRecoveryT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(startRecoveryT, labCnst);
		
		JPanel startRecoveryDV=new JPanel(new FlowLayout());
		jtfs[jtfCount]=new JTextField(4);//2
		startRecoveryDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		JLabel month1=new JLabel(" months after the default ");
		month1.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		startRecoveryDV.add(month1);
		labCnst.gridx++;
		mainPanel.add(startRecoveryDV, labCnst);
		
		//end recovery
        JLabel endRecoveryLabel=new JLabel("Recovery end month");
		endRecoveryLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		endRecoveryLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(endRecoveryLabel, labCnst);
        
        JPanel endRecoveryCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//2
        endRecoveryCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//3
        endRecoveryCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(endRecoveryCN, labCnst);
		
		JLabel endRecoveryT=new JLabel("int");
		endRecoveryT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		endRecoveryT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(endRecoveryT, labCnst);
		
		JPanel endRecoveryDV=new JPanel(new FlowLayout());
		jtfs[jtfCount]=new JTextField(4);//4
		endRecoveryDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		JLabel month2=new JLabel(" months after the default ");
		month2.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		endRecoveryDV.add(month2);
		labCnst.gridx++;
		mainPanel.add(endRecoveryDV, labCnst);
		
        //prepayment rate
		JLabel prepaymentRateLabel=new JLabel("Fixed prepayment rate");
		prepaymentRateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		prepaymentRateLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(prepaymentRateLabel, labCnst);
        
        JPanel prepaymentRateCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//3
        prepaymentRateCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//5
        prepaymentRateCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(prepaymentRateCN, labCnst);
		
		ArrayList<String> prepaymentRateT=new ArrayList<String>();
		prepaymentRateT.add("rate");
		prepaymentRateT.add("double");
		jcs[jcCount]= new JComboBox<String>();//1
		jcs[jcCount].setModel(new DefaultComboBoxModel(prepaymentRateT.toArray()));
		jcs[jcCount].addActionListener(this);
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel prepaymentRateDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(4);//6
		prepaymentRateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		percent=new JLabel("");
		percent.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		if(jcs[jcCount-1].getSelectedItem().equals("rate")){//set initial value
    		percent.setText("%");
    	}
		prepaymentRateDV.add(percent);

		labCnst.gridx++;
		mainPanel.add(prepaymentRateDV, labCnst);
        
        //prepayment rate type
		JLabel prepaymentRateTypeLabel=new JLabel("Prepayment rate type");
		prepaymentRateTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		prepaymentRateTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(prepaymentRateTypeLabel, labCnst);
        
        JPanel prepaymentRateTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//4
        prepaymentRateTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//7
        prepaymentRateTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(prepaymentRateTypeCN, labCnst);
		
		JLabel prepaymentRateTypeT=new JLabel("string");
		prepaymentRateTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		prepaymentRateTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(prepaymentRateTypeT, labCnst);
		
		ArrayList<String> prepaymentRateTypeDV=new ArrayList<String>();
		prepaymentRateTypeDV.add("fixed");
		prepaymentRateTypeDV.add("floating");
		jcs[jcCount]= new JComboBox<String>();//2
		jcs[jcCount].setModel(new DefaultComboBoxModel(prepaymentRateTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//prepayment model name
		JLabel prepaymentModelLabel=new JLabel("Prepayment model name");
		prepaymentModelLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		prepaymentModelLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(prepaymentModelLabel, labCnst);
        
        JPanel prepaymentModelCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//5
        prepaymentModelCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//8
        prepaymentModelCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(prepaymentModelCN, labCnst);
		
		JLabel prepaymentModelT=new JLabel("string");
		prepaymentModelT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		prepaymentModelT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(prepaymentModelT, labCnst);
		
		ArrayList<String> prepaymentModelDV=new ArrayList<String>();
		prepaymentModelDV.add(" ");
		if(ReferenceOps.getModels()!=null){
			for(RiskModel model: ReferenceOps.getModels()){
				prepaymentModelDV.add(model.getModelName());
			}
		}
		jcs[jcCount]= new JComboBox<String>();//3
		jcs[jcCount].setModel(new DefaultComboBoxModel(prepaymentModelDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		
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
		next.addActionListener(this);
		buttonPanel.add(next);
		
		UPDATE();
		mainFrame.setVisible(true); 
    }
	
    boolean portfolioSet=false;
    
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==back){
			mainFrame.setVisible(false);
			RiskFeatures.entry();
		}
		
		else if(ae.getSource()==next){
			int countJtf=0;
			int countJc=0;
			int countJcb=0;
			
			DataModel.setRecoveryType(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", recoveryTypeConverter((String)jcs[countJc++].getSelectedItem())));//set recovery type
			
			DataModel.setStartRecovery(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"int", Integer.parseInt(jtfs[countJtf++].getText())));//set the recovery starting month
			
			DataModel.setEndRecovery(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"int", Integer.parseInt(jtfs[countJtf++].getText())));//set the recovery end month
			
			DataModel.setPrepaymentRate(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					(String)jcs[countJc++].getSelectedItem(), Double.parseDouble(jtfs[countJtf++].getText())));//set prepayment rate
			
			DataModel.setPrepaymentRateType(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", rateTypeConverter((String)jcs[countJc++].getSelectedItem())));//set prepayment rate type
			
			DataModel.setPrepaymentModelName(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", (String)jcs[countJc++].getSelectedItem()));//set prepayment model name
			
			SAVE();
			
			mainFrame.setVisible(false);
			Running.entry();
		}
		
		//show % or not
		else if(ae.getSource()==jcs[1]){//prepayment rate
			if(jcs[1].getSelectedItem()=="rate"){
	    		percent.setText("%");
	    	}
			else{
				percent.setText("");
			}
		}
		
	}
	
	public void SAVE(){//Save the text of all text fields

        try {
            if(!saveParam5.exists()){
            	saveParam5.createNewFile();  //if the file !exist create a new one
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(saveParam5.getAbsolutePath()));
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

        } catch (IOException e) { e.printStackTrace(); }        

    }//End Of Save
	
	public void UPDATE(){ //UPDATE ON OPENING THE APPLICATION

        try {
            if(saveParam5.exists()){

                Scanner scan = new Scanner(saveParam5);   //Use Scanner to read the File

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
	
	//type & column number converters
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
	
	private char recoveryTypeConverter(String selectedItem){
		char returnValue;
		switch(selectedItem){
		case("infine"):
			returnValue='I';
		    break;
		case("linear"):
			returnValue='L';
		    break;
		default:
			returnValue='I';
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
