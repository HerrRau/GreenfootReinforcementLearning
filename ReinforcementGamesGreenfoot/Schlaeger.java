import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Schlaeger extends Tastenbeweger
{
    public Schlaeger()
    {
        int x = getImage().getWidth();
        int y = getImage().getHeight();
        getImage().scale(100/2, y);

    }

    public void act()
    {
        ueberpruefeSteuerung();
        ueberpruefeFangBelohnung();
    }

    public void ueberpruefeFangBelohnung()
    {
        if (isTouching(Belohnung.class))
        {
            if (isTouching(Belohnung1.class))
            {
                getImage().scale(200, 5);
                setImage(new GreenfootImage(getImage()));
            }
            else if (isTouching(Belohnung2.class))
            {
                getImage().scale(100, 5);
                setImage(new GreenfootImage(getImage()));
            }
            removeTouching(Belohnung.class);
        }

    }
}
