package programEntry;

import javax.swing.JOptionPane;

import controlFlow.Import;


public class StartWithInterface {

    public static void main(String[] args) throws Exception{
    	
    	try {
    	    Import.entry();
    	} 
    	catch (Throwable t) {
    	    JOptionPane.showMessageDialog(
    	        null, t.getClass().getSimpleName() + ": " + t.getMessage());
    	    throw t; // don't suppress Throwable
    	}
    }
}
