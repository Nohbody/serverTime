import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Listener implements Runnable{

	public String color;
	public File dataFile;
	public Scanner dataScan;
	
	public Listener() throws FileNotFoundException {
		dataFile = new File("./src/Color.txt");
		dataScan = new Scanner(dataFile);
		color = dataScan.nextLine();
	}
	
	public void run() {
		try {
			dataScan = new Scanner(dataFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		color = dataScan.nextLine();
		
		if (color.equals("RED"))
			Driver.newPanel.sharedView.setBackground(Color.RED);
		if (color.equals("GREEN"))
			Driver.newPanel.sharedView.setBackground(Color.GREEN);
		if (color.equals("CYAN"))
			Driver.newPanel.sharedView.setBackground(Color.CYAN);
		if (color.equals("BLACK"))
			Driver.newPanel.sharedView.setBackground(Color.BLACK);
	}

}
