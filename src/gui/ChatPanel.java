package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import main.DBOps;
import main.Driver;

/* *************************************************************
File Name: ChatPanel.java
Last Changed Date: November 10th, 2014
Purpose: Layout for chatting
Author: Adam Clemons
************************************************************* */

public class ChatPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	public ArrayList<String> messages;
	public JTextPane messageArea;
	public JScrollPane messagePane;
	public JFormattedTextField messageField;
	public JButton send;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
	public String lastSent;
	public SimpleAttributeSet keyWord;
	
	
	public ChatPanel() throws ParseException, BadLocationException {
		messages = new ArrayList<String>();
		messageField = new JFormattedTextField();
			messageField.setColumns(20);
			messageField.setText("You must log in to chat.");
			messageField.addKeyListener(new EnterListener());
			messageField.setEditable(false);
		send = new JButton("Send");
			send.addActionListener(new SendListener());
			send.setEnabled(false);
		messageArea = new JTextPane();
			messageArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret)messageArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		messagePane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			messagePane.setPreferredSize(new Dimension(450, 80));
		
		keyWord = new SimpleAttributeSet();
			
		messages.add("Skynet: Welcome to Server Time Chat!");
		
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
			if (messageField.getText().equals("/users")) {
				
				DBOps.updateData("info", "string_colour", "SkyNet" + ": " + " Online users: " + Driver.onlineUsers(), "id", "2");
				try {
					DBOps.updateData("info", "time_stamp", getTimeStamp(), "id", "2");
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			else {
				DBOps.updateData("info", "string_colour", Driver.currentUser.getName() + ": " + messageField.getText(), "id", "2");
				try {
					DBOps.updateData("info", "time_stamp", getTimeStamp(), "id", "2");
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			messageField.setText("");
		}
	}
	
	private class EnterListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				send.doClick();
		}

		public void keyReleased(KeyEvent arg0) {
		}

		public void keyTyped(KeyEvent arg0) {
		}
		
	}

	public void run() {

		if (!(DBOps.getData("info", "2", "id", "string_colour").get(0)
				.equals(messages.get(messages.size() - 1)) && DBOps
				.getData("info", "2", "id", "time_stamp").get(0)
				.equals(lastSent))) {
			messages.add((String) DBOps.getData("info", "2", "id","string_colour").get(0));
			lastSent = (String) DBOps.getData("info", "2", "id", "time_stamp").get(0);
			try {
				updateChat();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

}
