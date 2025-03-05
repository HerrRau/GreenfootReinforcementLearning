import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Kugelhelfer extends BreakoutElement // Actor// SmoothMover
{
    public Schlaeger gibBeruehrtenSchlaeger()
    {
        return (Schlaeger) getOneIntersectingObject(Schlaeger.class);
    }

    public Hindernis gibBeruehrtesHindernis()
    {
        return (Hindernis) getOneIntersectingObject(Hindernis.class);
    }

    // ab hier nur Spur

    int zaehler = 0;
    int abstandHaeufigkeit = 2;

    public void macheSpur()
    {
        zaehler = zaehler - 1;
        if (zaehler < 1) 
        {
            getWorld().addObject ( new Wolke(), getX(), getY());
            zaehler = abstandHaeufigkeit ;
        }
    }

    public void addedToWorld(World w)
    {
        w.setPaintOrder(new Class[]{ Kugel.class, Wolke.class  });
    }
}
