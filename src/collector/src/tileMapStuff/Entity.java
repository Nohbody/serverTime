package collector.src.tileMapStuff;

import java.awt.*;

/**
 */
public class Entity {
	/** The x position of this entity in terms of grid cells */
	protected float x;
	/** The y position of this entity in terms of grid cells */
	protected float y;

	/** The map which this entity is wandering around */
	protected Map map;

	/** The size of this entity, this is used to calculate collisions with walls */
	private float size = 0.8f;
	private boolean grounded = false;
    protected int score = 0;

    protected Color playColor = new Color(233, 0, 66);

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

    public boolean move(float dx, float dy) {

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

        // if all the points checked are unblocked then we're in an ok
        // location
        return true;
    }

    // check to see if Entity is grounded
    public boolean isGrounded()
    {
        if (y >= map.TILE_SIZE * map.HEIGHT + 3)
        {
            grounded = true;
        }


        return grounded;
    }

	/**
	 * Draw this entity to the graphics context provided.
	 * @param g The graphics context to which the entity should be drawn
	 */
	public void paint(Graphics g) {
		// work out the screen position of the entity based on the
		// x/y position and the size that tiles are being rendered at. So
		// if we're at 1.5,1.5 and the tile size is 10 we'd render on screen 
		// at 15,15.
		int xp = (int) (Map.TILE_SIZE * x);
		int yp = (int) (Map.TILE_SIZE * y);
		g.setColor(playColor);
        g.fill3DRect(xp, yp, 18, 18, true);

	}
}
