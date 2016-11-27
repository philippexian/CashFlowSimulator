package parameterization;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import processing.Processing;

public class GeneralFeatures2 implements ActionListener{

	public JFrame mainFrame;
	private JLabel headerLabel;
	
	private JPanel mainPanel = new JPanel();
	private JPanel buttonPanel  = new JPanel();
	
	private JPanel timeLine=new JPanel();
	private JPanel format=new JPanel();
	private JPanel decimal=new JPanel();
	private JPanel errorMargin=new JPanel();

	private JLabel[] brs=new JLabel[10];
	private JTextField[] jtfs=new JTextField[10];
	private JComboBox[] jcs=new JComboBox[10];
	
	private int jtfCount=0;
	private int brCount=0;
	private int jcCount=0;
	
	private JPanel controlPanel;

	private ImageIcon imageIcon;
	private JLabel imageLabel;
	   
	private JButton back = new JButton("back");
	private JButton confirm = new JButton("next");

    private File saveBasicParam=new File("GeneralFeatures2.txt");
    
	public GeneralFeatures2(){
		prepareGUI();
    }
    
    public static void entry(){
		GeneralFeatures2 example= new GeneralFeatures2();
		example.showJPanelDemo();
    }

    private void prepareGUI(){
	    mainFrame = new JFrame("GeneralFeatures2");
		mainFrame.setSize(800,650);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BorderLayout(50, 50));

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
		headerLabel.setText("    Some general features (2)        ");      

		imageIcon = new ImageIcon("hehe.gif"); //write the path of the doc
	    imageLabel = new JLabel(imageIcon);         //initialize JLabel
	    imageLabel.setBounds(0, 0, 2*imageIcon.getIconWidth(), 2*imageIcon.getIconHeight());
		JPanel imagePanel = new JPanel();
		
		imagePanel.setBackground(Color.WHITE);
	    imagePanel.setLayout(new FlowLayout());  
	    imagePanel.add(imageLabel);

	    controlPanel.add(BorderLayout.WEST, headerLabel);
		controlPanel.add(BorderLayout.EAST, imagePanel); 
		
		//output settings
		JLabel outputSettingLabel=new JLabel("Output settings: ");
		outputSettingLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		outputSettingLabel.setForeground(Color.BLACK);
	    mainPanel.add(BorderLayout.WEST, outputSettingLabel);
		brs[brCount]= new JLabel("                                                                                                          "
				+"                                                                                                                          ");
		mainPanel.add(brs[brCount]);
		brCount++;
		
		//time line
		JLabel timeLineLabel = new JLabel("        Timeline of portfolio:    ",JLabel.CENTER); //not sure
		timeLineLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		timeLineLabel.setForeground(Color.BLACK);
		timeLine.add(timeLineLabel);
		
	    jcs[jcCount] = new JComboBox<String>();
	    ArrayList<String> items1 = new ArrayList<String>();

	    items1.add("monthly");
	    items1.add("daily");
	    items1.add("weekly");
	    items1.add("quarterly");
	    items1.add("biannual");
	    items1.add("annual");
	    jcs[jcCount].setModel(new DefaultComboBoxModel(items1.toArray()));
	    jcs[jcCount].setSelectedItem("monthly");
	    timeLine.add(jcs[jcCount]);
	    jcCount++;
	    
	    //br
	    mainPanel.add(BorderLayout.WEST, timeLine);
		brs[brCount]= new JLabel("                                                                                                          ");
		mainPanel.add(brs[brCount]);
		brCount++;
	    
		//output format
	    JLabel formatLabel = new JLabel("        Output format:    ",JLabel.CENTER);  //
		formatLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		formatLabel.setForeground(Color.BLACK);
		format.add(formatLabel);
		
		jcs[jcCount] = new JComboBox<String>();
	    ArrayList<String> items2 = new ArrayList<String>();

	    items2.add("csv");
	    items2.add("txt");
	    items2.add("xlsx");
	    items2.add("xls");

	    jcs[jcCount].setModel(new DefaultComboBoxModel(items2.toArray()));
	    jcs[jcCount].setSelectedItem("xlsx");
	    format.add(jcs[jcCount]);
	    jcCount++;
	    
	    //br
	    mainPanel.add(BorderLayout.WEST, format);
		brs[brCount]= new JLabel("                                                                                                          ");
		mainPanel.add(brs[brCount]);
		brCount++;
		
	    //decimal
	    JLabel decimalLabel = new JLabel("        Number of decimals in output file:    ", JLabel.CENTER);  
		decimalLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		decimalLabel.setForeground(Color.BLACK);
		decimal.add(decimalLabel);
		
		jtfs[jtfCount] = new JTextField(4);
		decimal.add(jtfs[jtfCount]);
		jtfCount++;

		//br
		mainPanel.add(BorderLayout.WEST, decimal);
		brs[brCount]= new JLabel("                                                                                                              ");
		mainPanel.add(brs[brCount]);
		brCount++;
		
  		//error margin
  		JLabel errorMarginLabel = new JLabel("        Materiality threshold in calculations:    ", JLabel.CENTER);  
  		errorMarginLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
  		errorMarginLabel.setForeground(Color.BLACK);
  		errorMargin.add(errorMarginLabel);
  		
  		jtfs[jtfCount] = new JTextField(4);
  		errorMargin.add(jtfs[jtfCount]);
  		jtfCount++;

  		//br
  		mainPanel.add(BorderLayout.WEST, errorMargin);
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

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==back){
			mainFrame.setVisible(false);
			GeneralFeatures.entry();
		}
		else{
			Processing.setTimeLine(timeLineConverter((String)jcs[0].getSelectedItem()));//set time line
			Processing.setOutputFormat((String)jcs[1].getSelectedItem());//set output format
		    Processing.setDecimal(Integer.parseInt(jtfs[0].getText()));//set the number of decimals in the output data
		    Processing.setErrorMargin(Double.parseDouble(jtfs[1].getText()));//set the minimum margin in errors in the calculation results
		    SAVE();
		    mainFrame.setVisible(false);
		    AssetFeatures.entry();
		}
		
	}
	
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
	
	//converters
	public char timeLineConverter(String str){
		char value;
		switch(str){
		case("monthly"):
			value='M';
		    break;
		case("quarterly"):
			value='Q';
		    break;
		case("biannual"):
			value='B';
		    break;
		case("annual"):
			value='A';
		    break;
		case("weekly"):
			value='W';
		    break;
		case("daily"):
			value='D';
		    break;
		default:
			value='M';
		}
		return value;
	}
}
