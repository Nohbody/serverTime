package gui;

import main.DBOps;
import main.Driver;
import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
							JOptionPane.showMessageDialog(null, "You are now logged in!");
							Driver.currentUser = new User(u.getName(), passwordField.getText());
							success = true;
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
							attemptName + "\", \"" + passwordField.getText() + "\", \"0");
				}
			}
			
			if (success) {
				Driver.newPanel.mainPanel.setLayout(new FlowLayout());
				Driver.newPanel.mainPanel.removeAll();
				Driver.newPanel.mainPanel.setPreferredSize(null);
				MenuPanel mp= new MenuPanel();
                Driver.newPanel.mainPanel.add(mp);
                /**this is where i need to add the new panel
                 *
                 */
				Driver.newPanel.mainPanel.remove(mp);
				Driver.newPanel.chatPanel.messageField.setText("");
				Driver.newPanel.chatPanel.messageField.setEditable(true);
				Driver.newPanel.chatPanel.send.setEnabled(true);
				
				Driver.newPanel.revalidate(); Driver.newPanel.repaint();
			}
			
		}
	}
}
