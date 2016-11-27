package controlFlow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import references.ReferenceOps;

public class ImportIndex implements ActionListener{
	
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel attentionPanel=new JPanel();
	private JPanel delimiter=new JPanel();
	private JPanel separator=new JPanel();
	private JPanel valueType=new JPanel();
	private JPanel dateFormat=new JPanel();
	private JLabel warningLabel=new JLabel("", JLabel.CENTER);

	private JLabel brLabel1= new JLabel("                                                    "
			+ "                                                                             "
			+"                                                                              ");
	private JPanel buttonPanel  = new JPanel();
	   
	private ImageIcon imageIcon;
	private JLabel imageLabel;
	   
	private JButton back=new JButton("back");
	private JButton chooser = new JButton("choose an index folder");
	private JButton confirm = new JButton("OK");
	   
	private static String Path="";
	private JFileChooser fDialog;
	   
	private JLabel[] brs=new JLabel[10];
	private int brCount=0;
	   
	private JTextField[] jtfs=new JTextField[10];
	private int jtfCount=0;
	   
	private JComboBox jc;
	   
	private File saveIndexParam=new File("IndexParam.txt");

	public ImportIndex(){
	    prepareGUI();
	}

	public static void entry(){
	    ImportIndex  importIndex = new ImportIndex();  
	    importIndex.showJPanelDemo();
	}

