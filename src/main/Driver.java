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
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.text.BadLocationException;

public class Driver {

	public static GUI newPanel;
	public static JFrame displayFrame;
	public static ArrayList<User> users;
	public static User currentUser;

	public static void main(String[] args) throws IOException, ParseException, BadLocationException {
		DBOps.connect();
		updateUsers();
		
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
	
	public static void updateUsers() {
		users = new ArrayList<User>();
		ArrayList<String> usernames = DBOps.getColumn("users", "user");
		ArrayList<String> passwords = DBOps.getColumn("users", "password");
		for (int i = 0; i < usernames.size(); i ++)
			users.add(new User((String) usernames.get(i), (String)passwords.get(i)));
	}
}
