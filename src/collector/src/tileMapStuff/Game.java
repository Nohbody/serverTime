package collector.src.tileMapStuff;//Chris Murphy
import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

//sept 2013
//purpose: a main panel for the player and prince to do battle


public class Game extends JPanel
{
	private ImageIcon zombieImage,  zombieImageRight, princeImage, flameIcon, flameIconRight;
	//random thing stuff
	Random randTron = new Random();

	private final int height = 270;
	private final int length = 450;


    private Color myPurp = new Color(150, 0, 220);

	private boolean zombieMoveRight = true;
	//timer
	private Timer timer, healthTimer;
	private final int timerInt = 5;

	private final int moveInt = 8;


//booleas for which key is pressed
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
//some more stuff that im adding from the game canvas
Map map = new Map();
    private ArrayList<Coin> coins = new ArrayList<Coin>();
    Coin myCoin = new Coin( map, 60, 60);
    private float gravity = 0.08f;


    Zombie player = new Zombie(map, 100f, 100f);

    //constructor
	public Game()
	{
		addKeyListener (new DirectionListener());
		//image declorations

        setBackground(Color.black);
		setPreferredSize (new  Dimension(length, height));
		setFocusable(true);
		
		Timer myTimer = new Timer(10, new GameLoop());
		myTimer.start();
		
		player.move(0, gravity);
    }

    private class GameLoop implements ActionListener
    {
        
			public void actionPerformed(ActionEvent arg0) {
				long last = System.nanoTime();

				requestFocus();
		        // keep looking while the game is running

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
		            // pause a bit so that we don't choke the system
		            try
		            {
		                Thread.sleep(4);
		            } catch (Exception e)
		            {
		                System.out.println("we can't sleep, the Thread is not slowing down");
		            }
		            // calculate how long its been since we last ran the
		            // game logic
		            long delta = (System.nanoTime() - last) / 1000000;
		            last = System.nanoTime();
		            System.out.println(delta);
		            //  we divide the amount of time passed into segments
		            // of 5 milliseconds and update based on that
		            repaint();
		            for (int iFor = 0; iFor < delta / 5; iFor++)
		            {
		                logic(5);
		                System.out.println("logic sucks");
		            }
		            // after we've run through the segments if there is anything
		            // left over we update for that
		            if ((delta % 5) != 0)
		            {
		                logic(delta % 5);
		            }
				System.out.print(player.getX() + " " + player.getY());
			}

    }
	//logic does some stuff to move our player this will become mor complicated
    //shortly
    public void logic(long delta)
    {
        // check the keyboard and record which way the player
        // is trying to move this loop
        float dx = 0;
        float dy = 0;

        right = true;
        if (left)
        {
            dx -= 2.5f;

        }
        if (right)
        {
            dx += 2.5f;
        }
        if (up && player.isGrounded())
        {
            //dy += -4.7;
            dy += -7.0;
            System.out.println("we are not jumping");

        }
        if (down)
        {
            dy += 1;
            System.out.println("down is true");


        }

        // if the player needs to move attempt to move the entity
        // based on the keys multiplied by the amount of time thats

        if ((dx != 0) || (dy != 0))
        {
            player.move(dx * delta * 0.003f,
                    dy * delta * 0.003f);
        }
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

            	System.out.println("LEFT");
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

			if (event.getKeyCode() == KeyEvent.VK_LEFT
					|| event.getKeyCode() == (KeyEvent.VK_A + KeyEvent.VK_SPACE))
			{
				zombieMoveRight = false;
				setBackground(new Color(45, 21, 34));
			}

		}
	}
}



