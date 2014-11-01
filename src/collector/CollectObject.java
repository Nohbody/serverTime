package collector;

/**
 * Created by newScanTron on 10/28/2014.
 */
public class CollectObject
{
    private String name;
    private int[] location;
    public String getName()
    {
        return name;
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

    public CollectObject(String name)
    {
        this.name = name;
    }
}
