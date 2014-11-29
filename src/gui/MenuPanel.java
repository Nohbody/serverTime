package gui;

import collector.src.tileMapStuff.Game;
import gui.ChatPanel.ServerTime;

import javax.swing.*;

import processing.core.PApplet;
import snake.GameApplet;
import main.Driver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/* *************************************************************
File Name: MenuPanel.java
Last Changed Date: November 10th, 2014
Purpose: Layout for game access
Author: Adam Clemons
************************************************************* */


public class MenuPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	public JButton snake, collector, stats;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
    public Game collectorGame;
    public Timer enabler = new Timer(1000, new EnableButton());
	
	public MenuPanel() {
		snake = new JButton("Snake");
			snake.addActionListener(new MenuButton());
			snake.setFocusPainted(false);
			snake.setBackground(Color.RED);
		collector = new JButton("Collector");
			collector.addActionListener(new MenuButton());
			collector.setFocusPainted(false);
			collector.setBackground(Color.GREEN);
		stats = new JButton("Stats");
			stats.addActionListener(new MenuButton());
			stats.setFocusPainted(false);
			stats.setBackground(Color.CYAN);
		
		if (Driver.returnRunning() == false) {
			snake.setEnabled(false);
			collector.setEnabled(false);
			if (collectorGame != null)
				collectorGame.myTimer.stop();
		}

		gLayout = new GridBagLayout();
		c = new GridBagConstraints();
		setBackground(Color.BLACK);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		add(snake,c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(collector,c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(stats,c);
		
		enabler.start();
	}


	private class MenuButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == collector)
	        {
				Driver.newPanel.mainPanel.setLayout(new FlowLayout());
				Driver.newPanel.mainPanel.removeAll();
				Driver.newPanel.mainPanel.setPreferredSize(null);
				collectorGame = new Game();
					JPanel gamePanel = new JPanel();
						gamePanel.setPreferredSize(new Dimension(450,270));
						gamePanel.setLayout(new BorderLayout());
						gamePanel.add(collectorGame, BorderLayout.CENTER);
				Driver.newPanel.mainPanel.add(gamePanel);
				Driver.newPanel.mainPanel.setBackground(Color.MAGENTA);
				
				Driver.newPanel.chatPanel.setFocusable(false);
				
				Driver.newPanel.chatPanel.messageField.setEditable(false);
				Driver.newPanel.chatPanel.messageField.setText("Help your team win!");
				Driver.newPanel.chatPanel.send.setEnabled(false);
				
				Driver.newPanel.chatPanel.restartThread();
				Driver.newPanel.revalidate(); Driver.newPanel.repaint();
	        }
			else if (e.getSource() == snake) {
				Driver.newPanel.mainPanel.setLayout(new FlowLayout());
				Driver.newPanel.mainPanel.removeAll();
				Driver.newPanel.mainPanel.setPreferredSize(null);
				PApplet mySnakeApplet = new GameApplet();
				mySnakeApplet.init();
				JPanel gamePanel = new JPanel();
					gamePanel.setPreferredSize(new Dimension(300,300));
					gamePanel.setLayout(new BorderLayout());
					gamePanel.add(mySnakeApplet, BorderLayout.CENTER);
				Driver.newPanel.mainPanel.add(gamePanel);
				Driver.newPanel.mainPanel.setBackground(Color.MAGENTA);
				
				Driver.newPanel.chatPanel.setFocusable(false);
				
				Driver.newPanel.chatPanel.messageField.setEditable(false);
				Driver.newPanel.chatPanel.messageField.setText("Help your team win!");
				Driver.newPanel.chatPanel.send.setEnabled(false);
				
				Driver.newPanel.chatPanel.restartThread();
				
				Driver.newPanel.revalidate(); Driver.newPanel.repaint();
				
			}
			else if (e.getSource() == stats) {
				System.out.println("This guy wants to view stats.");
			}
			
		}
		
	}

	private class EnableButton implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if (Driver.returnRunning() == true) {
				snake.setEnabled(true);
				collector.setEnabled(true);
			}
		}
	}

}
