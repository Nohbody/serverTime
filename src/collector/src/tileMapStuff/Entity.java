package collector.src.tileMapStuff;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 */
public class Entity {
	/** The x position of this entity in terms of grid cells */
	protected float x;
	/** The y position of this entity in terms of grid cells */
	protected float y;
	/** The entityImage to draw for this entity */
	protected BufferedImage entityImage;
	/** The map which this entity is wandering around */
	protected Map map;
	/** The angle to draw this entity at */
	protected float ang;
	/** The size of this entity, this is used to calculate collisions with walls */
	private float size = 0.3f;
	private boolean grounded = true;
    protected int score = 0;

	public Entity(BufferedImage entityImage, Map map, float x, float y) {
		this.entityImage = entityImage;
		this.map = map;
		this.x = x*map.TILE_SIZE;
		this.y = y*map.TILE_SIZE;
	}

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

    //proximity detection for
    public int proximity()
    {
        return 0;
    }
    public void setScore(int add)
    {
        this.score += add;
    }
    public boolean move(float dx, float dy) {
		// work out what the new position of this entity will be
//		dy /= map.TILE_SIZE;
//       dx /= map.TILE_SIZE;
        float nx = (int)(x + dx)/map.TILE_SIZE;
		float ny = (int)(y + dy)/map.TILE_SIZE;
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
                System.out.println(score);

            }
            // and calculate the angle we're facing based on our last move
			//ang = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
			return true;
		}
		
		// if it wasn't a valid move don't do anything apart from 
		// tell the caller
		return false;
	}
	
	/**
	 * Check if the entity would be at a valid location if its position
	 * was as specified
	 * 
	 * @param nx The potential x position for the entity
	 * @param ny The potential y position for the entity
	 * @return True if the new position specified would be valid
	 */
	public boolean validLocation(float nx, float ny)
    {
        // here we're going to check some points at the corners of
        // the player to see whether we're at an invalid location
        // if any of them are isBlocked then the location specified
        // isn't valid
        System.out.println("nx" + nx + " ny " + ny);
        if (map.isBlocked(nx - size, ny - size))
        {
            return false;
        }
        if (map.isBlocked(nx + size, ny - size))
        {
            return false;
        }
        if (map.isBlocked(nx - size, ny + size))
        {
            grounded = true;
            return false;
        }
        if (map.isBlocked(nx + size, ny + size))
        {
            grounded = true;
            return false;
        }

        // if all the points checked are unblocked then we're in an ok
        // location
        return true;
    }

    // check to see if Entity is grounded
    public boolean isGrounded()
    {
        return grounded;
    }

	/**
	 * Draw this entity to the graphics context provided.
	 * 
	 * @param g The graphics context to which the entity should be drawn
	 */
	public void paint(Graphics g) {
		// work out the screen position of the entity based on the
		// x/y position and the size that tiles are being rendered at. So
		// if we're at 1.5,1.5 and the tile size is 10 we'd render on screen 
		// at 15,15.
		int xp = (int) (Map.TILE_SIZE * x);
		int yp = (int) (Map.TILE_SIZE * y);
		
		// rotate the sprite based on the current angle and then
		// draw it
		//g.rotate(ang, xp, yp);

        //g.drawImage(entityImage, (int) (xp - 20), (int) (yp - 30), null);
        g.setColor(Color.ORANGE);

//
        g.fill3DRect((int)x, (int)y, 18, 18, true);
		//g.rotate(-ang, xp, yp);
	}
}
