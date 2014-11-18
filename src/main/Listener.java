package main;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Listener implements Runnable{

	public String color;
	public File dataFile;
	public Scanner dataScan;
	
	public Listener() throws FileNotFoundException {
		color = (String)(DBOps.getData("info", "1", "id", "string_colour")).get(0);
		System.out.println(color);
	}
	
	public void run() {
		color = (String)(DBOps.getData("info", "1", "id", "string_colour")).get(0);
		
//		if (color.equals("RED"))
//			Driver.newPanel.sharedView.setBackground(Color.RED);
//		if (color.equals("GREEN"))
//			Driver.newPanel.sharedView.setBackground(Color.GREEN);
//		if (color.equals("CYAN"))
//			Driver.newPanel.sharedView.setBackground(Color.CYAN);
//		if (color.equals("BLACK"))
//			Driver.newPanel.sharedView.setBackground(Color.BLACK);
	}

}
