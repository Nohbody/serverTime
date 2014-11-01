/**
 * Created by newScanTron on 10/30/2014.
 */
package collector;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.CollisionEvent;
import org.newdawn.fizzy.World;
import org.newdawn.fizzy.WorldListener;
import pulpcore.Input;
import pulpcore.image.Colors;
import pulpcore.image.CoreFont;
import pulpcore.image.CoreImage;
import pulpcore.platform.desktop.CoreApplication;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Group;
import pulpcore.sprite.Label;
import pulpcore.sprite.Sprite;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Random;

public class collectPanel extends Scene2D
{
    // World: the model in which physics takes place.
    protected World physicsWorld;
    private static final float WORLD_TO_STAGE_SCALE = 10;
    private float time;
    private float dt = 1 / 60f;

    public PhysicsSprite playerSprite;

    CoreFont font = CoreFont.getSystemFont();
    Label playerDataLabel = new Label(font, "", 0, 0);
    DecimalFormat df = new DecimalFormat("#0.00");

    public static void main(String[] args)
    {
        CoreApplication app = new CoreApplication(collectPanel.class);
        app.setWindowSize(800,600);
        app.run();
    }

    public void load()
    {
        // use to reset demo
        unload();

        // background
        add(new FilledSprite(Colors.BLACK));

        physicsWorld = new World();  // positive gravity goes down in pulpcore.
        physicsWorld.setGravity(20);

        // group of physics sprites that have potential non-physics interaction
        final Group spriteList = new Group();
        add(spriteList);

        // create and add dynamic objects
        CoreImage playerImage = new CoreImage(32,32).tint(Colors.GREEN);
        playerSprite = new characterSprite("hero", playerImage, 400, 50);
        playerSprite.setBodyRectangle(true, physicsWorld);
        spriteList.add(playerSprite);

        CoreImage circImage = CoreImage.load("ball.png");

        PhysicsSprite circSprite = new PhysicsSprite(circImage, 300, 75);
        circSprite.setBodyCircle(true, physicsWorld);
        spriteList.add(circSprite);

        CoreImage boxImage = CoreImage.load("box.png");

        PhysicsSprite boxSprite = new PhysicsSprite(boxImage, 550, 75);
        boxSprite.setBodyRectangle(true, physicsWorld);
        spriteList.add(boxSprite);

        // create and add the ground and other static objects
        CoreImage floorImage = new CoreImage(900,10).tint(Colors.PURPLE);
        PhysicsSprite floor = new PhysicsSprite(floorImage, 410, 600);
        floor.setBodyRectangle(false, physicsWorld);
        spriteList.add(floor);
        //top so we have a bounce world
        CoreImage ceilingImage = new CoreImage(900, 10).tint(Colors.CYAN);
        PhysicsSprite ceiling = new PhysicsSprite(ceilingImage, 410, 0);
        ceiling.setBodyRectangle(false, physicsWorld);
        spriteList.add(ceiling);
        // create and add bounds on the left and right
        CoreImage leftImage = new CoreImage(10,900).tint(Colors.PURPLE);
        PhysicsSprite leftSprite = new PhysicsSprite(leftImage, -5, 500);
        leftSprite.setBodyRectangle(false, physicsWorld);
        spriteList.add(leftSprite);
        // and the right side
        CoreImage rightImage = new CoreImage(10,900).tint(Colors.PURPLE);
        PhysicsSprite rightSprite = new PhysicsSprite(rightImage, 805, 500);
        rightSprite.setBodyRectangle(false, physicsWorld);
        spriteList.add(rightSprite);

        CoreImage specialImage = new CoreImage(60,60).tint(Colors.PURPLE);
        PhysicsSprite specialBox = new PhysicsSprite(specialImage, 200, 300);
        specialBox.setBodyRectangle(false, physicsWorld);
        spriteList.add(specialBox);

        CoreImage platformOneImage = new CoreImage(120,40).tint(Colors.PURPLE);
        PhysicsSprite platformOne = new PhysicsSprite(platformOneImage, 500, 388);
        platformOne.setBodyRectangle(false, physicsWorld);
        spriteList.add(platformOne);

//        // text labels
//        CoreFont font = CoreFont.getSystemFont().tint(Colors.WHITE);
//        Label directions = new Label(font, "Press ARROW keys to add velocity to gray box or R key to reset.", 100, 40);
//        add(directions);
//
//        playerDataLabel = new Label(font, "Player data: ", 100, 60);
//        add(playerDataLabel);

        // actions to take place on collisions
        physicsWorld.addBodyListener( playerSprite.getBody(), new WorldListener()
        {
            PhysicsSprite p;
            public void collided(CollisionEvent event)
            {

                p = getPhysicsSprite( event.getBodyB(), spriteList );
                if (p == null) return;

                if ( p.getName().equals("special") )
                {

                    Random rand = new Random();
                    if (playerSprite.getXVelocity() > 0)
                        p.setImage(p.getImage().tint(Colors.rgb(rand.nextInt(254), rand.nextInt(254), rand.nextInt(254))));
//                        p.setImage(p.getImage().tint(Colors.BLUE));
                    else if (playerSprite.getXVelocity() < 0)
                        p.setImage(p.getImage().tint(Colors.rgb(rand.nextInt(254), rand.nextInt(254), rand.nextInt(254))));
                }
            }

            public void separated(CollisionEvent event)
            {
                Random rand = new Random();
                p = getPhysicsSprite( event.getBodyB(), spriteList );
                // this method intentionally left blank
                if (playerSprite.getYVelocity() > 0)
                    p.setImage(p.getImage().tint(Colors.rgb(rand.nextInt(254), rand.nextInt(254), rand.nextInt(254))));
                else if (playerSprite.getYVelocity() < 0)
                    p.setImage(p.getImage().tint(Colors.rgb(rand.nextInt(254), rand.nextInt(254), rand.nextInt(254))));;
            }
        });
    }

    // find the sprite that is both associated to a given body and in a given group
    public PhysicsSprite getPhysicsSprite(Body b, Group g)
    {
        Iterator<Sprite> spriteList = g.iterator(); // create a list to search through
        while ( spriteList.hasNext() )
        {
            Sprite item = spriteList.next(); // get the next Sprite in the list
            if (item instanceof PhysicsSprite && ((PhysicsSprite)item).getBody().equals(b) )
                return (PhysicsSprite)item;
        }
        return null;
    }

    public void update(int elapsedTime)
    {
        // restart application
        if (Input.isPressed(Input.KEY_R))
            load();

        // Update Physics.
        // This can be improved, see http://gafferongames.com/game-physics/fix-your-timestep/
        time += (elapsedTime / 1000f);
        while (time >= dt)
        {
            physicsWorld.update(dt);
            time -= dt;
        }

        if ( Input.isPressed(Input.KEY_UP) ) // Pressed: only applies once (when pressed)
            playerSprite.addYVelocity( -20 );
        if ( Input.isPressed(Input.KEY_DOWN) )
            playerSprite.addYVelocity( 10 );
        if ( Input.isPressed(Input.KEY_LEFT) )
            playerSprite.addXVelocity( -10 );
        if ( Input.isPressed(Input.KEY_RIGHT) )
            playerSprite.addXVelocity( 10 );

//        String s = "Player data: ";
//        s += "Position = [ " + df.format(playerSprite.x.get()) + " , " + df.format(playerSprite.y.get()) + " ]; ";
//        s += "Velocity = [ " + df.format( playerSprite.getXVelocity() ) + " , " + df.format(playerSprite.getYVelocity()) + " ] ";
//        playerDataLabel.setText( s );
    }
}