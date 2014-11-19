package collector.src.tileMapStuff;//Chris Murphy
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//sept 2013
//purpose: a main panel for the player and prince to do battle


public class Game extends JPanel
{

	private final int height = 270;
	private final int length = 450;

    private Color myPurp = new Color(150, 0, 220);

//booleas for which key is pressed
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
//some more stuff that im adding from the game canvas
    Map map = new Map();
    private ArrayList<Coin> coins ;
    Coin myCoin ;
    private float gravity = 0.3f;
    Zombie player ;

    //constructor
	public Game()
	{
        player = new Zombie(map, 3f, 2f);
        coins = new ArrayList<Coin>();

        myCoin = new Coin( map, 60, 60);
        addKeyListener (new DirectionListener());
        setBackground(Color.black);
		setPreferredSize (new  Dimension(length, height));
		setFocusable(true);
		
		Timer myTimer = new Timer(10, new GameLoop());
		myTimer.start();

    }

    private class GameLoop implements ActionListener
    {
        
			public void actionPerformed(ActionEvent arg0) {
				long last = System.nanoTime();

				requestFocus();
		        // keep looking while the game is running
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
		            repaint();
		            logic();
			}

    }
	//logic does some stuff to move our player this will become mor complicated
    //shortly
    public void logic()
    {
        // check the keyboard and record which way the player
        // is trying to move this loop
        float dx = 0;
        float dy = 0;
        float speed = 2.5f;
        float jump = 24.0f;
        float downPound =2f;
        if (left)
        {
            dx -= speed;

        }
        if (right)
        {
            dx += speed;

        }
        if (up )
        {
            dy -= jump;
        }
        if (down)
        {
            dy += downPound;

        }

        // if the player needs to move attempt to move the entity
        // based on the keys multiplied by the amount of time thats


            player.move(dx * 0.03f,
                    dy * 0.03f);


    }

	//apperantly this repaints my image  i am going to need this explained to me a bit
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);

        page.setColor(myPurp);

        page.fillRect(0, 0, 450, 270);
        map.paint(page);
        player.paint(page);

	}



    // key listener
	public class DirectionListener implements KeyListener
	{
        /**
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */

        public void keyPressed(KeyEvent e)
        {
            // check the keyboard and record which keys are pressed
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
            {


                left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                right = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                down = true;

            }
            if (e.getKeyCode() == KeyEvent.VK_UP)
            {
                up = true;


            }

        }

        /**
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         */
        public void keyReleased(KeyEvent e)
        {
            // check the keyboard and record which keys are released
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
            {
                left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                right = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP)
            {
                up = false;
            }
        }

		public void keyTyped(KeyEvent event)
        {

		}
	}
}



