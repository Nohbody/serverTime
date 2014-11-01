package collector;

/**
 * Created by newScanTron on 10/28/2014.
 */
public class CollectPlayer
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
    public CollectPlayer(String name)
    {
        this.name = name;
    }

}
