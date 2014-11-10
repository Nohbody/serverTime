package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/* *************************************************************
File Name: ChatPanel.java
Last Changed Date: November 10th, 2014
Purpose: Layout for chatting
Author: Adam Clemons
************************************************************* */

public class ChatPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public ArrayList<JLabel> messages;
	public JPanel messageArea;
	public JScrollPane messagePane;
	public JFormattedTextField messageField;
	public JButton send;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
	
	public ChatPanel() {
		messages = new ArrayList<JLabel>();
		messageField = new JFormattedTextField();
			messageField.setColumns(20);
		send = new JButton("Send");
		messageArea = new JPanel();
		messagePane = new JScrollPane(messageArea);
		
		messages.add(new JLabel("User1: Hi!"));
		messages.add(new JLabel("Steven: What is up?"));
		
		gLayout = new GridBagLayout();
		c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		add(messagePane,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(messageField,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 2;
		add(send,c);
		
		updateChat();
	}
	
	public void updateChat() {
		messageArea.removeAll();
		for (JLabel m:messages) {
			messageArea.add(m);
		};
		messageArea.revalidate(); messageArea.repaint();
	}

}
