package collector;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.World;
import pulpcore.animation.BindFunction;
import pulpcore.image.CoreImage;

/**
 * Created by newScanTron on 10/28/2014.
 */
public class CollectObject extends PhysicsSprite
{
    private String name;
    private int[] location;
    private int WORLD_TO_STAGE_SCALE = 10;
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

    public void setBody(Body b, World w)
    {
        this.body = b;
        w.add( this.body );

        this.body.setPosition( (float)this.x.get() / WORLD_TO_STAGE_SCALE,
                (float)this.y.get() / WORLD_TO_STAGE_SCALE );
        this.body.setRestitution(0.5f);  // not bouncy   - inelastic
        this.body.setFriction(0.1f); // not slippery - "frictiony"?

        // bind Sprite to Body
        this.x.bindTo(new BindFunction()
        {
            public Number f() { return body.getX() * WORLD_TO_STAGE_SCALE;  }
        });

        this.y.bindTo(new BindFunction()
        {
            public Number f() { return body.getY() * WORLD_TO_STAGE_SCALE;  }
        });

        this.angle.bindTo(new BindFunction()
        {
            public Number f() { return body.getRotation(); }
        });
    }
}
