package collector.src.tileMapStuff;

import java.awt.image.BufferedImage;


public class Zombie extends Entity
{
    private int health;
    private static BufferedImage zombie;

    private int x = 0;
    private int y = 0;
    private float gravity = 0.5f;
    private float velocityX = 5f, velocityY = 0f;
    private boolean grounded = true;


    public Zombie(BufferedImage image, Map map, float x, float y) {
        super(image, map, x, y);

    }


    public Zombie( Map map, float x, float y)

    {
        super( map, x,y);



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
    public void setHealth(int health)
    {

        this.health -= health;

    }

    /**
     * @return the player
     */
    public BufferedImage getZombie()
    {
        return zombie;
    }



    //if the price touches the player/mummie which i feel like is sort of a
    //square is a rectange type situation
    public void update()
    {
        this.x += velocityX ;
        this.y += velocityY;
        velocityY += gravity;
        if (this.y > 720.0f)
        {

            velocityY = 0;
            grounded = true;

        }
        if (this.x >= 1260 || this.x <= 0)
        {
            velocityX *= -1;

        }
    }


    public void moveX(int change)
    {
        velocityX *= change;
    }
    /**
     * @return the score
     */




    //jump and method to keep jumps controlled how long you hold down the jump
    public void jump()
    {
        this.y -= 100.0;
    }

    public void endJump()
    {
        if (velocityY < -60.0)
        {
            velocityY -= 60.0;
        }
    }

    public float getX()
    {
        return super.x;
    }


    public float getY()
    {
        return super.y;
    }

}
