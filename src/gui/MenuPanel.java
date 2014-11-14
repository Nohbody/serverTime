package gui;

import collector.src.tileMapStuff.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/* *************************************************************
File Name: MenuPanel.java
Last Changed Date: November 10th, 2014
Purpose: Layout for game access
Author: Adam Clemons
************************************************************* */

public class MenuPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	public JButton snake, collector, stats;
	public GridBagLayout gLayout;
	public GridBagConstraints c;
    public Game game;
	
	public MenuPanel() {
		snake = new JButton("Snake");
		collector = new JButton("Collector");
		stats = new JButton("Stats");

        collector.addActionListener(this);

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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == collector)
        {
            System.out.println("im the collector button");


            if (game == null)
            {
                try
                {
                    game = new Game();
                    add(game);
                } catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
}
