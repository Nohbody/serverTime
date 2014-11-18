package collector.src.dbStuff;

import java.io.FileNotFoundException;


public class collectListener implements Runnable{

	public String color;

	
	public collectListener() throws FileNotFoundException {
		color = (String)(dbCollectConnect.getData("info", "1", "id", "string_colour")).get(0);
		System.out.println(color);
	}
	
	public void run() {
		collector.src.tileMapStuff.Map.color = (String)(dbCollectConnect.getData("info", "1", "id", "string_colour")).get(0);
	}

}
