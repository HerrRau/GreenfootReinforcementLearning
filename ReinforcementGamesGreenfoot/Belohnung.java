import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Belohnung extends BreakoutElement
{
    public void act()
    {
        setLocation(getX(), getY()+1);
        if (isAtEdge())
        {
            getWorld().removeObject(this);
        }
    }
}
