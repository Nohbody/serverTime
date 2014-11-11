package collector.src.PNG;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class prince extends Rectangle2D
{
    private int health;


    private int xMove;
    private int yMove;
    private BufferedImage prince;
    private Rectangle2D rect;

    public prince(int health, BufferedImage prince)
    {
        //these where change to .setZombie when i automated the get/set it changed from
        //this. what are the differences?
        this.prince = prince;
        this.health = health;

    }

    /**
     * @return the health
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * @param health the health to set
     */
    //im going to use the setHealth method to decrement the health which is a constant i
    //put in the constructor
    public void setHealth(int health)
    {

        this.health -= health;
    }

    /**
     * @return the zombie
     */
    public BufferedImage getPrince()
    {
        return prince;
    }

    public int getxMove()
    {
        return xMove;
    }

    public void setxMove(int xMove)
    {
        this.xMove = xMove;
    }

    public int getyMove()
    {
        return yMove;
    }

    public void setyMove(int yMove)
    {
        this.yMove = yMove;
    }

    //this is the longest if statement to least amount of stuff inside the braces i have ever writen
    public boolean isHit(int princeX, int princeY, int fireX, int fireY, int fireW, int fireH)
    {

        if (/**/(fireX > princeX && (fireX + fireW) < (princeX + prince.getWidth()))/**/
                && (fireY > princeY && (fireY + fireH) < (princeY + prince.getHeight())))
        {
            System.out.println(" prince hit");
            return true;
        }

        return false;

    }

    @Override
    public void setRect(double x, double y, double w, double h)
    {

    }

    @Override
    public int outcode(double x, double y)
    {
        return 0;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r)
    {
        return null;
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r)
    {
        return null;
    }

    @Override
    public double getX()
    {
        return 0;
    }

    @Override
    public double getY()
    {
        return 0;
    }

    @Override
    public double getWidth()
    {
        return 0;
    }

    @Override
    public double getHeight()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }
}