package gui;

import collector.src.tileMapStuff.Game;

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
    public Game game;
	
	public MenuPanel() {
		snake = new JButton("Snake");
			snake.addActionListener(new MenuButton());
		collector = new JButton("Collector");
			collector.addActionListener(new MenuButton());
		stats = new JButton("Stats");
			stats.addActionListener(new MenuButton());

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
		
	}


	private class MenuButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == collector)
	        {
				Driver.newPanel.mainPanel.setLayout(new FlowLayout());
				Driver.newPanel.mainPanel.removeAll();
				Driver.newPanel.mainPanel.setPreferredSize(null);
				Game collectorGame;
				collectorGame = new Game();
					JPanel gamePanel = new JPanel();
						gamePanel.setPreferredSize(new Dimension(450,270));
						gamePanel.setLayout(new BorderLayout());
						gamePanel.add(collectorGame, BorderLayout.CENTER);
				Driver.newPanel.mainPanel.add(gamePanel);
				Driver.newPanel.mainPanel.setBackground(Color.MAGENTA);
				
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
				
				Driver.newPanel.revalidate(); Driver.newPanel.repaint();
				
			}
			else if (e.getSource() == stats) {
				System.out.println("This guy wants to view stats.");
			}
			
		}
		
	}

}
