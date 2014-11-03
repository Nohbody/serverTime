/* *************************************************************
 File Name: Driver.java
 Last Changed Date: October 27th, 2014
 Purpose: Displays the TestPanel showing inter-user connectivity
 Author: Adam Clemons
 ************************************************************* */

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class Driver {

	public static TestPanel newPanel;

	public static void main(String[] args) throws IOException {
		DBOps.connect();
		
		// Creates the JFrame, sets what happens when we close it, and makes it a fixed size (dependent on JPanel sizes)
		JFrame displayFrame = new JFrame("View Connectivity!"); 
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayFrame.setResizable(false);
		displayFrame.setLayout(new BorderLayout());	
		
		newPanel = new TestPanel();
		
		displayFrame.getContentPane().add(newPanel);
		
		// Packs the Frame up for delivery to the console
		displayFrame.pack();
		displayFrame.setLocationRelativeTo(null);
		displayFrame.setVisible(true); // Allows us to see the Frame
		
	}
}
