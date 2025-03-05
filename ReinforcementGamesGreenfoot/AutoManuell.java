import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class AutoManuell extends SmoothMoverSpecial
{
    public void act() 
    {
        ueberpruefeSteuerung();
    }    

    public void ueberpruefeSteuerung(){
        straight();
        if (Greenfoot.isKeyDown("space"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("up"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("left"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("down"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("right"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("w"))
        {
            accelerate();
        }
        if (Greenfoot.isKeyDown("a"))
        {
            this.straightLeft();
        }
        if (Greenfoot.isKeyDown("s"))
        {
            this.decelerate();
        }
        if (Greenfoot.isKeyDown("d"))
        {
            this.straightRight();
        }
    }
}