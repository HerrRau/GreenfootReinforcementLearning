import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Kugel extends Kugelhelfer
{
    boolean respawn = false;
    public  void respawn() {
        respawn = true;
    }

    public void act()
    {        
        if (respawn) {
            respawn = false;
            this.setLocation(getX(), 50);
            this.setRotation(360-getRotation());
        }

        ueberpruefeKollision();
        bewege();
        ueberpruefeEnde();
        macheSpur();
    }

    public void ueberpruefeKollision()
    {
        if (isTouching(Schlaeger.class))
        {
            kollidiereMitSchlaeger();
        }
        else if (isTouching(Hindernis.class))
        {
            kollidiereMitHindernis();
        }
        else if (getX()==0 || getX()==getWorld().getWidth()-1)
        {
            kollidiereVertikal();
        }
        else if (getY()==0 || getY()==getWorld().getHeight()-1)
        {
            kollidiereHorizontal();
        }
    }

    public void ueberpruefeEnde()
    {
        if (getWorld().getObjects(Hindernis.class).size()==0
        && getWorld().getObjects(Belohnung.class).size()==0) 
        {
            getWorldOfType(BreakoutGame.class).erhoeheLevel();
        }
    }

    public void bewege() {}  

    public void kollidiereHorizontal() {}

    public void kollidiereVertikal() {}

    public void kollidiereMitHindernis() {}

    public void kollidiereMitSchlaeger() {}

}
