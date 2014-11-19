package collector.src.tileMapStuff;

import java.awt.*;

/**
 * Created by newScanTron on 11/11/2014.
 */
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
        int xp = (int) (map.TILE_SIZE * x);
        int yp = (int) (map.TILE_SIZE * y);
        g.setColor(new Color(222, 104, 244));
        g.drawRect (xp ,yp , map.TILE_SIZE, map.TILE_SIZE);

    }
    //proximity detection
    public float proximity(float x, float y)
    {
        float distance = 0;
        distance = (float) Math.sqrt(Math.pow((this.x - x), 2)+Math.pow((this.y -y), 2));
        return distance;
    }
}
