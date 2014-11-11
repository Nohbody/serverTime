package collector.src.tileMapStuff;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Zombie extends Entity
{
    private int health;
    private static BufferedImage zombie;
    private int damage = 0;
    private float x = 0f;
    private float y = 0f;
    private float gravity = 0.5f;
    private float velocityX = 5f, velocityY = 0f;
    private boolean grounded = true;
    BufferedImage zombieImg;
    BufferedImage zombieRight;

    public Zombie(BufferedImage image, Map map, float x, float y) {
        super(image, map, x, y);

    }
    public Zombie(BufferedImage zombieImg, Map map, float x, float y, int health)

    {
        super(zombieImg, map, x,y);
        //these where change to .setZombie when i automated the get/set it changed from
        //this. what are the differences?
        this.zombieImg = zombieImg;
        this.health = health;
        try
        {
             BufferedImage zombieLeft = ImageIO.read(new File("zombieImg.png"));
            System.out.println("we found the zombieImg");
        } catch (IOException e)
        {
            System.out.println("we cant find that image");
        }
        //get zombieImg facing right
        try
        {
            BufferedImage zombieRight = ImageIO.read(new File("PNG/heroLeft.png"));
            System.out.println("we found the zombieImg");
        } catch (IOException e)
        {
            System.out.println("we cant find that image");
        }

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
     * @return the zombie
     */
    public BufferedImage getZombie()
    {
        return zombie;
    }

    //set that image
    public void setZombie(BufferedImage zombie)
    {
        this.zombieImg = zombieImg;
    }

    //if the price touches the zombie/mummie which i feel like is sort of a
    //square is a rectange type situation
    public void update()
    {
        this.x += velocityX ;
        this.y += velocityY;
        velocityY += gravity;
        if (this.y > 720.0f - zombieImg.getHeight())
        {
            this.y = 720.0f - zombieImg.getHeight();
            velocityY = 0;
            grounded = true;

        }
        if (this.x >= 1260 - zombie.getWidth() || this.x <= 0)
        {
            velocityX *= -1;
//            if (this.x >= 1260)
//            {
//                this.setZombie(zombieLeft);
//            }
//            if (this.x <= 0)
//            {
//                this.setZombie(zombieRight);
//            }
        }
    }

    public boolean isHit(int zombieX, int zombieY, int princeX, int princeY, int princeW, int princeH)
    {
        if ((princeX + princeW > zombieX && (princeX + princeW) < (zombieX + zombie.getWidth()))
                && (princeY + princeH > zombieY && (princeY + princeH) < (zombieY + zombie.getHeight())))
        {

            Random randTron = new Random();
            System.out.println(" zombie hit");
            return true;
        }

        return false;
    }

    public void moveX(int change)
    {
        velocityX *= change;
    }
    /**
     * @return the damage
     */
    public int getDamage()
    {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage)
    {
        this.damage = damage;
    }

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
        return this.x;
    }

    public void setX(int newX)
    {
        this.x = newX;
    }

    public float getY()
    {
        return this.y;
    }

    public void setY(int newY)
    {
        this.y = newY;
    }
}
