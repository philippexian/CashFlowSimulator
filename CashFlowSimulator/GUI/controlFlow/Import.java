package controlFlow;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import processing.Processing;

public class Import implements ActionListener{
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JLabel warningLabel=new JLabel("", JLabel.CENTER);

   private JLabel brLabel1= new JLabel("                                                    "
			+ "                                                                             "
			+"                                                                              ");
   private JLabel brLabel2= new JLabel("<html><br><br></html>");
   private JPanel buttonPanel  = new JPanel();
   private JPanel anotherPanel=new JPanel();
   private JPanel attentionPanel=new JPanel();
   private JTextField delimiter;
   
   private ImageIcon imageIcon;
   private JLabel imageLabel;
   
   private JButton chooser = new JButton("choose a file");
   private JButton confirm = new JButton("OK");
   
   private static String Path="";
   private static String fileType="";
   private JFileChooser fDialog;

   public Import(){
      prepareGUI();
   }

   public static void entry(){
      Import  input = new Import();  
      input.showJPanelDemo();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Input");
      mainFrame.setSize(800,650);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setLayout(new BorderLayout(100, 100));

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
	  headerLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 36)); 
      headerLabel.setText("<html>Welcome to the cashflow simulator </html>");      

      imageIcon = new ImageIcon("CA.png"); //image icon
      imageLabel = new JLabel(imageIcon); 
      imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
      JPanel panel1 = new JPanel();
      panel1.setBackground(Color.GREEN);
      panel1.setLayout(new FlowLayout());  
      panel1.add(BorderLayout.CENTER,imageLabel);

      controlPanel.add(panel1, BorderLayout.CENTER); 
      controlPanel.add(brLabel1);
      
      JLabel attentionLabel=new JLabel("        Attention: this program supports only 3 file formats: xlsx, csv, txt", JLabel.CENTER);
      attentionPanel.add(attentionLabel, BorderLayout.CENTER);
      controlPanel.add(attentionPanel);
      
      controlPanel.add(brLabel2);
      controlPanel.add(anotherPanel);
      
      chooser.addActionListener(this);
      buttonPanel.add(chooser, BorderLayout.SOUTH);
      confirm.addActionListener(this);
      buttonPanel.add(confirm, BorderLayout.SOUTH);
      
      mainFrame.add(controlPanel, BorderLayout.CENTER);
      
      mainFrame.setVisible(true);      
   }

   boolean correctType=false;
   
@Override
    public void actionPerformed(ActionEvent ae) {
	

	
	    if(ae.getSource()==chooser){

		    fDialog = new JFileChooser();
		    fDialog.setDialogTitle("choose a file");
		    fDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    int returnVal = fDialog.showOpenDialog(null);
		    
		    if(fDialog.getSelectedFile()!=null){
		    	Path=fDialog.getSelectedFile().toString();
		    	fileType=Path.substring(Path.lastIndexOf('.'));//the part after .
		    }
		
		
		    if(fileType.contains("csv")||fileType.contains("txt")){//csv txt must have a delimiter
		    	correctType=true;
		    	
			    warningLabel.setText("        file type:"+fileType+"    please enter the delimiter");  
			    warningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
			    warningLabel.setForeground(Color.BLACK);
			
			    delimiter = new JTextField(4);

			    anotherPanel.add(warningLabel, BorderLayout.CENTER);
			    anotherPanel.add(delimiter, BorderLayout.CENTER);
		        mainFrame.setVisible(true);  

		    }
		    
		    else if(fileType.contains("xls")){//excel type
		    	correctType=true;
		    	warningLabel.setText("");  
		    	anotherPanel.add(warningLabel, BorderLayout.CENTER);
		    	mainFrame.setVisible(true); 
		    }
		    
		    else{//if the file type is incorrect, show a warning
			    correctType=false;
			    warningLabel.setText("        the file type is not a correct one!");
			    warningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
			    warningLabel.setForeground(Color.RED);
			    anotherPanel.add(warningLabel, BorderLayout.CENTER);
		        mainFrame.setVisible(true); 
		    }
		
		    System.out.println(Path);
	    }
	
	    if(ae.getSource()==confirm && correctType){//when the file type is correct we continue to set the path & file type
			Processing.setFilePath(Path);
			Processing.setFileType(fileType);
			
			
			if(fileType.contains("csv")||fileType.contains("txt")){
			    Processing.setDelimiter(delimiter.getText());
			}
			
			ImportIndex.entry();

		    mainFrame.setVisible(false);
	    }
	
}   
}
