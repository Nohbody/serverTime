package collector.src.tileMapStuff;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by newScanTron on 11/11/2014.
 */
public class Coin extends Entity
{
    /**
     * Create a new entity in the game
     *
     * @param entityImage The entityImage to represent this entity (needs to be 32x32)
     * @param map         The map this entity is going to wander around
     * @param x           The initial x position of this entity in grid cells
     * @param y           The initial y position of this entity in grid cells
     */
    public Coin(BufferedImage entityImage, Map map, float x, float y)
    {
        super(entityImage, map, x, y);
    }
    public void paint(Graphics2D g) {
        // work out the screen position of the entity based on the
        // x/y position and the size that tiles are being rendered at. So
        // if we're at 1.5,1.5 and the tile size is 10 we'd render on screen
        // at 15,15.
        int xp = (int) (Map.TILE_SIZE * x);
        int yp = (int) (Map.TILE_SIZE * y);

        // rotate the sprite based on the current angle and then
        // draw it

        g.drawImage(entityImage, (int) (xp), (int) (yp), null);

    }
}
