package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import main.DBOps;
import main.Driver;

public class StatsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JLabel messages, loggedIn, playedSnake, playedCollector, scoredSnake, scoredCollector, wonSnake, wonCollector;
	public JPanel labelPanel;
	public JButton menuButton;
	public GridLayout gLayout;
	
	public Timer enabler = new Timer(1000, new StatsListener());
	
	public StatsPanel() {
		
		messages = new JLabel("Messages: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "messages")).get(0));
			messages.setForeground(Color.WHITE);
			messages.setBackground(Color.BLACK);
		loggedIn = new JLabel("Logged in: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "loggedIn")).get(0));
			loggedIn.setForeground(Color.WHITE);
			loggedIn.setBackground(Color.BLACK);
		playedSnake = new JLabel("Played Snake: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "playedSnake")).get(0));
			playedSnake.setForeground(Color.WHITE);
			playedSnake.setBackground(Color.BLACK);
		playedCollector = new JLabel("Played Collector: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "playedCollector")).get(0));
			playedCollector.setForeground(Color.WHITE);
			playedCollector.setBackground(Color.BLACK);
		scoredSnake = new JLabel("Snake score: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "scoredSnake")).get(0));
			scoredSnake.setForeground(Color.WHITE);
			scoredSnake.setBackground(Color.BLACK);
		scoredCollector = new JLabel("Collector score: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "scoredCollector")).get(0));
			scoredCollector.setForeground(Color.WHITE);
			scoredCollector.setBackground(Color.BLACK);
		wonSnake = new JLabel("Team Snake wins: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "wonSnake")).get(0));
			wonSnake.setForeground(Color.WHITE);
			wonSnake.setBackground(Color.BLACK);
		wonCollector = new JLabel("Team Collector wins: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "wonCollector")).get(0));
			wonCollector.setForeground(Color.WHITE);
			wonCollector.setBackground(Color.BLACK);
		
		menuButton = new JButton("Back to Menu");
			menuButton.setBackground(Color.WHITE);
			menuButton.setHorizontalAlignment(SwingConstants.CENTER);
			menuButton.setFocusPainted(false);
			menuButton.addActionListener(new MenuListener());
		
		labelPanel = new JPanel();
		gLayout = new GridLayout(7,2);
		labelPanel.setLayout(gLayout);
		labelPanel.setBackground(Color.BLACK);
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		
		labelPanel.add(messages);
		labelPanel.add(loggedIn);
		labelPanel.add(new JLabel(""));
		labelPanel.add(new JLabel(""));
		labelPanel.add(playedSnake);
		labelPanel.add(playedCollector);
		labelPanel.add(new JLabel(""));
		labelPanel.add(new JLabel(""));
		labelPanel.add(scoredSnake);
		labelPanel.add(scoredCollector);
		labelPanel.add(new JLabel(""));
		labelPanel.add(new JLabel(""));
		labelPanel.add(wonSnake);
		labelPanel.add(wonCollector);
		
		add(labelPanel, BorderLayout.CENTER);
		add(menuButton, BorderLayout.PAGE_END);
		
		enabler.start();
	}
	
	private class MenuListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			enabler.stop();
			Driver.newPanel.mainPanel.setLayout(new FlowLayout());
			Driver.newPanel.mainPanel.removeAll();
			Driver.newPanel.mainPanel.setPreferredSize(null);

			Driver.newPanel.mainPanel.add(new MenuPanel());
			
			Driver.newPanel.revalidate(); Driver.newPanel.repaint();
		}
		
	}
	
	private class StatsListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			messages.setText("Messages: " + (DBOps.getData("users", Driver.currentUser.getName(), "user", "messages")).get(0));
		}
		
	}
}