	private void prepareGUI(){
	    mainFrame = new JFrame("IndexFeatures");
	    mainFrame.setSize(800,650);
	    mainFrame.setLocationRelativeTo(null);
	    mainFrame.setLayout(new BorderLayout(30, 30));

	    mainFrame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	        }        
	    });    
	    headerLabel = new JLabel("", JLabel.CENTER);        
	    statusLabel = new JLabel("",JLabel.CENTER);    

	    statusLabel.setSize(200,50);

	    controlPanel = new JPanel();
	    controlPanel.setLayout(new FlowLayout());

	    mainFrame.add(headerLabel, BorderLayout.NORTH);

	    mainFrame.add(buttonPanel, BorderLayout.SOUTH);
	    mainFrame.add(statusLabel);
	    mainFrame.setVisible(true);  
	}


	private void showJPanelDemo(){
		//title
		headerLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 36)); 
	    headerLabel.setText("<html>Please import index files <br> and set the index features </html>");      

	    imageIcon = new ImageIcon("money.jpg"); //image icon
	    imageLabel = new JLabel(imageIcon); 
	    imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
	    JPanel panel1 = new JPanel();
	    panel1.setBackground(Color.GREEN);
	    panel1.setLayout(new FlowLayout());  
	    panel1.add(BorderLayout.CENTER,imageLabel);

	    controlPanel.add(panel1, BorderLayout.CENTER); 
	    controlPanel.add(brLabel1);
	      
	    JLabel attentionLabel=new JLabel("Attention: this program supports only 3 file formats: xlsx, csv, txt", JLabel.CENTER);
	    attentionPanel.add(attentionLabel, BorderLayout.CENTER);
	    controlPanel.add(attentionPanel);
	      
	    //br
	    brs[brCount]=new JLabel("<html><br><br></html>");
	    controlPanel.add(brs[brCount]);
	    brCount++;
	      
	    //delimiter for csv or txt
	    JLabel delimiterLabel=new JLabel("If there are csv or txt files in the folder, please specify the delimiter: ", JLabel.CENTER);
	    delimiterLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
	    delimiterLabel.setForeground(Color.BLACK);
	    delimiter.add(delimiterLabel);
	      
	    jtfs[jtfCount] = new JTextField(4);
	    delimiter.add(jtfs[jtfCount]);
	    jtfCount++;
	      
	    //br
	    controlPanel.add(delimiter, BorderLayout.CENTER);
	    brs[brCount]= new JLabel("<html><br></html>");
		controlPanel.add(brs[brCount]);
		brCount++;
		  
		//file name separator
		JLabel separatorLabel=new JLabel("Please rename the file as: index name_currency", JLabel.CENTER);
	    separatorLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
	    separatorLabel.setForeground(Color.BLACK);
	    separator.add(separatorLabel);
	      
	    //br
	    controlPanel.add(separator, BorderLayout.CENTER);
	    brs[brCount]= new JLabel("                                                               ");
		controlPanel.add(brs[brCount]);
		brCount++;
		  
		//value type
		JLabel valueTypeLabel=new JLabel("The index rates in the files are in: ", JLabel.CENTER);
	    valueTypeLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
	    valueTypeLabel.setForeground(Color.BLACK);
	    valueType.add(valueTypeLabel);
	      
	    ArrayList<String> valueTypes=new ArrayList<String>();
		valueTypes.add("percentage");
		valueTypes.add("number");
		jc= new JComboBox<String>();
		jc.setModel(new DefaultComboBoxModel(valueTypes.toArray()));
		valueType.add(jc, BorderLayout.WEST);
		  
		//br
	    controlPanel.add(valueType, BorderLayout.CENTER);
	    brs[brCount]= new JLabel("                                                           ");
		controlPanel.add(brs[brCount]);
		brCount++;
		  
		//date format
		JLabel dateFormatLabel=new JLabel("Date format in the index files: ", JLabel.CENTER);
	    dateFormatLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
	    dateFormatLabel.setForeground(Color.BLACK);
	    dateFormat.add(dateFormatLabel);
	      
	    jtfs[jtfCount] = new JTextField(10);
	    dateFormat.add(jtfs[jtfCount]);
	    jtfCount++;
	      
	    //br
	    controlPanel.add(dateFormat, BorderLayout.CENTER);
	    brs[brCount]= new JLabel("                                                               ");
		controlPanel.add(brs[brCount]);
		brCount++;

	    //buttons
		back.addActionListener(this);
		buttonPanel.add(back, BorderLayout.SOUTH);
	    chooser.addActionListener(this);
	    buttonPanel.add(chooser, BorderLayout.SOUTH);
	    confirm.addActionListener(this);
	    buttonPanel.add(confirm, BorderLayout.SOUTH);
	      
	    mainFrame.add(controlPanel, BorderLayout.CENTER);
	    UPDATE();
	    mainFrame.setVisible(true);      
	}

	boolean correctType=true;
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==back){
			mainFrame.setVisible(false);
			Import.entry();
		}
		
	    else if(ae.getSource()==chooser){

		    fDialog = new JFileChooser();
		    fDialog.setDialogTitle("choose a folder");
		    fDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    int returnVal = fDialog.showOpenDialog(null);
		    
		    if(fDialog.getSelectedFile()!=null){
				Path=fDialog.getSelectedFile().toString();
			    
			    int count=0;
			    
			    if(fDialog.getSelectedFile().listFiles()==null){
			    	correctType=false;
		    		warningLabel.setText("        there is a file whose type is not a correct one!");
				    warningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
				    warningLabel.setForeground(Color.RED);
				    controlPanel.add(warningLabel, BorderLayout.CENTER);
			        mainFrame.setVisible(true); 
			    }
			    
			    else{
			    	
			    	for(File file : fDialog.getSelectedFile().listFiles()){
				    	if(!file.getName().contains("xls") && !file.getName().endsWith("csv") && !file.getName().endsWith("txt")){
				    		//if there is one file in the folder that is not in excel, csv or txt
				    		count++;
				    		correctType=false;
				    		warningLabel.setText("        there is a file whose type is not a correct one!");
						    warningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
						    warningLabel.setForeground(Color.RED);
						    controlPanel.add(warningLabel, BorderLayout.CENTER);
					        mainFrame.setVisible(true); 
					        break;
				    	}
				    }
			    	
				    System.out.println(Path);
				    
				    if(count==0){
				    	correctType=true;
				    	warningLabel.setText("");
					    controlPanel.add(warningLabel, BorderLayout.CENTER);
				        mainFrame.setVisible(true); 
				    }
			    }
		    }
		    
	    }
	    
	    else if(ae.getSource()==confirm && correctType){//when we have chosen a file
	    	
	    	if(Path.contentEquals("")){
	    		//alert popup
	    		int dialogResult = JOptionPane.showConfirmDialog (null, "Continue without importing indices?", "Warning",
	    				JOptionPane.YES_NO_OPTION);
	    		if(dialogResult == JOptionPane.YES_OPTION){
	    	    	
	    	    	SAVE();

	    		    mainFrame.setVisible(false);
	    		    ImportModels.entry();
	    		}
	    	}
	    	
	    	else{
	    		ReferenceOps.setIndexFolderPath(Path);
		    	ReferenceOps.setIndexFileFormatDelimiter(jtfs[0].getText());//for csv or txt files
		    	ReferenceOps.setIndexFileNameSeparator("_");//format: name_currency
		    	ReferenceOps.setIndexValueType((String)jc.getSelectedItem());//percentage or just numbers
		    	ReferenceOps.setIndexDateFormat(new SimpleDateFormat(jtfs[1].getText()));//date format in the date list
		    	
		    	try {
					ReferenceOps.initIndices();
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("fail to import the index folder!");
					JOptionPane.showMessageDialog(null, e1.toString(), "fail to import the index folder!",
		                    JOptionPane.ERROR_MESSAGE);//error massage popup
					System.exit(0);
				}
		    	
		    	
		    	SAVE();

			    mainFrame.setVisible(false);
			    ImportModels.entry();
	    	}
	    	

	    }
		
	}
	
	
	public void SAVE(){//Save the text of all text fields

        try {
            if(!saveIndexParam.exists()){
            	saveIndexParam.createNewFile();  //if the file !exist create a new one
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(saveIndexParam.getAbsolutePath()));
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
            if(saveIndexParam.exists()){

                Scanner scan = new Scanner(saveIndexParam);   //Use Scanner to read the File
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
