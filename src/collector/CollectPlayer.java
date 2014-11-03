package collector;

import pulpcore.image.CoreImage;

/**
 * Created by newScanTron on 10/28/2014.
 */
public class CollectPlayer extends characterSprite
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCollectScore()
    {
        return collectScore;
    }

    public void setCollectScore(int collectScore)
    {
        this.collectScore = collectScore;
    }

    private int collectScore;
    public CollectPlayer(String name, CoreImage image, float xInt, float yInt)
    {

        super(name, image, xInt, yInt);
    }

}
