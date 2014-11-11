package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Driver;

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
			passwordField = new JFormattedTextField();
				passwordField.setColumns(10);
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
			boolean success;
			// Log in check
			if (loggingin) {
				System.out.println("This guy is trying to log in.");
				success = true;
			}
			// Registration check
			else {
				System.out.println("This guy is trying to register.");
				success = true;
			}
			
			if (success) {
				Driver.newPanel.mainPanel.setLayout(new FlowLayout());
				Driver.newPanel.mainPanel.removeAll();
				Driver.newPanel.mainPanel.setPreferredSize(null);
				Driver.newPanel.mainPanel.add(new MenuPanel());
				
				Driver.newPanel.revalidate(); Driver.newPanel.repaint();
			}
			
		}
	}
}
