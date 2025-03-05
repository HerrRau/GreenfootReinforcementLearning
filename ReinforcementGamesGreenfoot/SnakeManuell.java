import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeManuell extends SnakeHead
{
    public SnakeManuell() {
        beStrict = false;
    }
    
    public void ueberpruefeSteuerung()
    {
        // to do: wenn a gedrueckt wird, soll Rotation nur dann auf 180 gesetzt werden, wenn sie nicht 0 ist -> man kann nicht in sich selber laufen
        if (Greenfoot.isKeyDown("a"))
        {
            blickeNachWesten();
        }
        else if (Greenfoot.isKeyDown("s"))
        {
            blickeNachSueden();
        }
        else if (Greenfoot.isKeyDown("d"))
        {
            blickeNachOsten();
        }
        else if (Greenfoot.isKeyDown("w"))
        {
            blickeNachNorden();
        }

    }

    public void act()
    {
        ueberpruefeSteuerung();
        macheSchritt();
    }

        
}