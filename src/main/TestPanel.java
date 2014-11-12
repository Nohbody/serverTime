
package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	public JPanel ownWindow, sharedWindow, ownView, sharedView;
	public JButton redButton, cyanButton, greenButton;
	public JLabel userLabel, sharedLabel;
	
	public File dataFile;
	public FileWriter dataWriter;
	public BufferedWriter bufferedWriter;
	
	private ScheduledThreadPoolExecutor poolExecutor;
	public Listener dataListener;

	public TestPanel() throws IOException {
		setPreferredSize(new Dimension(500,500));
		setLayout(new GridBagLayout());
		
		ownWindow = new JPanel();
		ownWindow.setLayout(new BorderLayout());
		
		ownView = new JPanel();
		ownView.setBackground(Color.BLACK);
		ownView.setPreferredSize(new Dimension(150,150));
		ownView.setBorder(new LineBorder(Color.BLACK, 5));
		
		redButton = new JButton();
		redButton.setPreferredSize(new Dimension(50,20));
		redButton.setBackground(Color.RED);
		redButton.addActionListener(new ButtonListener());
		
		cyanButton = new JButton();
		cyanButton.setPreferredSize(new Dimension(50,20));
		cyanButton.setBackground(Color.CYAN);
		cyanButton.addActionListener(new ButtonListener());
		
		greenButton = new JButton();
		greenButton.setPreferredSize(new Dimension(50,20));
		greenButton.setBackground(Color.GREEN);
		greenButton.addActionListener(new ButtonListener());
		
		ownWindow.add(ownView, BorderLayout.NORTH);
		ownWindow.add(redButton, BorderLayout.LINE_START);
		ownWindow.add(cyanButton, BorderLayout.CENTER);
		ownWindow.add(greenButton, BorderLayout.LINE_END);
		
		userLabel = new JLabel("Individual View");
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ownWindow.add(userLabel, BorderLayout.SOUTH);
		
		add(ownWindow);
		JLabel spacer = new JLabel("      ");
		add(spacer);
		
		sharedWindow = new JPanel();
		sharedWindow.setLayout(new BorderLayout());
		
		sharedView = new JPanel();
		sharedView.setPreferredSize(new Dimension(300,300));
		sharedView.setBorder(new LineBorder(Color.BLACK, 5));
		sharedView.setBackground(Color.RED);
		
		sharedLabel = new JLabel("Shared View");
		sharedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		sharedWindow.add(sharedView, BorderLayout.CENTER);
		sharedWindow.add(sharedLabel, BorderLayout.SOUTH);
		
		add(sharedWindow);
		
		dataListener = new Listener();
		poolExecutor = new ScheduledThreadPoolExecutor(1);
		poolExecutor.scheduleAtFixedRate(dataListener, (long) 100, (long) 100, TimeUnit.MILLISECONDS);
	}
	
	private class ButtonListener implements ActionListener {

		public ButtonListener() throws IOException {
			DBOps.updateData("info", "string_colour", "BLACK", "id", "1");
		}
		
		public void actionPerformed(ActionEvent e){
			
			
			if (e.getSource() == redButton) {
				ownView.setBackground(Color.RED);
				// Change shared data to red
				DBOps.updateData("info", "string_colour", "RED", "id", "1");
			}
			else if (e.getSource() == cyanButton) {
				ownView.setBackground(Color.CYAN);
				// Change shared data to cyan
				DBOps.updateData("info", "string_colour", "CYAN", "id", "1");
			}
			else {
				ownView.setBackground(Color.GREEN);
				// Change shared data to green
				DBOps.updateData("info", "string_colour", "GREEN", "id", "1");
			}
			
		}
	}
}
