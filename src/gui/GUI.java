package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

/* *************************************************************
 File Name: GUI.java
 Last Changed Date: November 10th, 2014
 Purpose: Main GUI handling
 Author: Adam Clemons
 ************************************************************* */

public class GUI extends JPanel{

	private static final long serialVersionUID = 1L;
	public JPanel mainPanel, chatPanel;
	public JLabel credits, title;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
	
	public GUI() {
		// Initialize components
		credits = new JLabel("Created by: SS Group 1");
			credits.setHorizontalAlignment(SwingConstants.CENTER);
			credits.setForeground(Color.WHITE);
			credits.setBackground(Color.BLACK);
				credits.setOpaque(true);
		title = new JLabel("Server Time");
			title.setFont(new Font("Arial Black", Font.PLAIN, 72));
			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setBorder(new MatteBorder(0,0,2,0,Color.MAGENTA));
			title.setForeground(Color.WHITE);
			title.setBackground(Color.BLACK);
				title.setOpaque(true);
		gLayout = new GridBagLayout();
			c = new GridBagConstraints();
		mainPanel = new LoginPanel();
			mainPanel.setPreferredSize(new Dimension(500, 500));		
			mainPanel.setBackground(Color.BLACK);
		chatPanel = new JPanel();
			chatPanel.add(credits);
			chatPanel.setBackground(Color.BLACK);
			chatPanel.setBorder(new MatteBorder(2,0,0,0,Color.MAGENTA));
		
		// Set panel settings
		setPreferredSize(new Dimension(500, 500));
		setLayout(gLayout);
		setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		setBackground(Color.BLACK);
		
		// Add components
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(title,c);
		
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 500;
		c.weighty = 300;
		c.gridx = 0;
		c.gridy = 1;
		add(mainPanel,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		add(chatPanel,c);
	}

}
