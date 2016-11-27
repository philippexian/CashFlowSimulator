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

import references.ReferenceOps;
import references.RiskModel;

public class RiskFeatures implements ActionListener{

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
	private JLabel[] percents=new JLabel[10];
	
	private int jcCount=0;
	private int jtfCount=0;
	private int jcbCount=0;
	private int percentCount=0;
	
    private File saveParam4=new File("RiskFeatures.txt");
	
	public RiskFeatures(){
		prepareGUI();
    }

    public static void entry(){
    	RiskFeatures example= new RiskFeatures();
		example.showJPanelDemo();
    }

    private void prepareGUI(){
	    mainFrame = new JFrame("RiskFeatures");
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
        labCnst.gridx++;
        mainPanel.add(DVLabel, labCnst);
        
        //DP rate
		JLabel DPRateLabel=new JLabel("Fixed DP rate");
		DPRateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		DPRateLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(DPRateLabel, labCnst);
        
        JPanel DPRateCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//0
        DPRateCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//0
        DPRateCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(DPRateCN, labCnst);
		
		ArrayList<String> DPRateT=new ArrayList<String>();
		DPRateT.add("rate");
		DPRateT.add("double");
		jcs[jcCount]= new JComboBox<String>();//0
		jcs[jcCount].setModel(new DefaultComboBoxModel(DPRateT.toArray()));
		jcs[jcCount].addActionListener(this);
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel DPRateDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(4);//1
		DPRateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		percents[percentCount]=new JLabel("");
		percents[percentCount].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		if(jcs[jcCount-1].getSelectedItem().equals("rate")){//set initial value
    		percents[percentCount].setText("%");
    	}
		DPRateDV.add(percents[percentCount]);
		percentCount++;

		labCnst.gridx++;
		mainPanel.add(DPRateDV, labCnst);
        
        //DP rate type
		JLabel DPRateTypeLabel=new JLabel("DP rate type");
		DPRateTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		DPRateTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(DPRateTypeLabel, labCnst);
        
        JPanel DPRateTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//1
        DPRateTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//2
        DPRateTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(DPRateTypeCN, labCnst);
		
		JLabel DPRateTypeT=new JLabel("string");
		DPRateTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		DPRateTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(DPRateTypeT, labCnst);
		
		ArrayList<String> DPRateTypeDV=new ArrayList<String>();
		DPRateTypeDV.add("fixed");
		DPRateTypeDV.add("floating");
		jcs[jcCount]= new JComboBox<String>();//1
		jcs[jcCount].setModel(new DefaultComboBoxModel(DPRateTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//DP model name
		JLabel DPModelLabel=new JLabel("DP model name");
		DPModelLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		DPModelLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(DPModelLabel, labCnst);
        
        JPanel DPModelCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//2
        DPModelCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//3
        DPModelCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(DPModelCN, labCnst);
		
		JLabel DPModelT=new JLabel("string");
		DPModelT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		DPModelT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(DPModelT, labCnst);
		
		ArrayList<String> DPModelDV=new ArrayList<String>();
		DPModelDV.add(" ");
		
		if(ReferenceOps.getModels()!=null){
			for(RiskModel model: ReferenceOps.getModels()){
				DPModelDV.add(model.getModelName());
			}
		}

		jcs[jcCount]= new JComboBox<String>();//2
		jcs[jcCount].setModel(new DefaultComboBoxModel(DPModelDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
        //LGD rate
		JLabel LGDRateLabel=new JLabel("Fixed LGD rate");
		LGDRateLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		LGDRateLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(LGDRateLabel, labCnst);
        
        JPanel LGDRateCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//3
        LGDRateCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//4
        LGDRateCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(LGDRateCN, labCnst);
		
		ArrayList<String> LGDRateT=new ArrayList<String>();
		LGDRateT.add("rate");
		LGDRateT.add("double");
		jcs[jcCount]= new JComboBox<String>();//3
		jcs[jcCount].setModel(new DefaultComboBoxModel(LGDRateT.toArray()));
		jcs[jcCount].addActionListener(this);
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		JPanel LGDRateDV=new JPanel(new FlowLayout());
		
		jtfs[jtfCount]=new JTextField(4);//5
		LGDRateDV.add(jtfs[jtfCount]);
		jtfCount++;
		
		percents[percentCount]=new JLabel("");
		percents[percentCount].setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		if(jcs[jcCount-1].getSelectedItem().equals("rate")){//set initial value
    		percents[percentCount].setText("%");
    	}
		LGDRateDV.add(percents[percentCount]);
		percentCount++;

		labCnst.gridx++;
		mainPanel.add(LGDRateDV, labCnst);
        
        //LGD rate type
		JLabel LGDRateTypeLabel=new JLabel("LGD rate type");
		LGDRateTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		LGDRateTypeLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(LGDRateTypeLabel, labCnst);
        
        JPanel LGDRateTypeCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//4
        LGDRateTypeCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//6
        LGDRateTypeCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(LGDRateTypeCN, labCnst);
		
		JLabel LGDRateTypeT=new JLabel("string");
		LGDRateTypeT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		LGDRateTypeT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(LGDRateTypeT, labCnst);
		
		ArrayList<String> LGDRateTypeDV=new ArrayList<String>();
		LGDRateTypeDV.add("fixed");
		LGDRateTypeDV.add("floating");
		jcs[jcCount]= new JComboBox<String>();//4
		jcs[jcCount].setModel(new DefaultComboBoxModel(LGDRateTypeDV.toArray()));
		labCnst.gridx++;
		mainPanel.add(jcs[jcCount], labCnst);
		jcCount++;
		
		//LGD model name
		JLabel LGDModelLabel=new JLabel("LGD model name");
		LGDModelLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		LGDModelLabel.setPreferredSize(nameSize);
		labCnst.gridx = 0;
        labCnst.gridy++;
        mainPanel.add(LGDModelLabel, labCnst);
        
        JPanel LGDModelCN=new JPanel(new FlowLayout());
        
        jcbs[jcbCount]=new JCheckBox("");//5
        LGDModelCN.add(jcbs[jcbCount]);
        jcbCount++;
        
        jtfs[jtfCount]=new JTextField(4);//7
        LGDModelCN.add(jtfs[jtfCount]);
		jtfCount++;
		
		labCnst.gridx++;
		mainPanel.add(LGDModelCN, labCnst);
		
		JLabel LGDModelT=new JLabel("string");
		LGDModelT.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		LGDModelT.setPreferredSize(typeSize);
		labCnst.gridx++;
		mainPanel.add(LGDModelT, labCnst);
		
		ArrayList<String> LGDModelDV=new ArrayList<String>();
		LGDModelDV.add(" ");
		
		if(ReferenceOps.getModels()!=null){
			for(RiskModel model: ReferenceOps.getModels()){
				LGDModelDV.add(model.getModelName());
			}
		}

		jcs[jcCount]= new JComboBox<String>();//5
		jcs[jcCount].setModel(new DefaultComboBoxModel(LGDModelDV.toArray()));
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
			RateFeatures.entry();
		}
		else if(ae.getSource()==next){
			int countJtf=0;
			int countJc=0;
			int countJcb=0;
			
			DataModel.setDPRate(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					(String)jcs[countJc++].getSelectedItem(), Double.parseDouble(jtfs[countJtf++].getText())));//set default probability
			
			DataModel.setDPRateType(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", rateTypeConverter((String)jcs[countJc++].getSelectedItem())));//set DP rate type
			
			DataModel.setDPModelName(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", (String)jcs[countJc++].getSelectedItem()));//set DP model name
			
			DataModel.setLGDRate(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					(String)jcs[countJc++].getSelectedItem(), Double.parseDouble(jtfs[countJtf++].getText())));//set LGD
			
			DataModel.setLGDType(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", rateTypeConverter((String)jcs[countJc++].getSelectedItem())));//set LGD rate type
			
			DataModel.setLGDModelName(new Feature(columnNumberConverter(jtfs[countJtf++].getText(), countJcb++), 
					"String", (String)jcs[countJc++].getSelectedItem()));//set LGD model name
			
			SAVE();
			
			mainFrame.setVisible(false);
			RiskFeatures2.entry();
			
		}
		
		//show % or not
		else if(ae.getSource()==jcs[0]){//DP rate
			if(jcs[0].getSelectedItem()=="rate"){
	    		percents[0].setText("%");
	    	}
			else{
				percents[0].setText("");
			}
		}
		
		else if(ae.getSource()==jcs[3]){//LGD rate
			if(jcs[3].getSelectedItem()=="rate"){
	    		percents[1].setText("%");
	    	}
			else{
				percents[1].setText("");
			}
		}
		
		
		
	}
	
	public void SAVE(){//Save the text of all text fields

        try {
            if(!saveParam4.exists()){
            	saveParam4.createNewFile();  //if the file !exist create a new one
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(saveParam4.getAbsolutePath()));
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
            if(saveParam4.exists()){

                Scanner scan = new Scanner(saveParam4);   //Use Scanner to read the File

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
