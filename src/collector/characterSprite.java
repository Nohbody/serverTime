package collector;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.World;
import pulpcore.animation.BindFunction;
import pulpcore.image.CoreImage;
/**
 * Created by newScanTron on 10/31/2014.
 */
public class characterSprite extends PhysicsSprite

{
    private static final float WORLD_TO_STAGE_SCALE = 10;
    public characterSprite(String name, CoreImage image, float xInt, float yInt)
    {

        super(name, image, xInt, yInt);
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
