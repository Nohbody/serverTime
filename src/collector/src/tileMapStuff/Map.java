package collector.src.tileMapStuff;

import java.awt.*;
import java.io.FileNotFoundException;

/**
 * The map holds the data about game area. In this case its responsible
 * for both rendering the map and check collision against the grid cells
 * within. 
 * 
 * Our map is a simple WIDTHxHEIGHT grid containing value 0 to indicate
 * a clear cell and 1 to incidate a wall.
 * 
 * @author Kevin Glass
 */
public class Map {
	/** The value indicating a clear cell */
	private static final int CLEAR = 0;
	/** The value indicating a isBlocked cell */
	private static final int BLOCKED = 1;
	
	/** The width in grid cells of our map */
	private static final int WIDTH = 25;
	/** The height in grid cells of our map */
	private static final int HEIGHT = 15;
	
	/** The rendered size of the tile (in pixels) */
	public static final int TILE_SIZE = 40;
	
	/** The actual data for our map */
	private int[][] data = new int[WIDTH][HEIGHT];
    public static String color;

    /**
	 * Create a new map with some default contents
	 */
	public Map() throws FileNotFoundException
    {
		// create some default map data - it would be way
		// cooler to load this from a file and maybe provide
		// a map editor of some sort, but since we're just doing
		// a simple tutorial here we'll manually fill the data
		// with a simple little map

        for (int y=0;y<HEIGHT;y++) {
			data[0][y] = BLOCKED;

			data[7][y] = BLOCKED;
			data[11][y] = BLOCKED;
			data[WIDTH-1][y] = BLOCKED;
		}
		for (int x=0;x<WIDTH;x++) {

			data[x][0] = BLOCKED;
			data[x][HEIGHT-1] = BLOCKED;
		}
		
		data[4][9] = CLEAR;
		data[7][5] = CLEAR;
		data[7][4] = CLEAR;
		data[11][7] = CLEAR;


    }

	/**
	 * Render the map to the graphics context provided. The rendering
	 * is just simple fill rectangles
	 * 
	 * @param g The graphics context on which to draw the map
	 */

    public void paint(Graphics2D g) {
		// loop through all the tiles in the map rendering them
		// based on whether they block or not

        for (int x=0;x<WIDTH;x++) {
			for (int y=0;y<HEIGHT;y++) {

				// so if the cell is blocks, draw a light grey block
				// otherwise use a dark gray


				if (data[x][y] == BLOCKED) {

                    if (color.equals("RED"))
                        g.setColor(Color.RED);
                    else if (color.equals("BLACK"))
                    {
                        g.setColor(Color.BLACK);

                    }
                    else if (color.equals("GREEN"))
                    {
                        g.setColor(Color.GREEN);

                    }
                    else if (color.equals("CYAN"))
                        g.setColor(Color.CYAN);
                    else
                        g.setColor(Color.blue);
                    g.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
				}

			}
		}
	}
	
	/**
	 * @param x The x position to check for blocking
	 * @param y The y position to check for blocking
	 * @return True if the location is isBlocked
	 */
	public boolean isBlocked(float x, float y) {
		// look up the right cell (based on simply rounding the floating
		// values) and check the value
		return data[(int) x][(int) y] == BLOCKED;
	}
}
