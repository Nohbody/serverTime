package collector.src.tileMapStuff;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Map
{
    /**
     * The value indicating a each type of cell
     */
    private static final int CLEAR = 0;
    private static final int BLOCKED = 1;
    private static final int COIN = 2;
    private static final int CLOSEST = 5;
    /**
     * The width  and height in grid cells of our map
     */
    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;
    /**
     * The rendered size of the tile (in pixels)
     */
    public static final int TILE_SIZE = 18;
    /**
     * The actual data for our map
     */
    private Color coinColor = new Color(244, 20, 100);
    private int[][] data;
    public ArrayList<Coin> coinList = new ArrayList<Coin>();

    public Map()
    {
//   will get this to work so we can impliment a level editor

//        BufferedReader br = new BufferedReader(new FileReader("collector/src/tileMapStuff/levelOne.txt"));
//        try {
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            while (line != null) {
//                sb.append(line);
//                sb.append(System.lineSeparator());
//                line = br.readLine();
//                System.out.println(line);
//            }
//            String everything = sb.toString();
//        } finally {
//            br.close();
//        }
        data = new int[][]

                {

                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 2, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1},
                        {1, 0, 2, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                        {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                };
    }

    public ArrayList<Coin> getCoinList()
    {
        return coinList;
    }

    /**
     * Render the map to the graphics context provided. The rendering
     * is just simple fill rectangles
     *
     * @param g The graphics context on which to draw the map
     */

    public void paint(Graphics g)
    {
        // loop through all the tiles in the map rendering them
        // based on whether they block or not
        coinList = new ArrayList<Coin>();
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {

                // so if the cell is blocks, draw a light grey block
                // otherwise use a dark gray
                if (data[x][y] == BLOCKED)
                {
                    g.setColor(new Color(13,140,200));
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                if (data[x][y] == COIN)
                {
                    coinList.add(new Coin(this, x, y));
                    g.setColor(Color.GREEN);
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2);

                }
                if (data[x][y] == CLOSEST)
                {
                    g.setColor(coinColor);
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2);

                }
            }
        }
    }

    /**
     * @param x The x position to check for blocking
     * @param y The y position to check for blocking
     * @return True if the location is isBlocked
     * is a coin and a method to reset the coin if
     * it is gathered
     */
    public boolean isBlocked(float x, float y)
    {
        return data[(int) x][(int) y] == BLOCKED;
    }

    public boolean isCoin(float x, float y)
    {
        return data[(int) x][(int) y] == COIN || data[(int) x][(int) y] == CLOSEST;
    }

    public void setClear(int x, int y)
    {
        data[x][y] = CLEAR;
        resetCoin();
    }

    public void setClosest(int x, int y)
    {
        for (int xCount = 0; xCount < WIDTH; xCount++)
        {
            for (int yCount = 0; yCount < HEIGHT; yCount++)
            {
//hoping this will reset what was the closest coin to a regular coin
                if (data[xCount][yCount] == CLOSEST)
                {
                    data[xCount][yCount] = COIN;
                }
            }
        }
        data[x][y] = CLOSEST;
    }

    //method to replace the coin in a new and random space
    public void resetCoin()
    {
        Random rand = new Random();
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        if (data[x][y] != BLOCKED && data[x][y] != COIN)
        {
            data[x][y] = COIN;
        }
        if (data[x][y] == BLOCKED)
        {
            resetCoin();
        }
    }
}
