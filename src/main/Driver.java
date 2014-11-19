package main;
/* *************************************************************
 File Name: Driver.java
 Last Changed Date: October 27th, 2014
 Purpose: Displays the TestPanel showing inter-user connectivity
 Author: Adam Clemons
 ************************************************************* */

import gui.GUI;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


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
		
		// Tells the DB the user has disconnected
		displayFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if (currentUser != null) {
					DBOps.updateData("users", "connected", "0", "user", currentUser.getName());
					DBOps.updateData("info", "string_colour", "SkyNet" + ": " + currentUser.getName() + " ran away from chat.", "id", "2");
					try {
						DBOps.updateData("info", "time_stamp", Driver.newPanel.chatPanel.getTimeStamp(), "id", "2");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				System.out.println("Disconnected");
                System.exit(0);
            }
		});
		
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
	
	public static String onlineUsers() {
		updateUsers();
		String onlineUsers = "SkyNet";
		for (int i = 0; i < users.size(); i ++) {
			if ((Integer.parseInt((DBOps.getData("users", users.get(i).getName(), "user", "connected")).get(0))) == 1) {
				onlineUsers = onlineUsers + ", " + users.get(i).getName();
			};
		}
		return onlineUsers;
	}
}
