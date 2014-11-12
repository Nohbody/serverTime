package gui;

import javax.swing.*;
import java.awt.*;

/* *************************************************************
File Name: MenuPanel.java
Last Changed Date: November 10th, 2014
Purpose: Layout for game access
Author: Adam Clemons
************************************************************* */

public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public JButton snake, collector, stats;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
	
	public MenuPanel() {
		snake = new JButton("Snake");
		collector = new JButton("Collector");
		stats = new JButton("Stats");
		
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
}
