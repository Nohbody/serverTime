package collector.src.tileMapStuff;//Chris Murphy

import main.DBOps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//sept 2013
//purpose: a main

public class Game extends JPanel implements Runnable
{

    private final int height = 270;
    private final int length = 450;
    private Color myPurp = new Color(150, 0, 220);
    //booleas for which key is pressed
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    private String dbString = "", DBscore = "";
    private Thread t, dbThread;
    //some more stuff that im adding from the game canvas
    Map map = new Map();
    private ArrayList<Coin> coins;
    Coin myCoin;
    private float gravity = 0.048f;
    Entity player;
    Timer myTimer;
    //constructor
    public Game()
    {
        player = new Entity(map, 3f, 2f);
        coins = new ArrayList<Coin>();
        myCoin = new Coin(map, 60, 60);
        addKeyListener(new DirectionListener());
        setBackground(Color.black);
        setPreferredSize(new Dimension(length, height));
        setFocusable(true);
        myTimer = new Timer(12, new GameLoop());
        t = new Thread(this, "gameThread");
        t.start();
    }

    @Override
    public void run()
    {
        myTimer.start();

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
            DBOps.updateData("player", "CloseBlock", "" + dbString, "id", "1");
            System.out.println("what up now im geting connected\n" + DBscore);
            return;
        }
    }
    private class GameLoop implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            requestFocus();
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
            dbString = "blockB";
            dbThread = new Thread(new dbStuff(), "dbThread");
            dbThread.start();
            repaint();
            logic();
        }
    }

    //logic does some stuff to move our player this will become mor complicated
    //shortly
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

    public void paintComponent(Graphics page)
    {
        super.paintComponent(page);
        page.setColor(myPurp);
        page.fillRect(0, 0, 450, 270);
        map.paint(page);
        player.paint(page);
    }

    public class DirectionListener implements KeyListener
    {
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



