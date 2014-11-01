package collector; /**
 * Created by newScanTron on 10/30/2014.
 */
import org.newdawn.fizzy.*;
import pulpcore.animation.BindFunction;
import pulpcore.image.CoreImage;
import pulpcore.sprite.ImageSprite;

public class PhysicsSprite extends ImageSprite
{
    protected Body body;

    private static final float WORLD_TO_STAGE_SCALE = 10;

    public PhysicsSprite(String spriteName, String imageFileName, float x, float y)
    {
        super(imageFileName, x, y);
        setName(spriteName);
        setAnchor(0.5, 0.5);
    }

    public PhysicsSprite(String imageFileName, float x, float y)
    {
        super(imageFileName, x, y);
        setName("");
        setAnchor(0.5, 0.5);
    }

    public PhysicsSprite(String spriteName, CoreImage image, float x, float y)
    {
        super(image, x, y);
        setName(spriteName);
        setAnchor(0.5, 0.5);
    }

    public PhysicsSprite(CoreImage image, float x, float y)
    {
        super(image, x, y);
        setName("");
        setAnchor(0.5, 0.5);
    }

    public void setBody(Body b, World w)
    {
        this.body = b;
        w.add( this.body );

        this.body.setPosition( (float)this.x.get() / WORLD_TO_STAGE_SCALE,
                (float)this.y.get() / WORLD_TO_STAGE_SCALE );
        this.body.setRestitution(0);  // not bouncy   - inelastic
        this.body.setFriction(0.25f); // not slippery - "frictiony"?

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

    // automatically creates rectangular body based on image dimensions
    public void setBodyRectangle(boolean isDynamic, World w)
    {
        Rectangle r = new Rectangle( (float)this.width.get() / WORLD_TO_STAGE_SCALE,
                (float)this.height.get() / WORLD_TO_STAGE_SCALE );

        Body b;
        if ( isDynamic )
            b = new DynamicBody(r, (float)this.x.get() / WORLD_TO_STAGE_SCALE,
                    (float)this.y.get() / WORLD_TO_STAGE_SCALE);
        else
            b = new StaticBody(r, (float)this.x.get() / WORLD_TO_STAGE_SCALE,
                    (float)this.y.get() / WORLD_TO_STAGE_SCALE);

        setBody(b, w);
    }

    // automatically creates circular body based on image dimensions
    public void setBodyCircle(boolean isDynamic, World w)
    {
        Circle c = new Circle( (float)this.width.get() / (2 * WORLD_TO_STAGE_SCALE) );

        Body b;
        if ( isDynamic )
        {
            b = new DynamicBody(c, (float) this.x.get() / WORLD_TO_STAGE_SCALE,
                    (float) this.y.get() / WORLD_TO_STAGE_SCALE);
            b.setFixedRotation(true);
        }
        else
            b = new StaticBody(c, (float)this.x.get() / WORLD_TO_STAGE_SCALE,
                    (float)this.y.get() / WORLD_TO_STAGE_SCALE);

        setBody(b, w);
    }

    public Body getBody()
    {   return this.body;   }

    public float getXVelocity()
    {   return this.body.getXVelocity();   }

    public float getYVelocity()
    {   return this.body.getYVelocity();   }

    public void setVelocity(float xVelocity, float yVelocity)
    {   this.body.setVelocity( xVelocity, yVelocity );   }

    public void setXVelocity(float xVelocity)
    {   this.body.setVelocity( xVelocity, this.body.getYVelocity() );   }

    public void setYVelocity(float yVelocity)
    {   this.body.setVelocity( this.body.getXVelocity(), yVelocity );   }

    public void addVelocity(float deltaX, float deltaY)
    {   this.body.setVelocity( this.body.getXVelocity() + deltaX, this.body.getYVelocity() + deltaY ); }

    public void addXVelocity(float deltaX)
    {   this.body.setVelocity( this.body.getXVelocity() + deltaX, this.body.getYVelocity() ); }

    public void addYVelocity(float deltaY)
    {   this.body.setVelocity( this.body.getXVelocity(), this.body.getYVelocity() + deltaY ); }

    public void setRestitution(float r)
    {   this.body.setRestitution(r);  }

    public void setFriction(float f)
    {   this.body.setFriction(f);  }

    public void setName(String s)
    {   super.setTag(s);  }

    public String getName()
    {   return (String)this.getTag();  }
}