package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import main.DBOps;

/* *************************************************************
File Name: ChatPanel.java
Last Changed Date: November 10th, 2014
Purpose: Layout for chatting
Author: Adam Clemons
************************************************************* */

public class ChatPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public ArrayList<String> messages;
	public JTextPane messageArea;
	public JScrollPane messagePane;
	public JFormattedTextField messageField;
	public JButton send;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
	public String lastSent;
	public ChatRunner chatRunner;
	public SimpleAttributeSet keyWord;
	
	
	public ChatPanel() throws ParseException, BadLocationException {
		
		chatRunner = new ChatRunner();
		messages = new ArrayList<String>();
		messageField = new JFormattedTextField();
			messageField.setColumns(20);
			messageField.setText("BLAH");
		send = new JButton("Send");
			send.addActionListener(new SendListener());
		messageArea = new JTextPane();
			messageArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret)messageArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		messagePane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			messagePane.setPreferredSize(new Dimension(450, 80));
		
		keyWord = new SimpleAttributeSet();
			
		messages.add("User1: Hi!");
		messages.add("Steven: What is up?");
		messages.add("Steven: What is up?");
		messages.add("Steven: What is up?");
		messages.add("Steven: What is up?");
		messages.add("Steven: What is up?");
		
		gLayout = new GridBagLayout();
		c = new GridBagConstraints();
		setLayout(gLayout);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = .7;
		c.gridx = 0;
		c.gridy = 0;
		add(messagePane,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.weightx = .8;
		c.weighty = .3;
		c.gridx = 0;
		c.gridy = 1;
		add(messageField,c);
		
		c.weightx = .2;
		c.gridx = 1;
		c.gridy = 1;
		add(send,c);
		
		updateChat();
	}
	
	public void updateChat() throws BadLocationException {
		messageArea.setText(" ");
		if (messages.size() < 6)
			messageArea.setLayout(new GridLayout(6,1));
		else
			messageArea.setLayout(new GridLayout(messages.size(),1));
		
		for (String m:messages) {
			String[] textWords = m.split(" ");
			String username = textWords[0].substring(0, textWords[0].length());
			String message = m.substring(username.length(), m.length());
			
			StyleConstants.setForeground(keyWord, Color.RED);
			messageArea.getStyledDocument().insertString(messageArea.getText().length(),username, keyWord);
			StyleConstants.setForeground(keyWord, Color.BLACK);
			messageArea.getStyledDocument().insertString(messageArea.getText().length(),message + "\n", keyWord);
		};
		
		if (messages.size() < 6) {
			for (int i = 6-messages.size(); i < 6; i++)
				messageArea.add(new JLabel(""));
		}
		messageArea.revalidate(); messageArea.repaint();
	}
	
	public String getTimeStamp() throws ParseException {
	    Date currentTime = new Date();
	    DateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	    timeFormat.setTimeZone(TimeZone.getTimeZone(""));

	    String standardTime  = timeFormat.format(currentTime);
		return standardTime;
	}
	
	private class SendListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			DBOps.updateData("info", "string_colour", messageField.getText(), "id", "2");
			try {
				DBOps.updateData("info", "time_stamp", getTimeStamp(), "id", "2");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class ChatRunner implements Runnable {

		public void run() {
			System.out.println("RUNNING");
			
			
			if (!(DBOps.getData("info", "2", "id", "string_colour").get(0).equals(messages.get(messages.size()-1))
					&& DBOps.getData("info", "2", "id", "time_stamp").get(0).equals(lastSent))) {
					messages.add((String) DBOps.getData("info", "2", "id", "string_colour").get(0));
					lastSent = (String) DBOps.getData("info", "2", "id", "time_stamp").get(0);
					try {
						updateChat();
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
			}
		}
		
	}
}
