package collector.src.tileMapStuff;

import main.DBOps;
import main.Driver;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by newScanTron on 11/29/2014.
 */
public class collectorPApplet extends PApplet
{
    //all my variables
    private static final long serialVersionUID = 1L;
    private final int height = 270;
    private final int length = 450;
    private Color myPurp = new Color(150, 0, 220);
    //booleas for which key is pressed
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    private String dbString = "", DBscore = "";
    private int closeInt = 0;
    private Thread t, dbThread;
    //some more stuff that im adding from the game canvas
    Map map = new Map();
    private ArrayList<Coin> coins;
    Coin myCoin;
    private float gravity = 0.11f;
    Entity player;


    static public void main(String args[]) {
        PApplet.main(new String[] { "collector.src.tileMapStuff.collectorPApplet" });
    }

    public void setup()
    {
        size(450, 270);
        background(0);
        frameRate(20);

        player = new Entity(map, 3f, 2f);
        coins = new ArrayList<Coin>();
        myCoin = new Coin(map, 60, 60);

        setBackground(Color.black);
        setPreferredSize(new Dimension(length, height));
        setFocusable(true);



    }

    public void draw()
    {

        background(150, 0, 220);

        player.move(0, gravity);
        coins = map.getCoinList();
        float currlow = 1000;
        float currX = 0;
        float currY = 0;
        for (Coin c : coins)
        {
            float currDist = c.proximity(player.getX(), player.getY());
            if (currDist < currlow)
            {
                currlow = currDist;
                currX = c.getX();
                currY = c.getY();
            }
        }
        map.setClosest((int) currX, (int) currY);
        closeInt = 2;
        dbThread = new Thread(new dbStuff(), "dbThread");
        dbThread.start();

//        page.fillRect(0, 0, 450, 270);
       map.paint();
        player.paint();

        logic();

    }

    public void logic()
    {
        float dx = 0;
        float dy = 0;
        float speed = 2.5f;
        float jump = 9.0f;
        float downPound = 6f;
        float smoothing = 0.04f;
        if (left)
        {
            dx -= speed;
        }
        if (right)
        {
            dx += speed;
        }
        if (up && player.isGrounded())
        {
            dy -= jump;
        }
        if (down)
        {
            dy += downPound;
        }
        player.move(dx * smoothing, dy * smoothing);
    }

    public void keyPressed()
    {

        switch (key)
        {
            case ('w'):
                up = true;
                break;

            case ('a'):
                left = true;
                break;

            case ('s'):
                down = true;
                break;

            case ('d'):
                right = true;
                break;

            case (' '):
                setup();
        }

    }
    public void keyReleased()
    {

        switch (key)
        {
            case ('w'):
                up = false;
                break;

            case ('a'):
                left = false;
                break;

            case ('s'):
                down = false;
                break;

            case ('d'):
                right = false;
                break;

            case (' '):
                setup();
        }

    }

    private class dbStuff implements Runnable
    {
        public void run()
        {

            try
            {
                DBscore = (DBOps.getData("player", "1", "id", "CloseBlock")).get(0);
            } catch (NullPointerException e)
            {
                System.out.print("that database get failed");
            }
            DBOps.updateData("player", "CloseBlock", "" + closeInt, "id", "1");
            System.out.println("what up now im geting connected\n" + DBscore);

            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return;
        }
    }
    public class Entity {
        /** The x and y position of this entity in terms of grid cells
         * that make up the map object*/
        protected float x;
        protected float y;
        protected Map map;
        /** The size of this entity, this is used to calculate collisions with walls */
        private float size = 0.8f;
        private boolean grounded = false;
        protected int score = 0;
        protected Color playColor = new Color(233, 9, 76);
        private Thread scoreThread = new Thread(new UpdateScore());

        public Entity(Map map, float x, float y)
        {
            this.map = map;
            this.x = x;
            this.y = y;
        }
        public float getX()
        {
            return x;
        }

        public float getY()
        {
            return y;
        }

        public void setScore(int add)
        {
            this.score += add;
        }

