package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import main.DBOps;
import main.Driver;
import main.User;



/* *************************************************************
File Name: LoginPanel.java
Last Changed Date: November 10th, 2014
Purpose: Layout for logging in
Author: Adam Clemons
************************************************************* */

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JButton login, register;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
	public JLabel username, password;
	public JFormattedTextField usernameField, passwordField;
	public JButton submit;
	private boolean loggingin;
	
	public LoginPanel() {
		login = new JButton("Login!");
			login.addActionListener(new ButtonListener());
		register = new JButton("Register!");
			register.addActionListener(new ButtonListener());
		setLayout(new GridLayout(1, 2));
		
		add(login);
		add(register);
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == login)
				loggingin = true;
			else 
				loggingin = false;
			
			// Log in components
			gLayout= new GridBagLayout();
			c = new GridBagConstraints();
	
			username = new JLabel("Username:");
				username.setForeground(Color.WHITE);
			password = new JLabel("Password:");
				password.setForeground(Color.WHITE);
			usernameField = new JFormattedTextField();
				usernameField.setColumns(10);
				usernameField.addKeyListener(new RegexOnly());
			passwordField = new JFormattedTextField();
				passwordField.setColumns(10);
				passwordField.addKeyListener(new RegexOnly());
			submit = new JButton("Submit");
				submit.addActionListener(new SubmitListener());
			
			setLayout(gLayout);
			removeAll();
			
			// Add components
			c.fill = GridBagConstraints.BOTH;
			c.weighty = (double) 1/3;
			c.weightx = .5;
			c.gridx = 0;
			c.gridy = 0;
			add(username,c);
			
			c.gridx = 1;
			c.gridy = 0;
			add(usernameField,c);
			
			c.gridx = 0;
			c.gridy = 1;
			add(password,c);
			
			c.gridx = 1;
			c.gridy = 1;
			add(passwordField,c);
			
			c.gridwidth = 2;
			c.weightx = 1;
			c.gridx = 0;
			c.gridy = 2;
			add(submit,c);
			
			Driver.newPanel.revalidate();

		}
	}

	private class SubmitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			boolean success = true;
			boolean found = false;
			
			// Log in check
			if (loggingin) {
				Driver.updateUsers();
				String attemptName = usernameField.getText();
				for (User u:Driver.users) {
					if (attemptName.toLowerCase().trim().equals(u.getName().toLowerCase().trim())) {
						found = true;
						
						if (passwordField.getText().equals(u.getPassword())) {
							if ((Integer.parseInt((DBOps.getData("users", u.getName(), "user", "connected")).get(0))) == 0) {
								JOptionPane.showMessageDialog(null, "You are now logged in!");
								Driver.currentUser = new User(u.getName(), passwordField.getText());
								DBOps.updateData("users", "connected", "1", "user", u.getName());
								DBOps.updateData("info", "string_colour", "SkyNet" + ": " + u.getName() + " has entered the fray.", "id", "2");
								int DBscore = Integer.parseInt((DBOps.getData("users", Driver.currentUser.getName(), "user", "loggedIn")).get(0)) + 1;
								DBOps.updateData("users", "loggedIn", "" + DBscore, "user", Driver.currentUser.getName() );
								try {
									DBOps.updateData("info", "time_stamp", "" + Driver.newPanel.chatPanel.getTimeStamp(), "id", "2");
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								success = true;
							}
							else {
								JOptionPane.showMessageDialog(null, "User is already logged in!");
								success = false;
								break;
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Invalid password!");
							success = false;
							break;
						}
					}
				}
				if (!found){
					JOptionPane.showMessageDialog(null, "Username not registered!");
					success = false;
				}
			}
			// Registration check
			else {
				Driver.updateUsers();
				String attemptName = usernameField.getText();
				if (attemptName.toLowerCase().equals("skynet") || attemptName.toLowerCase().equals("adam")) {
					JOptionPane.showMessageDialog(null, "Hide.");
					return;
				}
					
				for (User u:Driver.users) {
					if (attemptName.toLowerCase().trim().equals(u.getName().toLowerCase().trim())) {
						JOptionPane.showMessageDialog(null, "This username has already been taken");
						success = false;
					}
				}
				if (success) {
					JOptionPane.showMessageDialog(null, "Username registered!");
					Driver.currentUser = new User(attemptName.trim(), passwordField.getText());
					DBOps.insertData("users", "user`, `password`, `connected", 
							attemptName + "\", \"" + passwordField.getText() + "\", \"1");
					DBOps.updateData("info", "string_colour", "SkyNet" + ": " + Driver.currentUser.getName() + " has become one of us.", "id", "2");
					try {
						DBOps.updateData("info", "time_stamp", "" + Driver.newPanel.chatPanel.getTimeStamp(), "id", "2");
					} catch (ParseException e1) {
					}
						
				}
			}
			
			if (success) {
				Driver.newPanel.mainPanel.setLayout(new FlowLayout());
				Driver.newPanel.mainPanel.removeAll();
				Driver.newPanel.mainPanel.setPreferredSize(null);
				Driver.newPanel.mainPanel.setBackground(Color.MAGENTA);

				Driver.newPanel.mainPanel.add(new MenuPanel());

				Driver.newPanel.chatPanel.messageField.setText("");
				Driver.newPanel.chatPanel.messageField.setEditable(true);
				Driver.newPanel.chatPanel.send.setEnabled(true);
				
				Driver.newPanel.revalidate(); Driver.newPanel.repaint();
			}
			
		}
	}
	
	public class RegexOnly extends KeyAdapter {

		private int MAXSIZE = 10;
	    private String allowedRegex = "[^A-Za-z0-9]";

	    public void keyReleased(KeyEvent e) {
	        String curText = ((JTextComponent) e.getSource()).getText();
	        curText = curText.replaceAll(allowedRegex, "");
	        
	        if (curText.length() > MAXSIZE)
	        	curText = curText.substring(0, MAXSIZE);
	        if (curText.equals(""))
	        	curText = "0";
	        
	        ((JTextComponent) e.getSource()).setText(curText);
	    }
	}
}
