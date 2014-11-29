package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Timer;
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
	public long lastSent;
	public SimpleAttributeSet keyWord;
	public Thread myTimer, updateThread;
	public Timer newTimer;
	public int time;
	
	
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
			
		messages.add("SkyNet: Welcome to Server Time Chat! Available commands: /users, /start");
		
		keyWord = new SimpleAttributeSet();
		
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
		updateThread = new Thread(new UpdateChatThread());
		updateThread.start();
	}
	
	public long getTimeStamp() throws ParseException {
		return System.currentTimeMillis() / 1000L;
	}
	
	private class SendListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (messageField.getText().equals("/users")) {
				DBOps.updateData("info", "string_colour", "SkyNet" + ": " + " Online users: " + Driver.onlineUsers(), "id", "2");
				try {
					DBOps.updateData("info", "time_stamp", "" + getTimeStamp(), "id", "2");
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			
			else if (messageField.getText().equals("/start")) {
				if (Driver.returnRunning() == false) {
					
					DBOps.updateData("info", "time_stamp", "True", "id", "3");
					DBOps.updateData("scores", "Collector", "" + 0, "id", "1");
					DBOps.updateData("scores", "Snake", "" + 0, "id", "1");
					
					DBOps.updateData("info", "string_colour", "SkyNet" + ": " + "New match started! Teams have 60 seconds. Good luck!", "id", "2");
					try {
						DBOps.updateData("info", "time_stamp", "" + getTimeStamp(), "id", "2");
						DBOps.updateData("info", "time_stamp", "" + getTimeStamp(), "id", "5");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					DBOps.updateData("info", "time_stamp", "60", "id", "4");
				}
				else {
					DBOps.updateData("info", "string_colour", "SkyNet" + ": " + "Game is currently in progress.", "id", "2");
					try {
						DBOps.updateData("info", "time_stamp", "" + getTimeStamp(), "id", "2");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
			
			else {
				DBOps.updateData("info", "string_colour", Driver.currentUser.getName() + ": " + messageField.getText(), "id", "2");
				try {
					DBOps.updateData("info", "time_stamp", "" + getTimeStamp(), "id", "2");
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
				.equals("" + lastSent))) {
			lastSent = Long.parseLong(DBOps.getData("info", "2", "id", "time_stamp").get(0));
			messages.add((String) DBOps.getData("info", "2", "id","string_colour").get(0));
			try {
				updateChat();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	

	class ServerTime implements Runnable {
		private volatile boolean isRunning;
		
		public void run() {
			if (Driver.currentUser != null)
				isRunning = true;
			while(isRunning) {
				try {
					System.out.println(Long.parseLong(DBOps.getData("info", "2", "id", "time_stamp").get(0)));
					System.out.println(getTimeStamp());
					int tempTime = Integer.parseInt((DBOps.getData("info", "4", "id", "time_stamp")).get(0)) - 10;
					if (getTimeStamp() - Long.parseLong(DBOps.getData("info", "2", "id", "time_stamp").get(0))  >= 10 && Driver.returnRunning()) {
						if (Integer.parseInt((DBOps.getData("info", "4", "id", "time_stamp")).get(0)) - 10 >= 0)
							time = tempTime;
						DBOps.updateData("info", "time_stamp", "" + time, "id", "4");
						String s = (DBOps.getData("scores", "1", "id", "Snake")).get(0);
						String c = (DBOps.getData("scores", "1", "id", "Collector")).get(0);
	
						if (time > 0) {
							DBOps.updateData("info", "string_colour", "SkyNet" + ": " + "Teams have " + time + " seconds. S: " + s + " C: " + c, "id", "2");
							try {
								DBOps.updateData("info", "time_stamp", "" + getTimeStamp(), "id", "2");
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
						}
						else {
							String winner;
							DBOps.updateData("info", "time_stamp", "False", "id", "3");
							if (Integer.parseInt(s) > Integer.parseInt(c)) 
								winner = "Snakes win!";
							else if (Integer.parseInt(c) > Integer.parseInt(s))
								winner = "Collectors win!";
							else
								winner = "It's a tie!";
							DBOps.updateData("info", "string_colour", "SkyNet" + ": " + "Game Over! " + winner + " S: " + s + " C: " + c, "id", "2");
							try {
								DBOps.updateData("info", "time_stamp", "" + getTimeStamp(), "id", "2");
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							isRunning = false;
							
							Driver.newPanel.mainPanel.setLayout(new FlowLayout());
							Driver.newPanel.mainPanel.removeAll();
							Driver.newPanel.mainPanel.setPreferredSize(null);
	
							Driver.newPanel.mainPanel.add(new MenuPanel());
							Driver.newPanel.chatPanel.setFocusable(true);
	
							Driver.newPanel.chatPanel.messageField.setText("");
							Driver.newPanel.chatPanel.messageField.setEditable(true);
							Driver.newPanel.chatPanel.send.setEnabled(true);
							
							Driver.newPanel.revalidate(); Driver.newPanel.repaint();
							
							return;
						}
					}
					else {
						if (Driver.returnRunning() == false) {
							isRunning = false;
							Driver.newPanel.mainPanel.setLayout(new FlowLayout());
							Driver.newPanel.mainPanel.removeAll();
							Driver.newPanel.mainPanel.setPreferredSize(null);
	
							Driver.newPanel.mainPanel.add(new MenuPanel());
							Driver.newPanel.chatPanel.setFocusable(true);
	
							Driver.newPanel.chatPanel.messageField.setText("");
							Driver.newPanel.chatPanel.messageField.setEditable(true);
							Driver.newPanel.chatPanel.send.setEnabled(true);
							
							Driver.newPanel.revalidate(); Driver.newPanel.repaint();
							
							return;
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	
	private class UpdateChatThread implements Runnable {

		public void run() {
			messageArea.setText("\u200B");
			messageArea.setLayout(new GridLayout(messages.size(),1));
			
			for (String m:messages) {
				String[] textWords = m.split(" ");
				String username = textWords[0].substring(0, textWords[0].length());
				String message = m.substring(username.length(), m.length());
				
				StyleConstants.setForeground(keyWord, Color.RED);
				try {
					messageArea.getStyledDocument().insertString(messageArea.getText().length(),username, keyWord);
					StyleConstants.setForeground(keyWord, Color.BLACK);
					messageArea.getStyledDocument().insertString(messageArea.getText().length(),message + "\n", keyWord);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			};
			
			messageArea.revalidate(); messageArea.repaint();
			
		}
	}
	
	public void restartThread() {
		myTimer = new Thread(new ServerTime());
		myTimer.start();
	}
}
