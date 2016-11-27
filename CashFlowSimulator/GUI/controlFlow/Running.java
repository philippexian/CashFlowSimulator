package controlFlow;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import parameterization.RiskFeatures2;
import processing.Processing;

public class Running implements ActionListener{

    private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel buttonPanel  = new JPanel();
	   
	private ImageIcon imageIcon;
	private JLabel imageLabel;
	
	private JButton back=new JButton("back");
	private JButton setter=new JButton("set portfolio");
	private JButton runner = new JButton("calculate");
	private JLabel brLabel1=new JLabel("                                                    "
				+ "                                                                             "
				+"                                                                              ");

	private JPanel anotherPanel=new JPanel();
	private JLabel warningImport=new JLabel("", JLabel.CENTER);
	private JLabel warningSetting=new JLabel("", JLabel.CENTER);
	   
	public Running(){
	    prepareGUI();
	}

	public static void entry(){
		Running running = new Running();  
		running.showJPanel();
	}

	private void prepareGUI(){
		mainFrame = new JFrame("Run");
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

		statusLabel.setSize(350,100);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel, BorderLayout.NORTH);

		mainFrame.add(buttonPanel, BorderLayout.SOUTH);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);  
	}


	private void showJPanel(){
		headerLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 36)); 
		headerLabel.setText("<html>Please run the program! </html>");      

		imageIcon = new ImageIcon("haha.gif"); 
		imageLabel = new JLabel(imageIcon);    
		imageLabel.setBounds(0, 0, 2*imageIcon.getIconWidth(), 2*imageIcon.getIconHeight());
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.CYAN);
		panel1.setLayout(new FlowLayout());  
		panel1.add(BorderLayout.CENTER,imageLabel);

		controlPanel.add(panel1); 
		controlPanel.add(brLabel1);
		controlPanel.add(anotherPanel);

		back.addActionListener(this);
		buttonPanel.add(back);
		setter.addActionListener(this);
		buttonPanel.add(setter);
		runner.addActionListener(this);
		buttonPanel.add(runner);
		      
		mainFrame.add(controlPanel, BorderLayout.CENTER);
		      
		mainFrame.setVisible(true);      
	}

	boolean portfolioSet=false; 
	    
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==back){
			mainFrame.setVisible(false);
			RiskFeatures2.entry();
		}

		//set portfolio
		if(ae.getSource()==setter){

			try {
				warningImport.setText("");
				warningSetting.setText("");

				Processing.setPorfolio();

				warningSetting.setText("      portfolio successfully set, ready to run!");
				warningSetting.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
				warningSetting.setForeground(Color.BLACK);
				anotherPanel.add(warningSetting, BorderLayout.CENTER);
			    mainFrame.setVisible(true); 
			        
			    portfolioSet=true;
					
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("fail to set the features of the portfolio!");

			    JOptionPane.showMessageDialog(null, e.toString(), "fail to set the features of the portfolio!",
			                                    JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}

		}
		
		//run and calculate
		if(ae.getSource()==runner){
			if(portfolioSet){
				try {
					Processing.simulateCashflow();
					
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("choose the output location");
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					chooser.showSaveDialog(null);
					
					if(chooser.getSelectedFile()!=null){
						Processing.writeDoc(chooser.getSelectedFile().toString());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.toString(), "fail to calculate th results!",
                            JOptionPane.ERROR_MESSAGE);
				}
					
				mainFrame.setVisible(false);
				System.exit(0);
			}
			else{
				warningSetting.setText("      please set the asset features first!");
				warningSetting.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
				warningSetting.setForeground(Color.RED);
				anotherPanel.add(warningSetting, BorderLayout.CENTER);
			    mainFrame.setVisible(true); 
			}
		}
			
	}

}