        public boolean move(float dx, float dy)
        {
            float nx = x + dx;
            float ny = y + dy;
            // check if the new position of the entity collides with
            // anything
            if (validLocation(nx, ny)) {
                // if it doesn't then change our position to the new position
                x = nx;
                y = ny;
                if (map.isCoin(x, y))
                {
                    map.setClear((int)x, (int)y);
                    setScore(1);
                    scoreThread = new Thread(new UpdateScore());
                    scoreThread.start();
                    System.out.println(score);
                }
                return true;
            }
            return false;
        }

        public boolean validLocation(float nx, float ny)
        {

            if (map.isBlocked(nx, ny))
            {
                return false;
            }
            if (map.isBlocked(nx + size, ny))
            {
                return false;
            }
            if (map.isBlocked(nx, ny + size))
            {
                grounded = true;
                return false;
            }
            if (map.isBlocked(nx + size, ny + size))
            {
                grounded = true;
                return false;
            }
            return true;
        }

        // check to see if Entity is grounded
        public boolean isGrounded()
        {
            if (y >= Map.TILE_SIZE * Map.HEIGHT + 3)
            {
                grounded = true;
            }
            return grounded;
        }

        public void paint() {
            int xp = (int) (Map.TILE_SIZE * x);
            int yp = (int) (Map.TILE_SIZE * y);

            rect(xp, yp, 18, 18);
            fill(233, 9, 76);
        }

        private class UpdateScore implements Runnable {

            public void run() {
                int DBscore = Integer.parseInt((DBOps.getData("scores", "1", "id", "Collector")).get(0)) + 5;
                DBOps.updateData("scores", "Collector", "" + DBscore, "id", "1");
                DBscore = Integer.parseInt((DBOps.getData("users", Driver.currentUser.getName(), "user", "scoredCollector")).get(0)) + 5;
                DBOps.updateData("users", "scoredCollector", "" + DBscore, "user", Driver.currentUser.getName() );
                return;
            }

        }
    }
    public class Coin extends Entity
    {
        /**
         * Create a new entity in the game
         *  @param map         The map this entity is going to wander around
         * @param x           The initial x position of this entity in grid cells
         * @param y           The initial y position of this entity in grid cells
         */
        Map map = new Map();
        public Coin(Map map, float x, float y)
        {
            super(map, x, y);
        }

        public void paint(Graphics g) {
            // work out the screen position of the entity based on the
            // x/y position and the size that tiles are being rendered at. So
            // if we're at 1.5,1.5 and the tile size is 10 we'd render on screen
            // at 15,15.
            int xp = (int) (Map.TILE_SIZE * x);
            int yp = (int) (Map.TILE_SIZE * y);
            // g.setColor(new Color(222, 104, 244));
            rect(xp, yp, Map.TILE_SIZE, Map.TILE_SIZE);


        }
        //proximity detection
        public float proximity(float x, float y)
        {
            float distance = 0;
            distance = (float) Math.sqrt(Math.pow((this.x - x), 2)+Math.pow((this.y -y), 2));
            return distance;
        }
    }

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
        public ArrayList<Coin> coinList = new ArrayList<Coin>(5);

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
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
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

         */

        public void paint()
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

                        rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        fill(13, 140, 200);
                    }
                    if (data[x][y] == COIN)
                    {
                        coinList.add(new Coin(this, x, y));

                        rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2);
                        fill(0, 244, 0);

                    }
                    if (data[x][y] == CLOSEST)
                    {

                        rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2);
                        fill(0, 244, 66);
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
                //data[x][y] = CLOSEST;
            }
            data[x][y] = CLOSEST;
        }

        //method to replace the coin in a new and random space
        public void resetCoin()
        {
            Random rand = new Random();
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);
            if ((data[x][y] != BLOCKED && data[x][y] != COIN) || (data[x][y] != CLOSEST && data[x][y] != BLOCKED))
            {
                data[x][y] = COIN;
            }
            if (data[x][y] == BLOCKED)
            {
                resetCoin();
            }
        }
    }

}
