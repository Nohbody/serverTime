package main;
/* *************************************************************
 File Name: Driver.java
 Last Changed Date: October 27th, 2014
 Purpose: Displays the TestPanel showing inter-user connectivity
 Author: Adam Clemons
 ************************************************************* */

import gui.GUI;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class Driver {

	public static GUI newPanel;
	public static JFrame displayFrame;

	public static void main(String[] args) throws IOException {
		DBOps.connect();
		
		// Creates the JFrame, sets what happens when we close it, and makes it a fixed size (dependent on JPanel sizes)
		displayFrame = new JFrame("Server Time!"); 
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayFrame.setResizable(false);
		displayFrame.setLayout(new BorderLayout());	
		
		newPanel = new GUI();
		
		displayFrame.getContentPane().add(newPanel);
		
		// Packs the Frame up for delivery to the console
		displayFrame.pack();
		displayFrame.setLocationRelativeTo(null);
		displayFrame.setVisible(true); // Allows us to see the Frame
		
	}
}
