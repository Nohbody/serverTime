package collector;

import pulpcore.image.CoreImage;

/**
 * Created by newScanTron on 10/28/2014.
 */
public class CollectObject extends PhysicsSprite
{
    private String name;
    private int[] location;
    public String getName()
    {
        return name;
    }
    public CollectObject(String name, CoreImage image, float x, float y)
    {
        super(name, image, x, y);
        this.name = name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public int[] getLocation()
    {
        return location;
    }

    public void setLocation(int[] location)
    {
        this.location = location;
    }


}
